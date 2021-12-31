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

import static com.epam.esm.exception.LocalizationExceptionColumn.ID_LESS_1;
import static com.epam.esm.exception.LocalizationExceptionColumn.ORDER_ID_LESS_1;
import static com.epam.esm.exception.LocalizationExceptionColumn.PAGE_NUMBER_GREATER_999;
import static com.epam.esm.exception.LocalizationExceptionColumn.PAGE_NUMBER_LESS_1;
import static com.epam.esm.exception.LocalizationExceptionColumn.PAGE_SIZE_GREATER_10;
import static com.epam.esm.exception.LocalizationExceptionColumn.PAGE_SIZE_LESS_1;
import static com.epam.esm.exception.LocalizationExceptionColumn.USER_ID_LESS_1;

@RestController
@Validated
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id")
                                            @Min(value = 1, message = ID_LESS_1) long id,
                                            @RequestParam(value = "loc", required = false) String locale) throws EntityNotFoundException {
        setLocale(locale);
        UserDto userDto = userService.findById(id);
        HateoasBuilder.addLinkToUser(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findAll(@RequestParam(name = "page", defaultValue = "1")
                                                 @Min(value = 1, message = PAGE_NUMBER_LESS_1)
                                                 @Max(value = 999, message = PAGE_NUMBER_GREATER_999) int page,
                                                 @RequestParam(name = "page_size", defaultValue = "20")
                                                 @Min(value = 1, message = PAGE_SIZE_LESS_1)
                                                 @Max(value = 10, message = PAGE_SIZE_GREATER_10) int pageSize,
                                                 @RequestParam(value = "loc", required = false) String locale
    ) throws EntityNotFoundException {
        setLocale(locale);
        List<UserDto> userList = userService.findAll(page, pageSize);
        HateoasBuilder.addLinksToUsers(userList);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{user_id}/order/{order_id}")
    public ResponseEntity<OrderDto> findUserOrderById(@PathVariable(name = "user_id")
                                                      @Min(value = 1, message = USER_ID_LESS_1) long userId,
                                                      @PathVariable(name = "order_id")
                                                      @Min(value = 1, message = ORDER_ID_LESS_1) long orderId,
                                                      @RequestParam(value = "loc", required = false) String locale
    ) throws EntityNotFoundException {
        setLocale(locale);
        OrderDto order = userService.findUserOrderById(userId, orderId);
        HateoasBuilder.addLinkToOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> findUserOrders(@PathVariable(name = "id")
                                                         @Min(value = 1, message = ID_LESS_1) long id,
                                                         @RequestParam(name = "page", defaultValue = "1")
                                                         @Min(value = 1, message = PAGE_NUMBER_LESS_1)
                                                         @Max(value = 999, message = PAGE_NUMBER_GREATER_999) int page,
                                                         @RequestParam(name = "page_size", defaultValue = "20")
                                                         @Min(value = 1, message = PAGE_SIZE_LESS_1)
                                                         @Max(value = 10, message = PAGE_SIZE_GREATER_10) int pageSize,
                                                         @RequestParam(value = "loc", required = false) String locale) throws EntityNotFoundException {
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
