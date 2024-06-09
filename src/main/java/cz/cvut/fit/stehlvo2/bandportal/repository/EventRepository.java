package cz.cvut.fit.stehlvo2.bandportal.repository;

import cz.cvut.fit.stehlvo2.bandportal.domain.Event;
import jakarta.persistence.Entity;
import jakarta.persistence.OrderBy;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	Iterable<Event> findAllByOrderByEventStartDesc();

	@Query("select event from Event event where event.eventStart >= current_timestamp order by event.eventStart asc")
	Iterable<Event> findUpcoming();
}
