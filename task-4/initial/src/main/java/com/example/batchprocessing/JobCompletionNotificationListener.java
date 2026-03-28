package com.example.batchprocessing;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		final long duration = Duration.between(jobExecution.getStartTime(), jobExecution.getEndTime()).toMillis();
    
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      		log.info("!!! JOB FINISHED! Time to verify the results");
			log.info("Total time: {} ms", duration);

			jdbcTemplate
				.query("SELECT productId, productSku, productName, productAmount, productData FROM products", new DataClassRowMapper<>(Product.class))
				.forEach(person -> log.info("Transformed <{}> in the database.", person));
		}
		else if (jobExecution.getStatus() == BatchStatus.FAILED) {
       		log.error("!!! JOB FAILED after {} ms", duration);
        	log.error("Exit Status: {}", jobExecution.getExitStatus().getExitDescription());

		}
    	jobExecution.getStepExecutions().forEach(step -> {
			log.info("Step [{}]: Read={}, Written={}, Skipped={}", 
				step.getStepName(), step.getReadCount(), step.getWriteCount(), step.getReadSkipCount());
    	});
	}
}
