package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class OrderDaoImpl extends AbstractDao<Long, Order> implements OrderDao {
    private EntityManager entityManager;

    @Autowired
    public OrderDaoImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    protected Class<Order> getIdentityClass() {
        return Order.class;
    }
}
