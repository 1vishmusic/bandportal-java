package cz.cvut.fit.stehlvo2.bandportal.service;

import cz.cvut.fit.stehlvo2.bandportal.utils.TwoEventsAtSameTimeException;
import cz.cvut.fit.stehlvo2.bandportal.domain.Event;
import cz.cvut.fit.stehlvo2.bandportal.domain.Place;
import cz.cvut.fit.stehlvo2.bandportal.repository.EventRepository;
import cz.cvut.fit.stehlvo2.bandportal.repository.PlaceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class EventServiceImplTest {
	@Autowired
	private EventServiceImpl eventService;

	@MockBean
	private EventRepository eventRepository;
	@MockBean
	private PlaceRepository placeRepository;

	Event event;
	Place place;

	@BeforeEach
	void setUp() {
		place = new Place();
		place.setPlaceName("Praha, Klub 007 Strahov");
		place.setPlaceWebsite("https://klub007.cz");
		place.setPlaceId(4L);
		place.setEvents(new ArrayList<>());

		event = new Event();
		event.setEventId(3L);
		event.setEventName("Koncert");
		event.setEventStart(LocalDateTime.of(2024, 1, 1, 20, 0));
		event.setEventEnd(LocalDateTime.of(2024, 1, 1, 22, 0));
		event.setPlace(null);
	}

	@Test
	void addTwoEventsAtSameTime() {
		Mockito.when(eventService.readAll()).thenReturn(new ArrayList<>() {{ add(event); }});

		Event anotherEvent = new Event();
		anotherEvent.setEventId(5L);
		anotherEvent.setEventName("Koncert ve stejny den");
		anotherEvent.setEventStart(LocalDateTime.of(2024, 1, 1, 21, 0));
		anotherEvent.setEventEnd(LocalDateTime.of(2024, 1, 1, 23, 0));
		anotherEvent.setPlace(null);

		Assertions.assertThrows(TwoEventsAtSameTimeException.class, () -> eventService.create(anotherEvent));
	}

	@Test
	void addPlace() {
		Mockito.when(eventRepository.findById(event.getEventId())).thenReturn(Optional.of(event));
		Mockito.when(placeRepository.findById(place.getPlaceId())).thenReturn(Optional.of(place));

		eventService.updatePlace(event.getEventId(), place);

		Assertions.assertEquals(event.getPlace(), place);
		Assertions.assertTrue(place.getEvents().contains(event));

		Mockito.verify(eventRepository, Mockito.atLeastOnce()).save(event);
		Mockito.verify(placeRepository, Mockito.atLeastOnce()).save(place);
	}
}