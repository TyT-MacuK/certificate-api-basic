package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Relation(collectionRelation = "content", itemRelation = "giftCertificate")
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    private long id;

    @Size(min = 2, max = 45, message = "{gift_certificate_name_length_error}")
    private String name;

    @Size(min = 2, max = 100, message = "{description_length_error}")
    private String description;
    private BigDecimal price;
    private int duration;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "UTC")
    private LocalDateTime createDay;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "UTC")
    private LocalDateTime lastUpdateDay;

    private List<TagDto> tags;
}
