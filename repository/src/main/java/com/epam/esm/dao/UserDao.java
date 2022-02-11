package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface User dao.
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

    /**
     * Find user order by id optional.
     *
     * @param id      the user id
     * @param orderId the order id
     * @return the optional order
     */
    @Query(value = "SELECT o FROM Order o WHERE o.user.id = :id AND o.id = :orderId")
    Optional<Order> findUserOrderById(@Param("id") long id, @Param("orderId") long orderId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :id")
    List<Order> findUserOrders(@Param("id") long id, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.name = :name")
    Optional<User> findByName(@Param("name") String name);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean existByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.name = :name")
    boolean existByName(@Param("name") String name);
}
