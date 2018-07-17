package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(User user) {
        entityManager.persist(user);
        entityManager.close();
    }

    @Override
    public User read(Integer id) {
        User user = entityManager.find(User.class, id);
        entityManager.close();

        return user;
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
        entityManager.close();
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.getReference(User.class, id));
        entityManager.close();
    }

    @Override
    public User find(String login) {
        return entityManager.createQuery("select u from user u where u.login = :login", User.class)
                            .setParameter("login", login)
                            .getSingleResult();
    }
}
