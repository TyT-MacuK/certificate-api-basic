package com.epam.esm.dao.impl;


import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
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
        assertDoesNotThrow(() -> tagDao.save(Tag.builder().name("Test").build()));
    }

    @Test
    void findByIdTest() {
        Optional<Tag> optionalActual = tagDao.findById(1L);
        assertEquals(expectedTag, optionalActual.get());
    }

    @Test
    void findAll() {
        List<Tag> actual = tagDao.findAll(PageRequest.of(0,1)).stream().toList();
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
        assertDoesNotThrow(() -> tagDao.save(expectedTag));
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
