package cz.cvut.fit.stehlvo2.bandportal.controller.event;

import cz.cvut.fit.stehlvo2.bandportal.controller.CrudController;
import cz.cvut.fit.stehlvo2.bandportal.domain.Ticket;
import cz.cvut.fit.stehlvo2.bandportal.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/event/ticket", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController extends CrudController<Ticket, Long, TicketService> {
	public TicketController(TicketService service) {
		super(service);
	}

	@Override
	@PostMapping
	@Operation(summary = "Creates ticket information for event specified with its id.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Ticket was successfully saved."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format.")
	})
	public ResponseEntity<Object> create(@RequestBody Ticket entity) {
		return super.create(entity);
	}

	@Override
	@GetMapping
	@Operation(summary = "Returns all the stored tickets.")
	public Iterable<Ticket> readAll() {
		return super.readAll();
	}

	@Override
	@GetMapping("/~/{id}")
	@Operation(summary = "Returns a ticket for specified event if exists, depending on its id.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "The query was successfully executed."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format."),
		@ApiResponse(responseCode = "404", description = "Requested ticket was not found.")
	})
	public ResponseEntity<Object> get(@PathVariable Long id) {
		return super.get(id);
	}

	@Override
	@PutMapping("/~/{id}")
	@Operation(summary = "Updates a ticked with specified event id, if exists.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Update request was successfully executed."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format."),
		@ApiResponse(responseCode = "404", description = "Requested ticket was not found.")
	})
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Ticket entity) {
		return super.update(id, entity);
	}

	@Override
	@DeleteMapping("/~/{id}")
	@Operation(summary = "Deletes a ticket for specified event by event id. It's also possible to remove a ticket by removing it's parent entity (Event).")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Ticket information were successfully deleted."),
		@ApiResponse(responseCode = "400", description = "Request body was not in correct format."),
		@ApiResponse(responseCode = "404", description = "Requested ticket was not found.")
	})
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		return super.delete(id);
	}

	@Override
	protected void setEntityId(Ticket ticket, Long id) {
		ticket.setEventId(id);
	}
}
