package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;
import com.epam.esm.service.TagService;
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
@RequestMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {
    private static final Logger logger = LogManager.getLogger();
    private final TagService service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addTagDto(@RequestBody TagDto tagDto,
                                             @RequestParam(value = "loc", required = false) String locale)
            throws InvalidEntityDataException, EntityAlreadyExistsException {
        logger.log(Level.DEBUG, "method addTagDto()");
        setLocale(locale);
        boolean result = service.add(tagDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagDtoById(@PathVariable("id") long id,
                                                @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method getTagDtoById()");
        setLocale(locale);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{partOfTagName}")
    public ResponseEntity<List<TagDto>> findByPartOfTagName(@PathVariable String partOfTagName,
                                                            @RequestParam(value = "loc", required = false) String locale) {
        logger.log(Level.DEBUG, "method findByPartOfTagName()");
        setLocale(locale);
        return new ResponseEntity<>(service.findByPartOfName(partOfTagName), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id,
                                          @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method delete()");
        setLocale(locale);
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @GetMapping("/certificates/{id}")
    public ResponseEntity<List<GiftCertificateDto>> findTagCertificates(@PathVariable long id,
                                                                        @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method findTagCertificates()");
        setLocale(locale);
        return new ResponseEntity<>(service.findTagCertificates(id), HttpStatus.OK);
    }

    private void setLocale(String locale) {
        if (locale !=null) {
            LocaleContextHolder.setLocale(new Locale(locale));
        }
    }
}
