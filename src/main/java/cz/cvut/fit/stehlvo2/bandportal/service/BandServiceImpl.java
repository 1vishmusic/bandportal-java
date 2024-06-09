package cz.cvut.fit.stehlvo2.bandportal.service;

import cz.cvut.fit.stehlvo2.bandportal.utils.InvalidEntityException;
import cz.cvut.fit.stehlvo2.bandportal.domain.Band;
import cz.cvut.fit.stehlvo2.bandportal.repository.BandRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class BandServiceImpl extends CrudServiceImpl<Band, Long> implements BandService {
	private final BandRepository bandRepository;

	public BandServiceImpl(BandRepository bandRepository) {
		this.bandRepository = bandRepository;
	}

	@Override
	public Iterable<Band> readAll() {
		return bandRepository.findAllByOrderByBandName();
	}

	@Override
	protected void validateCreation(Band band) throws InvalidEntityException {
		assertFieldNotEmpty("bandName", band.getBandName());
	}

	@Override
	protected void validateDeletion(Band entity) throws InvalidEntityException {
		if(!entity.getEvents().isEmpty()) {
			throw new InvalidEntityException("Unable to delete Band entity: There are concerts linked to this Band, so we can not remove the concert.");
		}
	}

	@Override
	protected CrudRepository<Band, Long> getRepository() {
		return bandRepository;
	}
}
