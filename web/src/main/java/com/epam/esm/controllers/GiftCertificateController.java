package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;
import com.epam.esm.hateoas.HateoasBuilder;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Locale;

import static com.epam.esm.exception.LocalizationExceptionColumn.DESCRIPTION_LENGTH_ERROR;
import static com.epam.esm.exception.LocalizationExceptionColumn.GIFT_CERTIFICATE_NAME_LENGTH_ERROR;
import static com.epam.esm.exception.LocalizationExceptionColumn.ID_LESS_1;
import static com.epam.esm.exception.LocalizationExceptionColumn.ORDER_SORT_BY_CREATE_DATE_PARAM_ERROR;
import static com.epam.esm.exception.LocalizationExceptionColumn.ORDER_SORT_BY_NAME_PARAM_ERROR;
import static com.epam.esm.exception.LocalizationExceptionColumn.PAGE_NUMBER_GREATER_999;
import static com.epam.esm.exception.LocalizationExceptionColumn.PAGE_NUMBER_LESS_1;
import static com.epam.esm.exception.LocalizationExceptionColumn.PAGE_SIZE_GREATER_10;
import static com.epam.esm.exception.LocalizationExceptionColumn.PAGE_SIZE_LESS_1;

@RestController
@Validated
@RequestMapping(value = "/certificate", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GiftCertificateController {
    private static final String SORT_ORDER_REGEX = "asc|desc|ASC|DESC";
    private final GiftCertificateService service;

    @PostMapping("/add")
    public ResponseEntity<Void> addGiftCertificate(@RequestBody @Valid GiftCertificateDto certificateDto,
                                                   @RequestParam(value = "loc", required = false) String locale)
            throws InvalidEntityDataException, EntityAlreadyExistsException {
        setLocale(locale);
        service.add(certificateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> findById(@PathVariable("id")
                                                       @Min(value = 1, message = ID_LESS_1) long id,
                                                       @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        setLocale(locale);
        GiftCertificateDto certificateDto = service.findById(id);
        HateoasBuilder.addLinkToGiftCertificate(certificateDto);
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GiftCertificateDto>> findAll(@RequestParam(name = "page", defaultValue = "1")
                                                            @Min(value = 1, message = PAGE_NUMBER_LESS_1)
                                                            @Max(value = 999, message = PAGE_NUMBER_GREATER_999) int page,
                                                            @RequestParam(name = "page_size", defaultValue = "5")
                                                            @Min(value = 1, message = PAGE_SIZE_LESS_1)
                                                            @Max(value = 10, message = PAGE_SIZE_GREATER_10) int pageSize,
                                                            @RequestParam(value = "loc", required = false) String locale) throws EntityNotFoundException {
        setLocale(locale);
        List<GiftCertificateDto> certificateDtoList = service.findAll(page, pageSize);
        HateoasBuilder.addLinksToGiftCertificates(certificateDtoList);
        return new ResponseEntity<>(certificateDtoList, HttpStatus.OK);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<GiftCertificateDto>> findByParams(
            @RequestParam(name = "certificate_name", required = false)
            @Size(min = 2, max = 45, message = GIFT_CERTIFICATE_NAME_LENGTH_ERROR) String certificateName,

            @RequestParam(name = "certificate_description", required = false)
            @Size(min = 2, max = 100, message = DESCRIPTION_LENGTH_ERROR) String certificateDescription,

            @RequestParam(name = "order_by_name", required = false)
            @Pattern(regexp = SORT_ORDER_REGEX, message = ORDER_SORT_BY_NAME_PARAM_ERROR) String orderByName,

            @RequestParam(name = "order_by_create_date", required = false)
            @Pattern(regexp = SORT_ORDER_REGEX, message = ORDER_SORT_BY_CREATE_DATE_PARAM_ERROR) String orderByCreateDate,

            @RequestParam(name = "tags", required = false) List<String> tagNames,

            @RequestParam(name = "page", defaultValue = "1")
            @Min(value = 1, message = PAGE_NUMBER_LESS_1)
            @Max(value = 999, message = PAGE_NUMBER_GREATER_999) int page,

            @RequestParam(name = "page_size", defaultValue = "5")
            @Min(value = 1, message = PAGE_SIZE_LESS_1)
            @Max(value = 10, message = PAGE_SIZE_GREATER_10) int pageSize,

            @RequestParam(value = "loc", required = false) String locale) throws InvalidEntityDataException, EntityNotFoundException {
        setLocale(locale);
        GiftCertificateSearchParamsDto searchParams = GiftCertificateSearchParamsDto.builder()
                .certificateName(certificateName)
                .certificateDescription(certificateDescription)
                .orderByName(orderByName)
                .orderByCreateDate(orderByCreateDate)
                .tagNames(tagNames)
                .build();

        List<GiftCertificateDto> certificateDtoList = service.findByParams(searchParams, page, pageSize);
        HateoasBuilder.addLinksToGiftCertificates(certificateDtoList);
        return new ResponseEntity<>(certificateDtoList, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateGiftCertificate(@PathVariable("id")
                                                      @Min(value = 1, message = ID_LESS_1) long id,
                                                      @RequestParam(value = "loc", required = false) String locale,
                                                      @RequestBody @Valid GiftCertificateDto certificateDto)
            throws EntityNotFoundException, InvalidEntityDataException {
        setLocale(locale);
        certificateDto.setId(id);
        service.updateGiftCertificate(certificateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable("id")
                                                      @Min(value = 1, message = ID_LESS_1) long id,
                                                      @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        setLocale(locale);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void setLocale(String locale) {
        if (locale != null) {
            LocaleContextHolder.setLocale(new Locale(locale));
        }
    }
}
