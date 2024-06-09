package cz.cvut.fit.stehlvo2.bandportal.service;

import cz.cvut.fit.stehlvo2.bandportal.utils.InvalidEntityException;
import cz.cvut.fit.stehlvo2.bandportal.domain.Ticket;
import cz.cvut.fit.stehlvo2.bandportal.repository.TicketRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class TicketServiceImpl extends CrudServiceImpl<Ticket, Long> implements TicketService{
	private final TicketRepository ticketRepository;

	public TicketServiceImpl(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	@Override
	protected void validateCreation(Ticket entity) throws InvalidEntityException {
		// Ticket is always correctly set up
	}

	@Override
	protected void validateDeletion(Ticket entity) throws InvalidEntityException {
		// Ticket is always deleted correctly
	}

	@Override
	protected CrudRepository<Ticket, Long> getRepository() {
		return ticketRepository;
	}
}
