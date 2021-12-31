package com.epam.esm.dao.impl;

import com.epam.esm.config.TestDataBaseConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestDataBaseConfig.class)
@Transactional
@TestPropertySource(locations = "classpath:init_test_db.properties")
class GiftCertificateDaoImplTest {
    @Autowired
    private GiftCertificateDao certificateDao;
    private static GiftCertificate expectedCertificateWithoutTags;
    private static GiftCertificate expectedCertificateWithTags;
    private static List<Tag> expectedList;
    private static Tag tagExample;

    @BeforeAll
    static void initialize() {
        expectedCertificateWithoutTags = GiftCertificate.builder()
                .name("photosession")
                .description("beautiful photos on memory")
                .price(new BigDecimal("10.5"))
                .duration(5)
                .lastUpdateDate(LocalDateTime.of(2021, 12, 1, 12, 0, 0))
                .createDate(LocalDateTime.of(2021, 12, 1, 12, 0, 0))
                .tags(new ArrayList<>())
                .build();
        expectedCertificateWithoutTags.setId(1);

        tagExample = new Tag();
        tagExample.setId(1);
        tagExample.setName("food");
        expectedCertificateWithTags = GiftCertificate.builder()

                .name("restaurant")
                .description("delicious food")
                .price(new BigDecimal("17.5"))
                .duration(20)
                .lastUpdateDate(LocalDateTime.of(2021, 5, 5, 15, 0, 0))
                .createDate(LocalDateTime.of(2021, 5, 5, 15, 0, 0))
                .tags(List.of(tagExample))
                .build();
        expectedCertificateWithTags.setId(3);
        initializeExpectedListTags();
    }

    @Test
    void addTest() {
        expectedCertificateWithoutTags.setId(0);
        assertDoesNotThrow(() -> certificateDao.add(expectedCertificateWithoutTags));
    }

    @Test
    void findByIdTest() {
        Optional<GiftCertificate> certificateOptional = certificateDao.findById(3L);
        GiftCertificate actual = certificateOptional.get();
        actual.setTags(List.of(tagExample));
        assertEquals(actual, expectedCertificateWithTags);
    }

    @ParameterizedTest
    @MethodSource("provideFindByParams")
    void findByParams(List<String> tagNames, String certificateName, String certificateDescription,
                      String orderByName, String orderByCreateDate) {
        List<GiftCertificate> actual = certificateDao.findByParams(tagNames, certificateName, certificateDescription,
                orderByName, orderByCreateDate, 1, 1);
        assertEquals(List.of(expectedCertificateWithTags), actual);
    }

    static List<Arguments> provideFindByParams() {
        List<Arguments> testCases = new ArrayList<>();
        testCases.add(Arguments.of(List.of("food"), "staura", "liciou", "asc", "asc"));
        testCases.add(Arguments.of(List.of("food"), "taurant", "liciou", "asc", null));
        testCases.add(Arguments.of(List.of("food"), "aurant", "liciou", null, null));
        testCases.add(Arguments.of(List.of("food"), "restau", null, null, null));
        testCases.add(Arguments.of(List.of("food"), null, null, null, null));
        testCases.add(Arguments.of(null, "restau", null, null, null));
        return testCases;
    }

    @Test
    void updateTest() {
        assertDoesNotThrow(() -> certificateDao.update(expectedCertificateWithoutTags));
    }

    @Test
    void deleteTest() {
        expectedCertificateWithoutTags.setId(0);
        assertDoesNotThrow(() -> certificateDao.delete(expectedCertificateWithoutTags));
    }

    @AfterAll
    static void tierDown() {
        expectedCertificateWithoutTags = null;
        expectedCertificateWithTags = null;
        expectedList = null;
        tagExample = null;
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
