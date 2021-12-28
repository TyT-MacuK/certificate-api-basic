package com.epam.esm.service;

import com.epam.esm.exception.EntityNotFoundException;

/**
 * The interface Order service.
 */
public interface OrderService {
    /**
     * Make an order.
     *
     * @param userId            the user id
     * @param giftCertificateId the gift certificate id
     * @throws EntityNotFoundException the entity not found exception
     */
    void add(long userId, long giftCertificateId) throws EntityNotFoundException;
}
