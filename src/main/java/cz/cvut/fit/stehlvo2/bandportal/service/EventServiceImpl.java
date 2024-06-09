package cz.cvut.fit.stehlvo2.bandportal.service;

import cz.cvut.fit.stehlvo2.bandportal.utils.InvalidEntityException;
import cz.cvut.fit.stehlvo2.bandportal.utils.TwoEventsAtSameTimeException;
import cz.cvut.fit.stehlvo2.bandportal.domain.Event;
import cz.cvut.fit.stehlvo2.bandportal.domain.Place;
import cz.cvut.fit.stehlvo2.bandportal.repository.EventRepository;
import cz.cvut.fit.stehlvo2.bandportal.repository.PlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
public class EventServiceImpl extends CrudServiceImpl<Event, Long> implements EventService {
	private final EventRepository eventRepository;
	private final PlaceRepository placeRepository;

	public EventServiceImpl(EventRepository eventRepository, PlaceRepository placeRepository) {
		this.eventRepository = eventRepository;
		this.placeRepository = placeRepository;
	}

	@Override
	public Iterable<Event> readAll() {
		return eventRepository.findAllByOrderByEventStartDesc();
	}

	public Event updatePlace(Long eventId, Place place) {
		Event event = readById(eventId);

		event.setPlace(place);
		place.getEvents().add(event);

		placeRepository.save(place);
		return eventRepository.save(event);
	}

	@Override
	public Iterable<Event> readUpcoming() {
		return eventRepository.findUpcoming();
	}

	private void validateBasics(Event entity, boolean update) throws InvalidEntityException {
		assertFieldNotEmpty("eventName", entity.getEventName());

		if(entity.getEventEnd() == null) {
			checkConcurrency(entity, update);
		} else {
			checkConcurrencyWithEnd(entity, update);

			if(entity.getEventStart().isAfter(entity.getEventEnd())) {
				throw new InvalidEntityException("Event start must be before its end.");
			}
		}
	}

	@Override
	protected void validateCreation(Event entity) throws InvalidEntityException {
		validateBasics(entity, false);
	}

	@Override
	protected void validateUpdate(Event entity) throws InvalidEntityException {
		validateBasics(entity, true);
	}

	@Override
	protected void validateDeletion(Event entity) throws InvalidEntityException {
		// Always valid
	}

	private void checkConcurrency(Event event, boolean update) throws TwoEventsAtSameTimeException {
		Iterable<Event> events = update ? StreamSupport.stream(readAll().spliterator(), false)
			.filter((e) -> (!e.getEventId().equals(event.getEventId())))
			.toList() : readAll();

		for(Event e : events) {
			if(e.getEventEnd() != null) {
				if(e.getEventStart().isBefore(event.getEventStart()) && e.getEventEnd().isAfter(event.getEventStart())) {
					throw new TwoEventsAtSameTimeException("Received event is set to time when another event is set.");
				}
			} else {
				if(event.getEventStart().equals(e.getEventStart())) {
					throw new TwoEventsAtSameTimeException("Received event is set to time when another event is set.");
				}
			}
		}
	}

	private void checkConcurrencyWithEnd(Event event, boolean update) throws TwoEventsAtSameTimeException {
		Iterable<Event> events = update ? StreamSupport.stream(readAll().spliterator(), false)
			.filter((e) -> (!e.getEventId().equals(event.getEventId())))
			.toList() : readAll();

		for(Event e : events) {
			if(e.getEventEnd() != null) {
				if(e.getEventStart().isBefore(event.getEventStart()) && e.getEventEnd().isAfter(event.getEventStart())) {
					throw new TwoEventsAtSameTimeException("Received event is set to time when another event is set.");
				}
				if(e.getEventEnd().isAfter(event.getEventEnd()) && e.getEventStart().isBefore(event.getEventEnd())) {
					throw new TwoEventsAtSameTimeException("Received event is set to time when another event is set.");
				}
				if(e.getEventStart().isAfter(event.getEventStart()) && e.getEventEnd().isBefore(event.getEventEnd())) {
					throw new TwoEventsAtSameTimeException("Received event is set to time when another event is set.");
				}
			} else {
				if(event.getEventStart().isBefore(e.getEventStart()) && event.getEventEnd().isAfter(e.getEventStart())) {
					throw new TwoEventsAtSameTimeException("Received event is set to time when another event is set.");
				}
			}
		}
	}

	@Override
	protected CrudRepository<Event, Long> getRepository() {
		return eventRepository;
	}
}
