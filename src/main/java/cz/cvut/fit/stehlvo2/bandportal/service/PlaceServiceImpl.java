package cz.cvut.fit.stehlvo2.bandportal.service;

import cz.cvut.fit.stehlvo2.bandportal.utils.InvalidEntityException;
import cz.cvut.fit.stehlvo2.bandportal.domain.Place;
import cz.cvut.fit.stehlvo2.bandportal.repository.PlaceRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class PlaceServiceImpl extends CrudServiceImpl<Place, Long> implements PlaceService {
	private final PlaceRepository placeRepository;

	public PlaceServiceImpl(PlaceRepository placeRepository) {
		this.placeRepository = placeRepository;
	}

	@Override
	public Iterable<Place> readAll() {
		return placeRepository.findAllByOrderByPlaceName();
	}

	@Override
	protected void validateCreation(Place entity) throws InvalidEntityException {
		assertFieldNotEmpty("placeName", entity.getPlaceName());
	}

	@Override
	protected void validateDeletion(Place entity) throws InvalidEntityException {
		if(!entity.getEvents().isEmpty()) {
			throw new InvalidEntityException("Could not delete Place entity: Place is linked to event(s).");
		}
	}

	@Override
	protected CrudRepository<Place, Long> getRepository() {
		return placeRepository;
	}
}
