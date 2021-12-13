package com.epam.esm.dao.impl;

import com.epam.esm.config.TestDataBaseConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.resolver.TestProfileResolver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDataBaseConfig.class)
@ActiveProfiles(resolver = TestProfileResolver.class)
@Transactional
class GiftCertificateDaoImplTest {
    @Autowired
    private GiftCertificateDao certificateDao;
    private static GiftCertificate expectedCertificate;
    private static List<Tag> expectedList;

    @BeforeAll
    static void initialize() {
        expectedCertificate = new GiftCertificate.Builder()
                .setId(1)
                .setName("photosession")
                .setDescription("beautiful photos on memory")
                .setPrice(new BigDecimal(10.5))
                .setDuration(5)
                .setLastUpdateDate(LocalDateTime.of(2021, 12, 01, 12, 00, 00))
                .setCreateDate(LocalDateTime.of(2021, 12, 01, 12, 00, 00))
                .build();
        initializeExpectedListTags();
    }

    @Test
    void addTest() {
        boolean actual = certificateDao.add(expectedCertificate);
        assertTrue(actual);
    }

    @Test
    void findByIdTest() {
        Optional<GiftCertificate> certificateOptional = certificateDao.findById(1L);
        assertEquals(certificateOptional.get(), expectedCertificate);
    }

    @Test
    void findCertificateTagsTest() {
        List<Tag> actual = certificateDao.findCertificateTags(2L);
        assertEquals(expectedList, actual);
    }

    @Test
    void findByPartOfNameTest() {
        List<GiftCertificate> actual = certificateDao.findByPartOfName("hotosession");
        List<GiftCertificate> expected = List.of(expectedCertificate);
        assertEquals(expected, actual);
    }

    @Test
    void sortCertificateTest() {
        List<GiftCertificate> actual = certificateDao.sortCertificate("asc");
        assertTrue(true);
    }

    @Test
    void updateTest() {
        boolean actual = certificateDao.update(expectedCertificate);
        assertTrue(actual);
    }

    @Test
    void deleteTest() {
        boolean actual = certificateDao.delete(1L);
        assertTrue(actual);
    }

    @Test
    void detachAllTagsTest() {
        boolean actual = certificateDao.detachAllTags(3L);
        assertTrue(actual);
    }

    @Test
    void attach() {
        boolean actual = certificateDao.attach(1L, 3L);
        assertTrue(actual);
    }

    @AfterAll
    static void tierDown() {
        expectedCertificate = null;
        expectedList = null;
    }

    private static void initializeExpectedListTags() {
        expectedList = new ArrayList<>();
        Tag firstTag = new Tag();
        firstTag.setId(2);
        firstTag.setName("health");
        Tag secondTag = new Tag();
        secondTag.setId(3);
        secondTag.setName("beauty");
        expectedList.add(firstTag);
        expectedList.add(secondTag);
    }
}
