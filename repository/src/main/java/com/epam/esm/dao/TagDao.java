package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Tag dao.
 */
@Repository
public interface TagDao extends JpaRepository<Tag, Long> {
    @Query(value = "SELECT t FROM Tag t WHERE t.name = :name")
    Optional<Tag> findByName(@Param("name")String name);

    @Query(value = """
            SELECT tag.id, tag.name FROM Tag
            JOIN gift_certificate_has_tag ON gift_certificate_has_tag.tag_id = tag.id
            JOIN gift_certificate ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id
            JOIN orders ON orders.gift_certificate_id = gift_certificate.id
            JOIN user ON user.id = orders.user_id
            WHERE user.id = :id
            GROUP BY orders.gift_certificate_id
            ORDER BY COUNT(*) DESC LIMIT 1
            """, nativeQuery = true)
    Optional<Tag> findMostWidelyUsedTag(@Param("id") Long userId);
}
