package com.encentral.user.impl;

import com.attendancemgmt.entities.User;
import com.encentral.user.api.IUser;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class DefaultUserImpl implements IUser {
    private final JPAApi jpaApi;

    @Inject
    public DefaultUserImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            User user = jpaApi.withTransaction(em -> {
                return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                        .setParameter("email", email)
                        .getSingleResult();
            });
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByToken(String token) {
        try {
            User user = jpaApi.withTransaction(em -> {
                return em.createQuery("SELECT u FROM User u WHERE u.token = :token", User.class)
                        .setParameter("token", token)
                        .getSingleResult();
            });
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaApi.withTransaction(entityManager -> {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class
            );
            query.setParameter("email", email);
            Long count = query.getSingleResult();
            return count > 0;
        });
    }
}
