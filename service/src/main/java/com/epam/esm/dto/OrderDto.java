package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Relation(collectionRelation = "content", itemRelation = "order")
public class OrderDto extends RepresentationModel<OrderDto> {
    @NotNull(message = "{user_id_null}")
    @Min(value = 1, message = "{user_id_less_1}")
    private long id;

    private BigDecimal cost;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "UTC")
    private LocalDateTime createDate;

    private UserDto user;

    private GiftCertificateDto giftCertificate;
}
