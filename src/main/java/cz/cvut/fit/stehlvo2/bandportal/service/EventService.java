package cz.cvut.fit.stehlvo2.bandportal.service;

import cz.cvut.fit.stehlvo2.bandportal.domain.Event;

public interface EventService extends CrudService<Event, Long> {
	Iterable<Event> readUpcoming();
}
