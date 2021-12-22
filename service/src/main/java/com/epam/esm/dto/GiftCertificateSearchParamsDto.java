package com.epam.esm.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class GiftCertificateSearchParamsDto extends AbstractDto {
    private List<String> tagNames;
    private String certificateName;
    private String certificateDescription;
    private String orderByName;
    private String orderByCreateDate;
}
