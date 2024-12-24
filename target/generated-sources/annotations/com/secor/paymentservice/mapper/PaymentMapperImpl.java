package com.secor.paymentservice.mapper;

import com.secor.paymentservice.dto.PaymentDto;
import com.secor.paymentservice.entity.Payment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-24T00:19:14+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentDto toDTO(Payment entity) {
        if ( entity == null ) {
            return null;
        }

        PaymentDto paymentDto = new PaymentDto();

        paymentDto.setPaymentId( entity.getPaymentId() );
        paymentDto.setOrderId( entity.getOrderId() );
        paymentDto.setAmount( entity.getAmount() );
        paymentDto.setPaymentMethod( entity.getPaymentMethod() );
        paymentDto.setStatus( entity.getStatus() );
        paymentDto.setCreatedAt( entity.getCreatedAt() );
        paymentDto.setUpdatedAt( entity.getUpdatedAt() );

        return paymentDto;
    }

    @Override
    public List<PaymentDto> toDTOList(List<Payment> entity) {
        if ( entity == null ) {
            return null;
        }

        List<PaymentDto> list = new ArrayList<PaymentDto>( entity.size() );
        for ( Payment payment : entity ) {
            list.add( toDTO( payment ) );
        }

        return list;
    }

    @Override
    public Payment toEntity(PaymentDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Payment payment = new Payment();

        payment.setPaymentId( productDto.getPaymentId() );
        payment.setOrderId( productDto.getOrderId() );
        payment.setAmount( productDto.getAmount() );
        payment.setPaymentMethod( productDto.getPaymentMethod() );
        payment.setStatus( productDto.getStatus() );
        payment.setCreatedAt( productDto.getCreatedAt() );
        payment.setUpdatedAt( productDto.getUpdatedAt() );

        return payment;
    }
}
