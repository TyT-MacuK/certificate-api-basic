package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.AttachException;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping(value = "/certificate", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GiftCertificateController {
    private final GiftCertificateService service;

    @PostMapping("/add")
    public ResponseEntity<Boolean> addGiftCertificateDto(@RequestBody GiftCertificateDto certificateDto,
                                                         @RequestParam(value = "loc", required = false) String locale)
            throws InvalidEntityDataException, EntityAlreadyExistsException {
        setLocale(locale);
        return new ResponseEntity<>(service.add(certificateDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> findById(@PathVariable("id") long id,
                                                       @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        setLocale(locale);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("/sort")
    public ResponseEntity<Map<GiftCertificateDto, List<TagDto>>> findByParams(
            @RequestBody GiftCertificateSearchParamsDto searchParams) throws InvalidEntityDataException {
        return new ResponseEntity<>(service.findByParams(searchParams), HttpStatus.OK);
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<List<TagDto>> findCertificateTags(@PathVariable long id,
                                                            @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        setLocale(locale);
        return new ResponseEntity<>(service.findCertificateTags(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Boolean> updateGiftCertificateDto(@PathVariable("id") long id,
                                                            @RequestParam(value = "loc", required = false) String locale,
                                                            @RequestBody GiftCertificateDto certificateDto)
            throws EntityNotFoundException, InvalidEntityDataException {
        setLocale(locale);
        certificateDto.setId(id);
        return new ResponseEntity<>(service.updateGiftCertificate(certificateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteGiftCertificateDto(@PathVariable("id") long id,
                                                            @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        setLocale(locale);
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Boolean> attachGiftCertificateAndTag(@RequestParam("certificate_id") long certificateId,
                                                               @RequestParam("tag_id") long tagId,
                                                               @RequestParam(value = "loc", required = false) String locale)
            throws AttachException {
        setLocale(locale);
        return new ResponseEntity<>(service.attach(certificateId, tagId), HttpStatus.CREATED);
    }

    private void setLocale(String locale) {
        if (locale != null) {
            LocaleContextHolder.setLocale(new Locale(locale));
        }
    }
}
