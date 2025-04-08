package com.example.kafkaproducer.service;

import com.example.kafkaproducer.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public void sendMessage(String content) {
        String messageId = UUID.randomUUID().toString();
        Message message = new Message(messageId, content, LocalDateTime.now());
        
        ListenableFuture<SendResult<String, Message>> future = 
            kafkaTemplate.send(topicName, messageId, message);
        
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Message> result) {
                log.info("Sent message=[{}] with offset=[{}]", message, 
                         result.getRecordMetadata().offset());
            }
            
            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[{}] due to : {}", message, ex.getMessage());
            }
        });
    }
}
