package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Ride;
import main.java.com.tsystems.superrailroad.model.entity.Ticket;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TicketDaoImpl implements TicketDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Ticket ticket) {
        entityManager.persist(ticket);
        entityManager.close();
    }

    @Override
    public Ticket read(Integer id) {
        Ticket ticket = entityManager.find(Ticket.class, id);
        entityManager.close();

        return ticket;
    }

    @Override
    public void update(Ticket ticket) {
        entityManager.merge(ticket);
        entityManager.close();
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.getReference(Ticket.class, id));
        entityManager.close();
    }

    @Override
    public long countByRide(Ride ride) {
        return entityManager
                .createQuery("select count(t) from Ticket t where t.ride = :ride", Long.class)
                .setParameter("ride", ride)
                .getSingleResult();
    }
}
