package com.epam.esm.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

import static com.epam.esm.exception.LocalizationExceptionColumn.DESCRIPTION_LENGTH_ERROR;
import static com.epam.esm.exception.LocalizationExceptionColumn.GIFT_CERTIFICATE_NAME_LENGTH_ERROR;
import static com.epam.esm.exception.LocalizationExceptionColumn.ORDER_SORT_BY_CREATE_DATE_PARAM_ERROR;
import static com.epam.esm.exception.LocalizationExceptionColumn.ORDER_SORT_BY_NAME_PARAM_ERROR;

@Data
@RequiredArgsConstructor
public class GiftCertificateSearchParamsDto {
    @Size(min = 2, max = 45, message = GIFT_CERTIFICATE_NAME_LENGTH_ERROR)
    private String certificateName;

    @Size(min = 2, max = 100, message = DESCRIPTION_LENGTH_ERROR)
    private String certificateDescription;

    @Pattern(regexp = "asc|desc|ASC|DESC", message = ORDER_SORT_BY_NAME_PARAM_ERROR)
    private String orderByName;

    @Pattern(regexp = "asc|desc|ASC|DESC", message = ORDER_SORT_BY_CREATE_DATE_PARAM_ERROR)
    private String orderByCreateDate;
    private List<String> tagNames;
}
