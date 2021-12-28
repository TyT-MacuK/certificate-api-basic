package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface User dao.
 */
public interface UserDao extends CrudDao<Long, User> {

    /**
     * Find user order by id optional.
     *
     * @param id      the id
     * @param orderId the order id
     * @return the optional order
     */
    Optional<Order> findUserOrderById(long id, long orderId);

    /**
     * Find user orders list.
     *
     * @param id       the id
     * @param page     the page
     * @param pageSize the page size
     * @return the list of orders
     */
    List<Order> findUserOrders(long id, int page, int pageSize);
}
