package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.EntityNotFoundException;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Find user by id.
     *
     * @param id the id
     * @return the user dto
     * @throws EntityNotFoundException the entity not found exception
     */
    UserDto findById(long id) throws EntityNotFoundException;

    /**
     * Find all users.
     *
     * @param pageNumber the page number
     * @param pageSize   the page size
     * @return the list
     */
    List<UserDto> findAll(int pageNumber, int pageSize);

    /**
     * Find user order by order id.
     *
     * @param userId  the user id
     * @param orderId the order id
     * @return the order dto
     * @throws EntityNotFoundException the entity not found exception
     */
    OrderDto findUserOrderById(long userId, long orderId) throws EntityNotFoundException;

    /**
     * Find user orders list.
     *
     * @param id       the id
     * @param page     the page
     * @param pageSize the page size
     * @return the list
     * @throws EntityNotFoundException the entity not found exception
     */
    List<OrderDto> findUserOrders(long id, int page, int pageSize) throws EntityNotFoundException;
}
