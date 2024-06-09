package cz.cvut.fit.stehlvo2.bandportal.controller;

import cz.cvut.fit.stehlvo2.bandportal.domain.Event;
import cz.cvut.fit.stehlvo2.bandportal.service.BandService;
import cz.cvut.fit.stehlvo2.bandportal.service.EventService;
import cz.cvut.fit.stehlvo2.bandportal.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/event", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController extends CrudController<Event, Long, EventService> {
	private final BandService bandService;
	private final PlaceService placeService;

	public EventController(EventService service, BandService bandService, PlaceService placeService) {
		super(service);

		this.bandService = bandService;
		this.placeService = placeService;
	}

	@Operation(summary = "Returns upcoming events (events with startTime timestamp in the future)")
	@GetMapping("/upcoming")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "The query was successfully executed.")
	})
	public Iterable<Event> upcoming() {
		return service.readUpcoming();
	}

	@Override
	@PostMapping
	@Operation(summary = "Creates a new event and saves it into database. In linked place entity are all attributes expect placeId (due to linking) ignored. To link bands, only band ids are used for linking.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Event was successfully saved."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format.")
	})
	public ResponseEntity<Object> create(@RequestBody Event event) {
		try {
			// Asserts that the place exists
			event.setPlace(placeService.readById(event.getPlace().getPlaceId()));
			// Asserts that all the bands exist
			event.setBands(event.getBands().stream()
				.map(band -> bandService.readById(band.getBandId()))
				.collect(Collectors.toList())
			);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), ""));
		}

		return super.create(event);
	}

	@Override
	@GetMapping
	@Operation(summary = "Returns all the stored events. Events are stored in descending order by date.")
	public Iterable<Event> readAll() {
		return super.readAll();
	}

	@Override
	@GetMapping("/~/{id}")
	@Operation(summary = "Returns event by its id, if exists.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "The query was successfully executed."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format."),
		@ApiResponse(responseCode = "404", description = "Requested event was not found.")
	})
	public ResponseEntity<Object> get(@PathVariable Long id) {
		return super.get(id);
	}

	@Override
	@PutMapping("/~/{id}")
	@Operation(summary = "Finds event and updates it if exists.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Update request was successfully executed."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format."),
		@ApiResponse(responseCode = "404", description = "Requested event, place or one of bands was not found.")
	})
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Event entity) {
		return super.update(id, entity);
	}

	@Override
	@DeleteMapping("/~/{id}")
	@Operation(summary = "Removes event from database. Also removes associated ticket information, if there are any.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Event was successfully deleted."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format."),
		@ApiResponse(responseCode = "404", description = "Requested event was not found.")
	})
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		return super.delete(id);
	}

	@Override
	protected void setEntityId(Event event, Long id) {
		event.setEventId(id);
	}
}
