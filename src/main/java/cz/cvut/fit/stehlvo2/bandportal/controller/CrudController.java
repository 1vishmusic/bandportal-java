package cz.cvut.fit.stehlvo2.bandportal.controller;

import cz.cvut.fit.stehlvo2.bandportal.utils.InvalidEntityException;
import cz.cvut.fit.stehlvo2.bandportal.service.CrudService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
abstract public class CrudController<T, Id, S extends CrudService<T, Id>> {
	protected final S service;

	public CrudController(S service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody T entity) {
		try {
			return ResponseEntity.ok(service.create(entity));
		} catch (IllegalArgumentException | InvalidEntityException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), ""));
		}
    }

	@GetMapping
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "The query was successfully executed", useReturnTypeSchema = true)
	})
	public Iterable<T> readAll() {
		return service.readAll();
	}

	@GetMapping("/~/{id}")
	public ResponseEntity<Object> get(@PathVariable Id id) {
		try {
			return ResponseEntity.ok(service.readById(id));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), ""));
		}
	}

	@PutMapping("/~/{id}")
	public ResponseEntity<Object> update(@PathVariable Id id, @RequestBody T entity) {
		try {
			setEntityId(entity, id);
			return ResponseEntity.ok(service.updateById(id, entity));
		} catch (InvalidEntityException | IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), ""));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), ""));
		}
	}

	@DeleteMapping("/~/{id}")
	public ResponseEntity<Object> delete(@PathVariable Id id) {
		try {
			service.deleteById(id);
			return ResponseEntity.ok("");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), ""));
		} catch (InvalidEntityException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), ""));
		}
	}

	protected Map<String, Object> createErrorResponse(HttpStatus status, String message, String details) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("status", "error");
		errorResponse.put("code", status.value());
		errorResponse.put("message", message);
		errorResponse.put("details", details);

		return errorResponse;
	}

	abstract protected void setEntityId(T entity, Id id);
}
