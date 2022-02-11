package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The interface Order dao.
 */
@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
}
