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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Boolean> addGiftCertificateDto(@RequestBody GiftCertificateDto certificateDto) throws InvalidEntityDataException, EntityAlreadyExistsException {
        logger.log(Level.DEBUG, "method addGiftCertificateDto()");
        return new ResponseEntity<>(service.add(certificateDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> findById(@PathVariable("id") long id) throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method findById()");
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{partOfName}")
    public ResponseEntity<List<GiftCertificateDto>> findByPartOfName(@PathVariable String partOfName) {
        logger.log(Level.DEBUG, "method findByPartOfName()");
        return new ResponseEntity<>(service.findByPartOfName(partOfName), HttpStatus.OK);
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<List<TagDto>> findCertificateTags(@PathVariable long id) throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method findCertificateTags()");
        return new ResponseEntity<>(service.findCertificateTags(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> findAll(@RequestParam("sort") String sort) throws InvalidSortOderNameException {
        logger.log(Level.DEBUG, "method findAll()");
        return new ResponseEntity<>(service.sortCertificate(sort), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Boolean> updateGiftCertificateDto(@PathVariable("id") long id,
                                                            @RequestBody GiftCertificateDto certificateDto)
            throws EntityNotFoundException, InvalidEntityDataException {

        logger.log(Level.DEBUG, "method updateGiftCertificateDto()");
        certificateDto.setId(id);
        return new ResponseEntity<>(service.updateGiftCertificate(certificateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteGiftCertificateDto(@PathVariable("id") long id) throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method deleteGiftCertificateDto()");
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Boolean> attachGiftCertificateAndTag(@RequestParam("certificate_id") long certificateId,
                                                               @RequestParam("tag_id") long tagId) throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method attachGiftCertificateAndTag()");
        return new ResponseEntity<>(service.attach(certificateId, tagId), HttpStatus.CREATED);
    }
}
