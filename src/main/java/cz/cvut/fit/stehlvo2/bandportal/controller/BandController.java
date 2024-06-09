package cz.cvut.fit.stehlvo2.bandportal.controller;

import cz.cvut.fit.stehlvo2.bandportal.domain.Band;
import cz.cvut.fit.stehlvo2.bandportal.service.BandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/band", produces = MediaType.APPLICATION_JSON_VALUE)
public class BandController extends CrudController<Band, Long, BandService> {
	protected BandController(BandService crudService) {
		super(crudService);
	}

	@Override
	@PostMapping
	@Operation(summary = "Creates a new band and saves it into database.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Band was successfully saved."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format.")
	})
	public ResponseEntity<Object> create(@RequestBody Band entity) {
		return super.create(entity);
	}

	@Override
	@GetMapping
	@Operation(summary = "Returns all the stored bands.")
	public Iterable<Band> readAll() {
		return super.readAll();
	}

	@Override
	@GetMapping("/~/{id}")
	@Operation(summary = "Returns a band with id, if exists.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "The query was successfully executed."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format."),
		@ApiResponse(responseCode = "404", description = "Requested band was not found.")
	})
	public ResponseEntity<Object> get(@PathVariable Long id) {
		return super.get(id);
	}

	@Override
	@PutMapping("/~/{id}")
	@Operation(summary = "Updates a band with specified id, if exists")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Update request was successfully executed."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format."),
		@ApiResponse(responseCode = "404", description = "Requested band was not found.")
	})
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Band entity) {
		return super.update(id, entity);
	}

	@Override
	@DeleteMapping("/~/{id}")
	@Operation(summary = "Deletes band from database by its id. It is not possible to delete event, if it is linked to event.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Band was successfully deleted."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format. Or the specified band is linked to event, which must be deleted first."),
		@ApiResponse(responseCode = "404", description = "Requested band was not found.")
	})
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		return super.delete(id);
	}

	@Override
	protected void setEntityId(Band entity, Long id) {
		entity.setBandId(id);
	}
}
