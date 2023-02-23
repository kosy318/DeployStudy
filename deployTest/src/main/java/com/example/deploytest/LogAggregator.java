package com.example.deploytest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LogAggregator {

    private final Map<String, List<String>> logs = new HashMap<>();

    @KafkaListener(topics = "logs")
    public void processLogMessage(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();
        List<String> messages = logs.getOrDefault(key, new ArrayList<>());
        messages.add(value);
        logs.put(key, messages);
        System.out.println("Received log message: " + value);
    }

    @Scheduled(fixedDelay = 5000)
    public void printLogs() {
        System.out.println("Aggregated logs:");
        logs.forEach((key, value) -> {
            System.out.println("Key: " + key);
            System.out.println("Messages: " + value);
        });
    }
}