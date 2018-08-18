package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Station;
import main.java.com.tsystems.superrailroad.model.entity.StationGraph;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class StationGraphDaoImpl implements StationGraphDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(StationGraph stationGraph) {
        entityManager.persist(stationGraph);
        entityManager.close();
    }

    @Override
    public StationGraph read(Integer id) {
        StationGraph stationGraph = entityManager.find(StationGraph.class, id);
        entityManager.close();

        return stationGraph;
    }

    @Override
    public void update(StationGraph stationGraph) {
        entityManager.merge(stationGraph);
        entityManager.close();
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.getReference(StationGraph.class, id));
        entityManager.close();
    }

    @Override
    public List<Station> getConnectedStations(Station station) {
        return entityManager
                .createQuery("select sg.stationRef from StationGraph sg where sg.station = :station", Station.class)
                .setParameter("station", station)
                .getResultList();
    }

    @Override
    public StationGraph find(Station station, Station stationRef) {
        return entityManager
                .createQuery("select sg from StationGraph sg where sg.station = :station and sg.stationRef = :stationRef", StationGraph.class)
                .setParameter("station", station)
                .setParameter("stationRef", stationRef)
                .getSingleResult();
    }

    @Override
    public List<StationGraph> findAllByStation(Station station) {
        return entityManager.createQuery("select sg from StationGraph sg where sg.station = :station or sg.stationRef = :station", StationGraph.class)
                .setParameter("station", station)
                .getResultList();
    }
}
