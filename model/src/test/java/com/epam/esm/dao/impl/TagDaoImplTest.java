package com.epam.esm.dao.impl;

import com.epam.esm.config.TestDataBaseConfig;
import com.epam.esm.dao.TagDao;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDataBaseConfig.class)
@ActiveProfiles(resolver = TestProfileResolver.class)
@Transactional
class TagDaoImplTest {
    @Autowired
    private TagDao tagDao;
    private static Tag expectedTag;
    private static List<GiftCertificate> expectedList;

    @BeforeAll
    static void initialize() {
        expectedTag = new Tag();
        expectedTag.setId(1);
        expectedTag.setName("food");

        GiftCertificate expectedCertificate = new GiftCertificate.Builder()
                .setId(2)
                .setName("spa")
                .setDescription("relax")
                .setPrice(new BigDecimal(40))
                .setDuration(30)
                .setLastUpdateDate(LocalDateTime.of(2021, 01, 10, 14, 00, 00))
                .setCreateDate(LocalDateTime.of(2021, 01, 10, 14, 00, 00))
                .build();
        expectedList = new ArrayList<>();
        expectedList.add(expectedCertificate);
    }

    @Test
    void addTest() {
        boolean actual = tagDao.add(expectedTag);
        assertTrue(actual);
    }

    @Test
    void findByIdTest() {
        Optional<Tag> optionalActual = tagDao.findById(1L);
        assertEquals(expectedTag, optionalActual.get());
    }

//    @Test//TODO
//    void findByPartOfNameTest() {
//        List<Tag> tagList = tagDao.findByPartOfName("oo");
//        List<Tag> expected = Arrays.asList(expectedTag);
//        assertEquals(expected, tagList);
//    }

    @Test
    void deleteTest() {
        boolean actual = tagDao.delete(4L);
        assertTrue(actual);
    }

    @Test
    void findByNameTest() {
        Optional<Tag> optionalActual = tagDao.findByName("food");
        assertEquals(expectedTag, optionalActual.get());
    }

    @Test
    void findTagCertificatesTest() {
        List<GiftCertificate> actual = tagDao.findTagCertificates(2L);
        assertEquals(expectedList, actual);
    }

    @Test
    void detachAllCertificatesTest() {
        boolean actual = tagDao.detachAllCertificates(1L);
        assertTrue(actual);
    }

    @AfterAll
    static void tierDown() {
        expectedTag = null;
        expectedList = null;
    }
}
