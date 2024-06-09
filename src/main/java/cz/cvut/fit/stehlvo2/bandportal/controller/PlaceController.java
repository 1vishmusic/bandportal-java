package cz.cvut.fit.stehlvo2.bandportal.controller;

import cz.cvut.fit.stehlvo2.bandportal.domain.Place;
import cz.cvut.fit.stehlvo2.bandportal.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/place", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlaceController extends CrudController<Place, Long, PlaceService> {
	public PlaceController(PlaceService service) {
		super(service);
	}

	@Override
	@PostMapping
	@Operation(summary = "Creates a new place and saves it into database.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Place was successfully saved."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format.")
	})
	public ResponseEntity<Object> create(@RequestBody Place entity) {
		return super.create(entity);
	}

	@Override
	@GetMapping
	@Operation(summary = "Returns all the places stored in database.")
	public Iterable<Place> readAll() {
		return super.readAll();
	}

	@Override
	@GetMapping("/~/{id}")
	@Operation(summary = "Returns place by its id.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "The query was successfully executed."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format."),
		@ApiResponse(responseCode = "404", description = "Requested place was not found.")
	})
	public ResponseEntity<Object> get(@PathVariable Long id) {
		return super.get(id);
	}

	@Override
	@PutMapping("/~/{id}")
	@Operation(summary = "Updates place information by its id.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Update request was successfully executed."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format."),
		@ApiResponse(responseCode = "404", description = "Requested place was not found.")
	})
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Place entity) {
		return super.update(id, entity);
	}

	@Override
	@DeleteMapping("/~/{id}")
	@Operation(summary = "Deletes place from database. Make sure to unlink it from events first. It is not possible to remove place linked to an event.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Place was successfully deleted."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format, or the place is linked to event, which must be deleted first."),
		@ApiResponse(responseCode = "404", description = "Requested place was not found.")
	})
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		return super.delete(id);
	}

	protected void setEntityId(Place place, Long id) {
		place.setPlaceId(id);
	}
}
