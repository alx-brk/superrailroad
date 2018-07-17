package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Role role) {
        entityManager.persist(role);
        entityManager.close();
    }

    @Override
    public Role read(Integer id) {
        Role role = entityManager.find(Role.class, id);
        entityManager.close();

        return role;
    }

    @Override
    public void update(Role role) {
        entityManager.merge(role);
        entityManager.close();
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.getReference(Role.class, id));
        entityManager.close();
    }

    @Override
    public Role find(String role) {
        return entityManager.createQuery("select r from role r where r.role = :role", Role.class)
                            .setParameter("role", role)
                            .getSingleResult();
    }
}
