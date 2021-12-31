package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao certificateDao;

    @Override
    @Transactional
    public void add(long userId, long giftCertificateId) throws EntityNotFoundException {
        User user = userDao.findById(userId).orElseThrow(EntityNotFoundException::new);
        GiftCertificate giftCertificate = certificateDao.findById(giftCertificateId).orElseThrow(EntityNotFoundException::new);
        Order order = Order.builder()
                .user(user)
                .giftCertificate(giftCertificate)
                .cost(giftCertificate.getPrice())
                .build();
        orderDao.add(order);
    }
}
