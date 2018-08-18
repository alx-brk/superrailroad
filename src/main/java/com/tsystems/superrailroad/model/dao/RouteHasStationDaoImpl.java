package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Route;
import main.java.com.tsystems.superrailroad.model.entity.RouteHasStation;
import main.java.com.tsystems.superrailroad.model.entity.Station;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    @Override
    public List<RouteHasStation> getStationsByRoute(Route route) {
        return entityManager.createQuery("select r from RouteHasStation r where r.route = :route order by r.stationOrder asc", RouteHasStation.class)
                            .setParameter("route", route)
                            .getResultList();
    }

    @Override
    public boolean stationUsed(Station station) {
        return (entityManager.createQuery("select count(r) from RouteHasStation r where r.station = :station", Long.class).setParameter("station", station).getSingleResult() > 0);
    }
}
