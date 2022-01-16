package com.epam.esm.controllers;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Locale;

@RestController
@Validated
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #userId == authentication.principal.id)")
    public ResponseEntity<Void> makeOrder(@RequestParam(name = "user_id")
                                          @Min(value = 1, message = "{id_less_1}") long userId,
                                          @RequestParam(name = "certificate_id")
                                          @Min(value = 1, message = "{gift_certificate_id_less_1}") long giftCertificateId,
                                          @RequestParam(value = "loc", required = false) String locale) throws EntityNotFoundException {
        setLocale(locale);
        orderService.add(userId, giftCertificateId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private void setLocale(String locale) {
        if (locale != null) {
            LocaleContextHolder.setLocale(new Locale(locale));
        }
    }

}
