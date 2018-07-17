package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Ride;
import main.java.com.tsystems.superrailroad.model.entity.RideHasStation;
import main.java.com.tsystems.superrailroad.model.entity.Station;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class RideHasStationDaoImpl implements RideHasStationDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(RideHasStation rideHasStation) {
        entityManager.persist(rideHasStation);
        entityManager.close();
    }

    @Override
    public RideHasStation read(Integer id) {
        RideHasStation rideHasStation = entityManager.find(RideHasStation.class, id);
        entityManager.close();

        return rideHasStation;
    }

    @Override
    public void update(RideHasStation rideHasStation) {
        entityManager.merge(rideHasStation);
        entityManager.close();
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.getReference(RideHasStation.class, id));
        entityManager.close();
    }

    @Override
    public List<Ride> findRideByStations(Station stationFrom, Station stationTo) {
        return entityManager
                .createQuery("select rhs1.ride from RideHasStation rhs1 " +
                        "inner join RideHasStation rhs2 on rhs1.ride = rhs2.ride " +
                        "where rhs1.station = :stationFrom and rhs2.station = :stationTo " +
                        "and rhs1.departure < rhs2.departure", Ride.class)
                .setParameter("stationFrom", stationFrom)
                .setParameter("stationTo", stationTo)
                .getResultList();
    }

    @Override
    public RideHasStation findByRideAndStation(Ride ride, Station station) {
        return entityManager
                .createQuery("select rhs from RideHasStation rhs where rhs.ride = :ride and rhs.station = :station",RideHasStation.class)
                .setParameter("ride", ride)
                .setParameter("station", station)
                .getSingleResult();

    }

    @Override
    public int sumPrice(Ride ride, Date dateFrom, Date dateTo) {
        return (int) Math.round(entityManager
                .createQuery("select sum(rhs.price) from RideHasStation rhs where rhs.ride = :ride and rhs.departure > :dateFrom and rhs.departure <= :dateTo", Double.class)
                .setParameter("ride",ride)
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .getSingleResult());
    }

    @Override
    public RideHasStation findFirstByRide(Ride ride) {
        return entityManager
                .createQuery("select rhs from RideHasStation rhs where rhs.ride = :ride and rhs.departure =" +
                        " (select min(rhs2.departure) from RideHasStation rhs2 where rhs2.ride = :ride)", RideHasStation.class)
                .setParameter("ride", ride)
                .getSingleResult();
    }

    @Override
    public RideHasStation findLastByRide(Ride ride) {
        return entityManager
                .createQuery("select rhs from RideHasStation rhs where rhs.ride = :ride and rhs.departure =" +
                        " (select max(rhs2.departure) from RideHasStation rhs2 where rhs2.ride = :ride)", RideHasStation.class)
                .setParameter("ride", ride)
                .getSingleResult();
    }

    @Override
    public List<RideHasStation> getRidesByStation(Station station) {
        return entityManager
                .createQuery("select rhs from RideHasStation rhs where rhs.station = :station",RideHasStation.class)
                .setParameter("station", station)
                .getResultList();
    }

    @Override
    public List<RideHasStation> getStationsByRide(Ride ride) {
        return entityManager
                .createQuery("select rhs from RideHasStation rhs where rhs.ride = :ride order by rhs.departure asc", RideHasStation.class)
                .setParameter("ride", ride)
                .getResultList();
    }
}
