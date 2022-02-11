package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DataJpaTest
@RunWith(SpringRunner.class)
class OrderDaoImplTest {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    UserDao userDao;
    @Autowired
    GiftCertificateDao certificateDao;

    @Test
    void addTest() {
        Order order = Order.builder().createDate(
                        LocalDateTime.of(2021, 12, 27, 14, 0, 0))
                .cost(BigDecimal.valueOf(10.5))
                .giftCertificate(certificateDao.findById(1L).get())
                .user(userDao.findById(1L).get())
                .build();
        assertDoesNotThrow(() -> orderDao.save(order));
    }
}
