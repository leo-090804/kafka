package com.example.kafkaconsumer.service;

import com.example.kafkaconsumer.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class KafkaConsumerService {

    private final List<Message> receivedMessages = new ArrayList<>();

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Message message) {
        log.info("Message received: {}", message);
        receivedMessages.add(message);
    }

    public List<Message> getReceivedMessages() {
        return new ArrayList<>(receivedMessages);
    }
}
