package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Train;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TrainDaoImpl implements TrainDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Train train) {
        entityManager.persist(train);
        entityManager.close();
    }

    @Override
    public Train read(Integer id) {
        Train train = entityManager.find(Train.class, id);
        entityManager.close();

        return train;
    }

    @Override
    public void update(Train train) {
        entityManager.merge(train);
        entityManager.close();
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.getReference(Train.class, id));
        entityManager.close();
    }
}
