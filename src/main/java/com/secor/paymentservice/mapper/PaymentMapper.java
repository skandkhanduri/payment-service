package com.secor.paymentservice.mapper;

import com.secor.paymentservice.dto.PaymentDto;
import com.secor.paymentservice.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);
    PaymentDto toDTO(Payment entity);
    List<PaymentDto> toDTOList(List<Payment> entity);
    Payment toEntity(PaymentDto productDto);
}
