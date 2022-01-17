package com.epam.esm.service.impl;

import com.epam.esm.converter.OrderConverter;
import com.epam.esm.converter.UserConverter;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserConverter userConverter;
    private final OrderConverter orderConverter;

    @Override
    public UserDto findById(long id) throws EntityNotFoundException {
        Optional<User> user = userDao.findById(id);
        return user.map(userConverter::convertToDto).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<UserDto> findAll(int pageNumber, int pageSize) {
        return userDao.findAll(PageRequest.of(pageNumber - 1, pageSize)).stream().map(userConverter::convertToDto).toList();
    }

    @Override
    @Transactional
    public OrderDto findUserOrderById(long userId, long orderId) throws EntityNotFoundException {
        userDao.findById(userId).orElseThrow(EntityNotFoundException::new);
        Order order = userDao.findUserOrderById(userId, orderId).orElseThrow(EntityNotFoundException::new);
        return orderConverter.convertToDto(order);
    }

    @Override
    @Transactional
    public List<OrderDto> findUserOrders(long id, int pageNumber, int pageSize) throws EntityNotFoundException {
        userDao.findById(id).orElseThrow(EntityNotFoundException::new);
        return userDao.findUserOrders(id, PageRequest.of(pageNumber - 1, pageSize)).stream().map(orderConverter::convertToDto).toList();
    }
}
