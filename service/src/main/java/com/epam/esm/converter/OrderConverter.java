package com.epam.esm.converter;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * The type Order converter.
 */
@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final UserConverter userConverter;
    private final GiftCertificateConverter certificateConverter;

    /**
     * Convert to order.
     *
     * @param dto the order dto
     * @return the order
     */
    public Order convertToEntity(OrderDto dto) {
        return Order.builder()
                .id(dto.getId())
                .cost(dto.getCost())
                .createDate(dto.getCreateDate())
                .user(userConverter.convertToEntity(dto.getUser()))
                .giftCertificate(certificateConverter.convertToEntity(dto.getGiftCertificate()))
                .build();
    }

    /**
     * Convert to order dto.
     *
     * @param order the order
     * @return the order dto
     */
    public OrderDto convertToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .cost(order.getCost())
                .createDate(order.getCreateDate())
                .user(userConverter.convertToDto(order.getUser()))
                .giftCertificate(certificateConverter.convertToDto(order.getGiftCertificate()))
                .build();
    }
}
