package cz.cvut.fit.stehlvo2.bandportal.repository;

import cz.cvut.fit.stehlvo2.bandportal.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
	Iterable<Place> findAllByOrderByPlaceName();
}
