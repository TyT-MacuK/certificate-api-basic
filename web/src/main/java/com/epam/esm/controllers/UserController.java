package com.epam.esm.controllers;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.hateoas.HateoasBuilder;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Locale;

@RestController
@Validated
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> findById(@PathVariable("id")
                                            @Min(value = 1, message = "{id_less_1}") long id,
                                            @RequestParam(value = "loc", required = false) String locale) throws EntityNotFoundException {
        setLocale(locale);
        UserDto userDto = userService.findById(id);
        HateoasBuilder.addLinkToUser(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> findAll(@RequestParam(name = "page", defaultValue = "1")
                                                 @Min(value = 1, message = "{page_number_less_1}")
                                                 @Max(value = 999, message = "{page_number_greater_999}") int page,
                                                 @RequestParam(name = "page_size", defaultValue = "5")
                                                 @Min(value = 1, message = "{page_size_less_1}")
                                                 @Max(value = 10, message = "{page_size_greater_10}") int pageSize,
                                                 @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        setLocale(locale);
        List<UserDto> userList = userService.findAll(page, pageSize);
        HateoasBuilder.addLinksToUsers(userList);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{user_id}/order/{order_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #userId == authentication.principal.id)")
    public ResponseEntity<OrderDto> findUserOrderById(@PathVariable(name = "user_id")
                                                      @Min(value = 1, message = "{id_less_1}") long userId,
                                                      @PathVariable(name = "order_id")
                                                      @Min(value = 1, message = "{order_id_less_1}") long orderId,
                                                      @RequestParam(value = "loc", required = false) String locale
    ) throws EntityNotFoundException {
        setLocale(locale);
        OrderDto order = userService.findUserOrderById(userId, orderId);
        HateoasBuilder.addLinkToOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/{id}/orders")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #id == authentication.principal.id)")
    public ResponseEntity<List<OrderDto>> findUserOrders(@PathVariable(name = "id")
                                                         @Min(value = 1, message = "{id_less_1}") long id,
                                                         @RequestParam(name = "page", defaultValue = "1")
                                                         @Min(value = 1, message = "{page_number_less_1}")
                                                         @Max(value = 999, message = "{page_number_greater_999}") int page,
                                                         @RequestParam(name = "page_size", defaultValue = "5")
                                                         @Min(value = 1, message = "{page_size_less_1}")
                                                         @Max(value = 10, message = "{page_size_greater_10}") int pageSize,
                                                         @RequestParam(value = "loc", required = false) String locale)
            throws EntityNotFoundException {
        setLocale(locale);
        List<OrderDto> orderList = userService.findUserOrders(id, page, pageSize);
        HateoasBuilder.addLinksToOrders(orderList);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    private void setLocale(String locale) {
        if (locale != null) {
            LocaleContextHolder.setLocale(new Locale(locale));
        }
    }
}
