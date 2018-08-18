package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Ride;
import main.java.com.tsystems.superrailroad.model.entity.Route;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    @Override
    public List<Ride> getAll() {
        return entityManager.createQuery("select r from ride r", Ride.class).getResultList();
    }

    @Override
    public boolean routeUsed(Route route) {
        return (entityManager.createQuery("select count(r) from ride r where r.route = :route", Long.class).setParameter("route", route).getSingleResult() > 0);
    }
}
