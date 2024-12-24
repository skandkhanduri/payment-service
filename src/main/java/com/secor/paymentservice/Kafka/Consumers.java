package com.secor.paymentservice.Kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secor.paymentservice.dto.PaymentDto;
import com.secor.paymentservice.entity.Payment;
import com.secor.paymentservice.mapper.PaymentMapper;
import com.secor.paymentservice.service.PaymentServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class Consumers {

    private PaymentServices paymentService;
    //KafkaProducer kafkaProducer;
    Consumers(PaymentServices paymentService)
    {
        this.paymentService=paymentService;
       // this.kafkaProducer= kafkaProducer;
    }

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics = "payment-initiation-topic",groupId = "1")
    public void handleInventoryReserved(String responseJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert the JSON string into an InventoryResponseDto object
        PaymentDto response = objectMapper.readValue(responseJson, PaymentDto.class);
        Payment payment= PaymentMapper.INSTANCE.toEntity(response);
        paymentService.createPayment(payment);
        }
    }


