package com.epam.esm.controllers;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;
import com.epam.esm.hateoas.HateoasBuilder;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Locale;

import static com.epam.esm.exception.LocalizationExceptionColumn.ID_LESS_1;
import static com.epam.esm.exception.LocalizationExceptionColumn.PAGE_NUMBER_GREATER_999;
import static com.epam.esm.exception.LocalizationExceptionColumn.PAGE_NUMBER_LESS_1;
import static com.epam.esm.exception.LocalizationExceptionColumn.PAGE_SIZE_GREATER_10;
import static com.epam.esm.exception.LocalizationExceptionColumn.PAGE_SIZE_LESS_1;

@RestController
@Validated
@RequestMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TagController {
    private final TagService service;

    @PostMapping("/add")
    public ResponseEntity<Void> addTag(@RequestBody @Valid TagDto tagDto,
                                       @RequestParam(value = "loc", required = false) String locale)
            throws InvalidEntityDataException, EntityAlreadyExistsException {
        setLocale(locale);
        service.add(tagDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> findTagById(@PathVariable("id")
                                              @Min(value = 1, message = ID_LESS_1)
                                                      long id,
                                              @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        setLocale(locale);
        TagDto tagDto = service.findById(id);
        HateoasBuilder.addLinkToTag(tagDto);
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TagDto>> findAll(@RequestParam(name = "page", defaultValue = "1")
                                                @Min(value = 1, message = PAGE_NUMBER_LESS_1)
                                                @Max(value = 999, message = PAGE_NUMBER_GREATER_999) int page,
                                                @RequestParam(name = "page_size", defaultValue = "5")
                                                @Min(value = 1, message = PAGE_SIZE_LESS_1)
                                                @Max(value = 10, message = PAGE_SIZE_GREATER_10) int pageSize,
                                                @RequestParam(value = "loc", required = false) String locale
    ) throws EntityNotFoundException {
        setLocale(locale);
        List<TagDto> tagList = service.findAll(page, pageSize);
        HateoasBuilder.addLinksToTags(tagList);
        return new ResponseEntity<>(tagList, HttpStatus.OK);
    }

    @GetMapping("/widely/{id}")
    public ResponseEntity<TagDto> findMostWidelyUsedTag(@PathVariable("id")
                                                        @Min(value = 1, message = ID_LESS_1) long id,
                                                        @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        setLocale(locale);
        TagDto tag = service.findMostWidelyUsedTag(id);
        HateoasBuilder.addLinkToTag(tag);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id")
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
