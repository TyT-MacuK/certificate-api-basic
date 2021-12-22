package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {
    private static final String ALL_TAGS = "SELECT t FROM Tag t";
    private static final String FIND_BY_NAME = "SELECT t FROM Tag t WHERE t.name = :name";

    private static final String NAME_PARAM = "name";

    private final EntityManager entityManager;

    @Override
    public void add(Tag tag) {
        entityManager.persist(tag);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public List<Tag> findAll() {
        return entityManager.createQuery(ALL_TAGS, Tag.class).getResultList();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        List<Tag> list = entityManager.createQuery(FIND_BY_NAME, Tag.class).setParameter(NAME_PARAM, name).getResultList();
        return list.stream().findAny();
    }
}