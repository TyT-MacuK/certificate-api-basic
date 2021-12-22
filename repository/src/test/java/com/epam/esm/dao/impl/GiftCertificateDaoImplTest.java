package com.epam.esm.dao.impl;

import com.epam.esm.config.TestDataBaseConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.resolver.TestProfileResolver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
    private static GiftCertificate expectedCertificateWithoutTags;
    private static GiftCertificate expectedCertificateWithTags;
    private static List<Tag> expectedList;

    @BeforeAll
    static void initialize() {
        expectedCertificateWithoutTags = GiftCertificate.builder()
                .id(1)
                .name("photosession")
                .description("beautiful photos on memory")
                .price(new BigDecimal("10.5"))
                .duration(5)
                .lastUpdateDate(LocalDateTime.of(2021, 12, 1, 12, 0, 0))
                .createDate(LocalDateTime.of(2021, 12, 1, 12, 0, 0))
                .build();
        expectedCertificateWithTags = GiftCertificate.builder()
                .id(3)
                .name("restaurant")
                .description("delicious food")
                .price(new BigDecimal("17.5"))
                .duration(20)
                .lastUpdateDate(LocalDateTime.of(2021, 5, 5, 15, 0, 0))
                .createDate(LocalDateTime.of(2021, 5, 5, 15, 0, 0))
                .build();
        initializeExpectedListTags();
    }

//    @Test
//    void addTest() {
//        boolean actual = certificateDao.add(expectedCertificateWithoutTags);
//        assertTrue(actual);
//    }

    @Test
    void findByIdTest() {
        Optional<GiftCertificate> certificateOptional = certificateDao.findById(1L);
        Assertions.assertEquals(certificateOptional.get(), expectedCertificateWithoutTags);
    }

//    @ParameterizedTest
//    @MethodSource("provideFindByParams")
//    void findByParams(String tagName, String certificateName, String certificateDescription,
//                      String orderByName, String orderByCreateDate) {
//        List<GiftCertificate> actual = certificateDao.findByParams(tagName, certificateName, certificateDescription,
//                orderByName, orderByCreateDate);
//        assertEquals(List.of(expectedCertificateWithTags), actual);
//    }
//
//    static List<Arguments> provideFindByParams() {
//        List<Arguments> testCases = new ArrayList<>();
//        testCases.add(Arguments.of("aut", "sta", "oo", "asc", "asc"));
//        testCases.add(Arguments.of("aut", "sta", "oo", "asc", null));
//        testCases.add(Arguments.of("aut", "sta", "oo", null, null));
//        testCases.add(Arguments.of("aut", "sta", null, null, null));
//        testCases.add(Arguments.of("aut", null, null, null, null));
//        return testCases;
//    }

    @Test
    void findCertificateTagsTest() {
        List<Tag> actual = certificateDao.findCertificateTags(2L);
        assertEquals(expectedList, actual);
    }

//    @Test
//    void updateTest() {
//        boolean actual = certificateDao.update(expectedCertificateWithoutTags);
//        assertTrue(actual);
//    }
//
//    @Test
//    void deleteTest() {
//        boolean actual = certificateDao.delete(1L);
//        assertTrue(actual);
//    }

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
        expectedCertificateWithoutTags = null;
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
