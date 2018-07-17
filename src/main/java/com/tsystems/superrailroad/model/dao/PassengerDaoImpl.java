package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Passenger;
import main.java.com.tsystems.superrailroad.model.entity.Ride;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class PassengerDaoImpl implements PassengerDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Passenger passenger) {
        entityManager.persist(passenger);
        entityManager.close();
    }

    @Override
    public Passenger read(Integer id) {
        Passenger passenger = entityManager.find(Passenger.class, id);
        entityManager.close();

        return passenger;
    }

    @Override
    public void update(Passenger passenger) {
        entityManager.merge(passenger);
        entityManager.close();
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.getReference(Passenger.class, id));
        entityManager.close();
    }

    @Override
    public Passenger find(String firstName, String lastName, Date birthDate) {
        return entityManager
                .createQuery("select p from Passenger p where p.firstName = :firstName and p.lastName = :lastName and p.birthDate = :birthDate", Passenger.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .setParameter("birthDate", birthDate)
                .getSingleResult();
    }

    @Override
    public List<Passenger> findByRide(Ride ride) {
        return entityManager
                .createQuery("select p from Passenger p inner join Ticket t on p = t.passenger where t.ride = :ride", Passenger.class)
                .setParameter("ride", ride)
                .getResultList();
    }
}
