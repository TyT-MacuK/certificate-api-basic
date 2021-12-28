package com.epam.esm.dao.impl;

import com.epam.esm.config.TestDataBaseConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestDataBaseConfig.class)
@Transactional
@TestPropertySource(locations = "classpath:init_test_db.properties")
class UserDaoTest {
    @Autowired
    UserDao userDao;
    @Autowired
    GiftCertificateDao certificateDao;
    private static User expectedUserWithoutOrder;

    @BeforeAll
    static void initialize() {
        expectedUserWithoutOrder = User.builder().id(2).name("Tom").orders(new ArrayList<>()).build();
    }

    @Test
    void findByIdTest() {
        User actual = userDao.findById(2L).get();
        assertEquals(expectedUserWithoutOrder, actual);
    }

    @Test
    void findAllTest() {
        List<User> actual = userDao.findAll(2, 1);
        assertEquals(List.of(expectedUserWithoutOrder), actual);
    }

    @Test
    void findUserOrderByIdTest() {
        Order expectedOrder = Order.builder().id(1).createDate(
                        LocalDateTime.of(2021, 12, 27, 14, 0, 0))
                .cost(BigDecimal.valueOf(10.5))
                .giftCertificate(certificateDao.findById(1L).get())
                .user(userDao.findById(1L).get())
                .build();
        Order actual = userDao.findUserOrderById(1, 1).get();
        assertEquals(expectedOrder, actual);
    }

    @Test
    void findUserOrdersTest() {
        Order expectedOrder = Order.builder().id(1).createDate(
                        LocalDateTime.of(2021, 12, 27, 14, 0, 0))
                .cost(BigDecimal.valueOf(10.5))
                .giftCertificate(certificateDao.findById(1L).get())
                .user(userDao.findById(1L).get())
                .build();
        List<Order> expected = new ArrayList<>();
        expected.add(expectedOrder);

        List<Order> actual = userDao.findUserOrders(1,1, 1);
        assertEquals(expected, actual);
    }

    @AfterAll
    static void tierDown() {
        expectedUserWithoutOrder = null;
    }
}
