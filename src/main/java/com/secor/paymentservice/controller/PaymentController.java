package com.secor.paymentservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.secor.paymentservice.dto.PaymentDto;
import com.secor.paymentservice.dto.PaymentResponseDto;
import com.secor.paymentservice.entity.Payment;
import com.secor.paymentservice.mapper.PaymentMapper;
import com.secor.paymentservice.service.PaymentServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments/v1")
public class PaymentController {


    private PaymentServices paymentServices;
    PaymentController(PaymentServices paymentServices)
    {
        this.paymentServices=paymentServices;
    }
    @GetMapping("/all")
    public ResponseEntity<Object> getAllPayments()
    {
  List<Payment> payment=paymentServices.getAllPayment();
        List<PaymentDto> responseDto= PaymentMapper.INSTANCE.toDTOList(payment);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getPayment")
    public ResponseEntity<Object> getPaymentByID(@RequestParam("paymentId") Long paymentId
                                             ) throws JsonProcessingException {
      Payment payment=  paymentServices.getPaymentById(paymentId).get();
      PaymentDto responseDto= PaymentMapper.INSTANCE.toDTO(payment);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getPaymentByOrderId")
    public ResponseEntity<Object> getPaymentByOrderId(@RequestParam("orderId") Long orderId
    ) throws JsonProcessingException {
        Payment payment=  paymentServices.getPaymentByOrderId(orderId).get();
        PaymentDto responseDto= PaymentMapper.INSTANCE.toDTO(payment);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/makePayment")
    public ResponseEntity<Object> createPayment(@RequestBody Payment payment) throws JsonProcessingException {
        PaymentResponseDto responseDto = paymentServices.createPayment(payment);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/updatePayment")
    public ResponseEntity<Object> updatePayment( @RequestBody Payment payment) {
        Payment updatedPayment = paymentServices.updatePayment(payment);
        PaymentDto responseDto= PaymentMapper.INSTANCE.toDTO(updatedPayment);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/deletePayment")
    public ResponseEntity<Void> deletePayment(@RequestParam("paymentId") Long paymentId) {
        paymentServices.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }

}
