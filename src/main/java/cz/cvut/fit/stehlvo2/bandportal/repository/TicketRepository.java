package cz.cvut.fit.stehlvo2.bandportal.repository;

import cz.cvut.fit.stehlvo2.bandportal.domain.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
}
