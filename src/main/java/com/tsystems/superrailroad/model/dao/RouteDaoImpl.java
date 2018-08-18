package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Route;
import main.java.com.tsystems.superrailroad.model.entity.Station;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RouteDaoImpl implements RouteDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Route route) {
        entityManager.persist(route);
        entityManager.close();
    }

    @Override
    public Route read(Integer id) {
        Route route = entityManager.find(Route.class, id);
        entityManager.close();

        return route;
    }

    @Override
    public void update(Route route) {
        entityManager.merge(route);
        entityManager.close();
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.getReference(Route.class, id));
        entityManager.close();
    }

    @Override
    public List<Route> readAll() {
        return entityManager.createQuery("select r from route r", Route.class).getResultList();
    }
}
