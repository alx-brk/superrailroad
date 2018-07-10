package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Station;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class StationDaoImpl implements StationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Station station) {
        entityManager.persist(station);
        entityManager.close();
    }

    @Override
    public Station read(Integer id) {
        Station station = entityManager.find(Station.class, id);
        entityManager.close();

        return station;
    }

    @Override
    public void update(Station station) {
        entityManager.merge(station);
        entityManager.close();
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.getReference(Station.class, id));
        entityManager.close();
    }

    @Override
    public List<Station> readAll() {
        List<Station> stations = entityManager.createQuery("select s from Station s", Station.class).getResultList();
        entityManager.close();

        return stations;
    }

    @Override
    public Station find(String name) {
        return entityManager.
                createQuery("select s from Station s where s.name = :name", Station.class).
                setParameter("name", name).
                getSingleResult();

    }
}
