package com.example.batchprocessing;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importProductJob; // Имя должно совпадать с вашим @Bean Job

    @PostMapping("/run-job")
    public String runJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis()) // Чтобы каждый запуск был уникальным
                    .toJobParameters();
            
            jobLauncher.run(importProductJob, params);
            return "Job started successfully!";
        } catch (Exception e) {
            return "Job failed: " + e.getMessage();
        }
    }
}
