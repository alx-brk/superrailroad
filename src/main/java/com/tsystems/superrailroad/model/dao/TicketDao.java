package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Ride;
import main.java.com.tsystems.superrailroad.model.entity.Ticket;

public interface TicketDao {
    void create (Ticket ticket);
    Ticket read(Integer id);
    void update(Ticket ticket);
    void delete(Integer id);
    long countByRide(Ride ride);
}
