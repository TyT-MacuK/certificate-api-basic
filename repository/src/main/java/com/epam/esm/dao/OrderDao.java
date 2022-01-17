package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The interface Order dao.
 */
public interface OrderDao extends JpaRepository<Order, Long> {
}
