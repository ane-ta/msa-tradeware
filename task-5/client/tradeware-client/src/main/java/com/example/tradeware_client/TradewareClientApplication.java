package com.example.tradeware_client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TradewareClientApplication implements CommandLineRunner {

	@Value("${tradeware.batch-url}")
    private String batchUrl;

    public static void main(String[] args) {
        SpringApplication.run(TradewareClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        RestTemplate restTemplate = new RestTemplate();
        

        System.out.println(">>> ИНИЦИИРУЮ ЗАПУСК ETL-ЗАДАЧИ...");

        try {
            // Выполняем POST запрос
            restTemplate.postForEntity(batchUrl, null, String.class);
            System.out.println(">>> СИГНАЛ ОТПРАВЛЕН УСПЕШНО!");
        } catch (Exception e) {
            System.err.println(">>> ОШИБКА ВЫЗОВА: " + e.getMessage());
        }
        
        // Завершаем работу клиента после отправки сигнала
        System.exit(0);
    }
}