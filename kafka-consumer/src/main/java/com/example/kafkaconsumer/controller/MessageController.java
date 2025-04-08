package com.example.kafkaconsumer.controller;

import com.example.kafkaconsumer.model.Message;
import com.example.kafkaconsumer.service.KafkaConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final KafkaConsumerService consumerService;

    @GetMapping
    public ResponseEntity<List<Message>> getReceivedMessages() {
        return ResponseEntity.ok(consumerService.getReceivedMessages());
    }
}
