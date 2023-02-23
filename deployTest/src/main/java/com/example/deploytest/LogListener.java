package com.example.deploytest;

@Service
public class LogListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogListener.class);

    @KafkaListener(topics = "${kafka.topic.log}")
    public void consume(String message) {
        LOGGER.info("Consumed log message: {}", message);
    }
}
