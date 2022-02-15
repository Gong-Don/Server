package com.example.gongdon.user.repository;

import com.example.gongdon.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class UserRepository {
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        System.out.println("email : " + email);
        try {
            return em.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public User findByName(String name) {
        System.out.println("name : " + name);
        try {
            return em.createQuery("select u from User u where u.name = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
