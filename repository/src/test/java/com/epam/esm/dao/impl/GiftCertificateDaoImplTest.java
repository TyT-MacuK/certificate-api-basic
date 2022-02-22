package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateSearchSpecificationBuilder;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
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
        expectedCertificateWithoutTags.setId(10);

        tagExample = new Tag();
        tagExample.setId(1);
        tagExample.setName("food");
        expectedCertificateWithTags = GiftCertificate.builder()
                .name("restaurant")
                .description("delicious food")
                .price(new BigDecimal("17.50"))
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
        assertDoesNotThrow(() -> certificateDao.save(expectedCertificateWithoutTags));
    }

    @Test
    void findByIdTest() {
        Optional<GiftCertificate> certificateOptional = certificateDao.findById(3L);
        GiftCertificate actual = certificateOptional.get();
        actual.setTags(List.of(tagExample));
        assertEquals(actual, expectedCertificateWithTags);
    }

    @Test
    void updateTest() {
        assertDoesNotThrow(() -> certificateDao.save(expectedCertificateWithoutTags));
    }

    @Test
    void deleteTest() {
        assertDoesNotThrow(() -> certificateDao.delete(expectedCertificateWithoutTags));
    }

    @ParameterizedTest
    @MethodSource("provideFindByParams")
    void findByParams(Specification<GiftCertificate> specification) {
        List<GiftCertificate> actual = certificateDao.findAll(specification);
        assertEquals(List.of(expectedCertificateWithTags), actual);
    }

    static List<Arguments> provideFindByParams() {
        List<Arguments> testCases = new ArrayList<>();
        testCases.add(Arguments.of(new GiftCertificateSearchSpecificationBuilder()
                .certificateName("restaurant")
                .certificateDescription("delicious food")
                .tagNames(List.of("food"))
                .orderByName("asc")
                .orderByCreateDate("asc")
                .build()));
        testCases.add(Arguments.of(new GiftCertificateSearchSpecificationBuilder()
                .certificateDescription("delicious food")
                .tagNames(List.of("food"))
                .orderByName("asc")
                .orderByCreateDate("asc")
                .build()));
        testCases.add(Arguments.of(new GiftCertificateSearchSpecificationBuilder()
                .certificateName("restaurant")
                .orderByCreateDate("asc")
                .build()));
        return testCases;
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