package com.epam.esm.service.impl;

import com.epam.esm.converter.OrderConverter;
import com.epam.esm.converter.UserConverter;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserDao userDao;
    @Mock
    private UserConverter userConverter;
    @Mock
    private OrderConverter orderConverter;

    private static User user;
    private static UserDto userDto;
    private static Order order;
    private static OrderDto orderDto;

    @BeforeAll
    static void initialize() {
        MockitoAnnotations.openMocks(GiftCertificateServiceImplTest.class);

        user = new User();
        userDto = UserDto.builder().build();
        order = new Order();
        orderDto = OrderDto.builder().build();
    }

    @Test
    void findByIdTest() throws EntityNotFoundException {
        when(userDao.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userConverter.convertToDto(any(User.class))).thenReturn(userDto);
        UserDto actual = service.findById(1);
        assertEquals(userDto, actual);
    }

    @Test
    void findAllTest() {
        when(userDao.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(user)));
        when(userConverter.convertToDto(any(User.class))).thenReturn(userDto);
        List<UserDto> actual = service.findAll(1, 1);
        assertEquals(List.of(userDto), actual);
    }

    @Test
    void findUserOrderByIdTest() throws EntityNotFoundException {
        when(userDao.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userDao.findUserOrderById(any(Long.class), any(Long.class))).thenReturn(Optional.ofNullable(order));
        when(orderConverter.convertToDto(any(Order.class))).thenReturn(orderDto);
        OrderDto actual = service.findUserOrderById(1, 1);
        assertEquals(orderDto, actual);
    }

    @Test
    void findUserOrdersTest() throws EntityNotFoundException {
        when(userDao.findById(any(Long.class))).thenReturn(Optional.ofNullable(user));
        when(userDao.findUserOrders(any(Long.class), any(PageRequest.class))).thenReturn(List.of(order));
        when(orderConverter.convertToDto(any(Order.class))).thenReturn(orderDto);
        List<OrderDto> actual = service.findUserOrders(1, 1, 1);
        assertEquals(List.of(orderDto), actual);
    }
}
