package com.epam.esm.dao.impl;

import com.epam.esm.config.TestDataBaseConfig;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestDataBaseConfig.class)
@Transactional
@TestPropertySource(locations = "classpath:init_test_db.properties")
class TagDaoImplTest {
    @Autowired
    private TagDao tagDao;
    private static Tag expectedTag;
    private static List<Tag> expectedList;

    @BeforeAll
    static void initialize() {
        expectedTag = new Tag();
        expectedTag.setId(1);
        expectedTag.setName("food");

        expectedList = new ArrayList<>();
        expectedList.add(expectedTag);
    }

    @Test
    void addTest() {
        assertDoesNotThrow(() -> tagDao.add(Tag.builder().name("Test").build()));
    }

    @Test
    void findByIdTest() {
        Optional<Tag> optionalActual = tagDao.findById(1L);
        assertEquals(expectedTag, optionalActual.get());
    }

    @Test
    void findAll() {
        List<Tag> actual = tagDao.findAll(1, 1);
        assertEquals(expectedList, actual);
    }

    @Test
    void findByNameTest() {
        Optional<Tag> optionalActual = tagDao.findByName("food");
        Assertions.assertEquals(expectedTag, optionalActual.get());
    }

//    @Test //TODO in H2 this test throw exception, but when this method run in MYSQL it work. It's problem db
//    void findMostWidelyUsedTag() {
//        Optional<Tag> optionalActual = tagDao.findMostWidelyUsedTag(1L);
//        assertEquals(expectedTag, optionalActual.get());
//    }

    @Test
    void update() {
        assertDoesNotThrow(() -> tagDao.update(expectedTag));
    }

    @Test
    void deleteTest() {
        assertDoesNotThrow(() -> tagDao.delete(Tag.builder().name("speed").build()));
    }

    @AfterAll
    static void tierDown() {
        expectedTag = null;
        expectedList = null;
    }
}
