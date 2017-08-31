package com.bobocode.dao.impl;

import com.bobocode.dao.UserDao;
import com.bobocode.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;


public class UserDaoImpl implements UserDao {

    private final EntityManagerFactory emf;

    public UserDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public User findOne(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        //todo: find account by id using EntityManager

        em.getTransaction().commit();
        em.close();

        throw new UnsupportedOperationException("Method is not implemented yet. It's your homework!");
        // todo: return account
    }

    @Override
    public User findByEmail(String email) {
        // todo: implement search by email via EntityManager
        throw new UnsupportedOperationException("Method is not implemented yet. It's your homework!");
    }

    @Override
    public List<User> findAll() {
        //todo: find and return all accounts using EntityManagers
        throw new UnsupportedOperationException("Method is not implemented yet. It's your homework!");
    }


    @Override
    public void save(User user) {
        // todo: save an account sing EntityManager
        throw new UnsupportedOperationException("Method is not implemented yet. It's your homework!");
    }


}
