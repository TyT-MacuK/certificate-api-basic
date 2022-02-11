package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
class UserDaoTest {
    @Autowired
    UserDao userDao;
    @Autowired
    GiftCertificateDao certificateDao;
    private static User expectedUserWithoutOrder;

    @BeforeAll
    static void initialize() {
        expectedUserWithoutOrder = User.builder().name("Tom").orders(new ArrayList<>()).build();
        expectedUserWithoutOrder.setId(2);
    }

    @Test
    void findByIdTest() {
        User actual = userDao.findById(2L).get();
        assertEquals(expectedUserWithoutOrder, actual);
    }

    @Test
    void findAllTest() {
        List<User> actual = userDao.findAll(PageRequest.of(1,1)).stream().toList();
        assertEquals(List.of(expectedUserWithoutOrder), actual);
    }

    @Test
    void findUserOrderByIdTest() {
        Order expectedOrder = Order.builder().createDate(
                        LocalDateTime.of(2021, 12, 27, 14, 0, 0))
                .cost(new BigDecimal("10.50"))
                .giftCertificate(certificateDao.findById(1L).get())
                .user(userDao.findById(1L).get())
                .build();
        expectedOrder.setId(1);
        Order actual = userDao.findUserOrderById(1, 1).get();
        assertEquals(expectedOrder, actual);
    }

    @Test
    void findUserOrdersTest() {
        Order expectedOrder = Order.builder().createDate(
                        LocalDateTime.of(2021, 12, 27, 14, 0, 0))
                .cost(new BigDecimal("10.50"))
                .giftCertificate(certificateDao.findById(1L).get())
                .user(userDao.findById(1L).get())
                .build();
        expectedOrder.setId(1);
        List<Order> expected = new ArrayList<>();
        expected.add(expectedOrder);

        List<Order> actual = userDao.findUserOrders(1,PageRequest.of(0,1));
        assertEquals(expected, actual);
    }

    @Test
    void findByNameTest() {
        User actual = userDao.findByName("Tom").get();
        assertEquals(expectedUserWithoutOrder, actual);
    }

    @ParameterizedTest
    @MethodSource("provideExistByEmail")
    void existByEmailTest(String email, boolean expected) {
        boolean actual = userDao.existByEmail(email);
        assertEquals(expected, actual);
    }

    static List<Arguments> provideExistByEmail() {
        List<Arguments> testCases = new ArrayList<>();
        testCases.add(Arguments.of("jack@gmail.com", true));
        testCases.add(Arguments.of("fake_email@gmail.com", false));
        return testCases;
    }

    @ParameterizedTest
    @MethodSource("provideExistByName")
    void existByNameTest(String name, boolean expected) {
        boolean actual = userDao.existByName(name);
        assertEquals(expected, actual);
    }

    static List<Arguments> provideExistByName() {
        List<Arguments> testCases = new ArrayList<>();
        testCases.add(Arguments.of("Jack", true));
        testCases.add(Arguments.of("fake_name", false));
        return testCases;
    }

    @AfterAll
    static void tierDown() {
        expectedUserWithoutOrder = null;
    }
}
