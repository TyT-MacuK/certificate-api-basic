package com.epam.esm.controllers;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Log4j2
public class TagController {
    private final TagService service;

    @PostMapping("/add")
    public ResponseEntity<Boolean> addTagDto(@RequestBody TagDto tagDto,
                                             @RequestParam(value = "loc", required = false) String locale)
            throws InvalidEntityDataException, EntityAlreadyExistsException {
        log.log(Level.DEBUG, "method addTagDto()");
        setLocale(locale);
        service.add(tagDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagDtoById(@PathVariable("id") long id,
                                                @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        log.log(Level.DEBUG, "method getTagDtoById()");
        setLocale(locale);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TagDto>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id,
                                          @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        log.log(Level.DEBUG, "method delete()");
        setLocale(locale);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void setLocale(String locale) {
        if (locale !=null) {
            LocaleContextHolder.setLocale(new Locale(locale));
        }
    }
}
