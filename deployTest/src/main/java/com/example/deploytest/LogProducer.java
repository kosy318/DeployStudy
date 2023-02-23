package com.example.deploytest;

@Service
public class LogProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.log}")
    private String topic;

    public LogProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String message) {
        kafkaTemplate.send(topic, message);
    }
}
