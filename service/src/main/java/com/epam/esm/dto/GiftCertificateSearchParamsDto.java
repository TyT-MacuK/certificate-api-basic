package com.epam.esm.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GiftCertificateSearchParamsDto {
    private String certificateName;
    private String certificateDescription;
    private String orderByName;
    private String orderByCreateDate;
    private List<String> tagNames;
}
