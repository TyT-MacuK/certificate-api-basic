package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;
import com.epam.esm.exception.InvalidSortOderNameException;
import com.epam.esm.service.GiftCertificateService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(value = "/certificate", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {
    private static final Logger logger = LogManager.getLogger();
    private final GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addGiftCertificateDto(@RequestBody GiftCertificateDto certificateDto,
                                                         @RequestParam(value = "loc", required = false) String locale)
            throws InvalidEntityDataException, EntityAlreadyExistsException {
        logger.log(Level.DEBUG, "method addGiftCertificateDto()");
        setLocale(locale);
        return new ResponseEntity<>(service.add(certificateDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> findById(@PathVariable("id") long id,
                                                       @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method findById()");
        setLocale(locale);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{partOfName}")
    public ResponseEntity<List<GiftCertificateDto>> findByPartOfName(@PathVariable String partOfName,
                                                                     @RequestParam(value = "loc", required = false) String locale) {
        logger.log(Level.DEBUG, "method findByPartOfName()");
        setLocale(locale);
        return new ResponseEntity<>(service.findByPartOfName(partOfName), HttpStatus.OK);
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<List<TagDto>> findCertificateTags(@PathVariable long id,
                                                            @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method findCertificateTags()");
        setLocale(locale);
        return new ResponseEntity<>(service.findCertificateTags(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> findAll(@RequestParam("sort") String sort,
                                                            @RequestParam(value = "loc", required = false) String locale)
            throws InvalidSortOderNameException {
        logger.log(Level.DEBUG, "method findAll()");
        setLocale(locale);
        return new ResponseEntity<>(service.sortCertificate(sort), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Boolean> updateGiftCertificateDto(@PathVariable("id") long id,
                                                            @RequestParam(value = "loc", required = false) String locale,
                                                            @RequestBody GiftCertificateDto certificateDto)
            throws EntityNotFoundException, InvalidEntityDataException {

        logger.log(Level.DEBUG, "method updateGiftCertificateDto()");
        setLocale(locale);
        certificateDto.setId(id);
        return new ResponseEntity<>(service.updateGiftCertificate(certificateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteGiftCertificateDto(@PathVariable("id") long id,
                                                            @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method deleteGiftCertificateDto()");
        setLocale(locale);
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Boolean> attachGiftCertificateAndTag(@RequestParam("certificate_id") long certificateId,
                                                               @RequestParam("tag_id") long tagId,
                                                               @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method attachGiftCertificateAndTag()");
        setLocale(locale);
        return new ResponseEntity<>(service.attach(certificateId, tagId), HttpStatus.CREATED);
    }

    private void setLocale(String locale) {
        if (locale !=null) {
            LocaleContextHolder.setLocale(new Locale(locale));
        }
    }
}
