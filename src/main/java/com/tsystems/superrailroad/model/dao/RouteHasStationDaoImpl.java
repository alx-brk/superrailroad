package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.RouteHasStation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RouteHasStationDaoImpl implements RouteHasStationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(RouteHasStation routeHasStation) {
        entityManager.persist(routeHasStation);
        entityManager.close();
    }

    @Override
    public RouteHasStation read(Integer id) {
        RouteHasStation routeHasStation = entityManager.find(RouteHasStation.class, id);
        entityManager.close();

        return routeHasStation;
    }

    @Override
    public void update(RouteHasStation routeHasStation) {
        entityManager.merge(routeHasStation);
        entityManager.close();
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.getReference(RouteHasStation.class, id));
        entityManager.close();
    }
}
