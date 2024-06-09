package cz.cvut.fit.stehlvo2.bandportal.repository;

import cz.cvut.fit.stehlvo2.bandportal.domain.Place;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class PlaceRepositoryTest {
	@Autowired
	private PlaceRepository repository;

	@Test
	void fetchPlacesSorted() {
		Place p1 = new Place();
		Place p2 = new Place();
		Place p3 = new Place();
		Place p4 = new Place();

		p1.setPlaceId(1L);
		p2.setPlaceId(2L);
		p3.setPlaceId(3L);
		p4.setPlaceId(4L);

		p1.setPlaceName("c");
		p2.setPlaceName("a");
		p3.setPlaceName("d");
		p4.setPlaceName("b");

		repository.saveAll(List.of(p1, p2, p3, p4));

		Iterable<Place> places = repository.findAllByOrderByPlaceName();
		Assertions.assertIterableEquals(List.of(p2, p4, p1, p3), places);
	}
}
