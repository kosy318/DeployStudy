package com.example.deploytest;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public LogController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/logs")
    public void sendLogMessage(@RequestBody String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>("logs", message);
        kafkaTemplate.send(record);
    }
}