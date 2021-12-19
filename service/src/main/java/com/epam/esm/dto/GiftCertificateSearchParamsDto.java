package com.epam.esm.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GiftCertificateSearchParamsDto extends AbstractDto {
    private String tagName;
    private String certificateName;
    private String certificateDescription;
    private String orderByName;
    private String orderByCreateDate;
}
