package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDao extends JpaRepository<GiftCertificate, Long>,
        JpaSpecificationExecutor<GiftCertificate> {
}
