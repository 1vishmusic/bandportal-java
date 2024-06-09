package cz.cvut.fit.stehlvo2.bandportal.repository;

import cz.cvut.fit.stehlvo2.bandportal.domain.Band;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandRepository extends CrudRepository<Band, Long> {
	Iterable<Band> findAllByOrderByBandName();
}
