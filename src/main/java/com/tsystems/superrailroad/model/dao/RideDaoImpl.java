package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Ride;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RideDaoImpl implements RideDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Ride ride) {
        entityManager.persist(ride);
        entityManager.close();
    }

    @Override
    public Ride read(Integer id) {
        Ride ride = entityManager.find(Ride.class, id);
        entityManager.close();

        return ride;
    }

    @Override
    public void update(Ride ride) {
        entityManager.merge(ride);
        entityManager.close();
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.getReference(Ride.class, id));
        entityManager.close();
    }
}
