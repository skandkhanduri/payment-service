package com.secor.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.secor.paymentservice.Kafka.KafkaProducer;
import com.secor.paymentservice.dto.PaymentResponseDto;
import com.secor.paymentservice.repository.PaymentRepository;
import com.secor.paymentservice.entity.Payment;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentServices {
    PaymentRepository paymentRepository;
    KafkaProducer kafkaProducer;
    PaymentServices(PaymentRepository paymentRepository, KafkaProducer kafkaProducer)
    {
     this.paymentRepository=paymentRepository;
     this.kafkaProducer=kafkaProducer;
    }
    public List<Payment> getAllPayment()

    {

        return paymentRepository.findAll();
    }

    public Optional <Payment> getPaymentById(Long id)

    {
        Optional<Payment> payment=paymentRepository.findById(id);
        if (payment.isPresent())
        {
            return payment;
        }
        else {
            throw new EntityNotFoundException("Payment with id " + id + " not found");
        }
    }

    public Optional <Payment> getPaymentByOrderId(Long OrderId)

    {
        Optional<Payment> payment=paymentRepository.findPaymentByOrderId(OrderId);
        if (payment.isPresent())
        {
            return payment;
        }
        else {
            throw new EntityNotFoundException("Payment with OrderId " + OrderId + " not found");
        }
    }

    public PaymentResponseDto createPayment(Payment payment) throws JsonProcessingException {
        PaymentResponseDto paymentResponseDto;
        Boolean flag=true;

        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0 || !Objects.equals(payment.getPaymentMethod(), "Card")) {
            flag=false;
        }

     if (flag) {
         payment.setCreatedAt(LocalDateTime.now());
         payment.setUpdatedAt(LocalDateTime.now());
         payment.setStatus("Completed");
         paymentRepository.save(payment);
         paymentResponseDto = new PaymentResponseDto("Payment Completed Successfully", payment.getOrderId().toString(), "Success");
     }
     else
     {
         paymentResponseDto = new PaymentResponseDto("Payment Failed", payment.getOrderId().toString(), "Failed");

     }
     kafkaProducer.publishPaymentStatus(paymentResponseDto);
       return paymentResponseDto;
    }
    public Payment updatePayment(Payment paymentDetails)
    {
        Optional<Payment> optionalProduct=paymentRepository.findById(paymentDetails.getPaymentId());
        if (optionalProduct.isPresent())
        {
            paymentDetails.setCreatedAt(optionalProduct.get().getCreatedAt());
            paymentDetails.setUpdatedAt(LocalDateTime.now());
            return paymentRepository.save(paymentDetails);
        }
        else {
            throw new EntityNotFoundException("Product with id " + paymentDetails.getPaymentId() + " not found");
        }
    }
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
