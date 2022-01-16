package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl extends AbstractDao<Long, User> implements UserDao {
    private EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Order> findUserOrderById(long userId, long orderId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(
                criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), orderId),
                        criteriaBuilder.equal(root.join("user").get("id"), userId)));
        Order result;
        try {
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<Order> findUserOrders(long id, int page, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root.get("orders")).where(criteriaBuilder.equal(root.get("id"), id));
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }


    @Override
    protected Class<User> getIdentityClass() {
        return User.class;
    }
}
