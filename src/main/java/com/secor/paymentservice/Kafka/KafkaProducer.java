package com.secor.paymentservice.Kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.secor.paymentservice.dto.PaymentResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaProducer
{
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    private static final String PaymentStatusTopic = "payment-status-topic";
    @Autowired //DEPENDENCY INJECTION PROMISE FULFILLED AT RUNTIME
    private KafkaTemplate<String, String> kafkaTemplate ;






    public void publishPaymentStatus(PaymentResponseDto paymentResponseDto) throws JsonProcessingException
    {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String datum =  objectMapper.writeValueAsString(paymentResponseDto);
//
        logger.info(String.format("#### -> Producing message ->"));
        this.kafkaTemplate.send(PaymentStatusTopic, datum);
    }


}
