package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderDao orderDao;
    @Mock
    private UserDao userDao;
    @Mock
    private GiftCertificateDao certificateDao;

    private static User user;
    private static GiftCertificate giftCertificate;
    private static Order order;

    @BeforeAll
    static void initialize() {
        MockitoAnnotations.openMocks(GiftCertificateServiceImplTest.class);

        user = new User();
        giftCertificate = new GiftCertificate();
        order = Order.builder().user(user).giftCertificate(giftCertificate).build();
    }

    @Test
    void addTest() throws EntityNotFoundException {
        when(userDao.findById(any(Long.class))).thenReturn(Optional.ofNullable(user));
        when(certificateDao.findById(any(Long.class))).thenReturn(Optional.ofNullable(giftCertificate));
        orderService.add(user.getId(), giftCertificate.getId());
        verify(orderDao).save(order);
    }
}
