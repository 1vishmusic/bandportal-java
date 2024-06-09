package cz.cvut.fit.stehlvo2.bandportal.service;

import cz.cvut.fit.stehlvo2.bandportal.utils.InvalidEntityException;
import cz.cvut.fit.stehlvo2.bandportal.domain.EntityWithId;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public abstract class CrudServiceImpl<T extends EntityWithId<Id>, Id> extends EntityValidator<T, Id> implements CrudService<T, Id> {
	@Override
	public T create(T entity) throws InvalidEntityException {
		validateCreation(entity);

		return getRepository().save(entity);
	}

	@Override
	public Iterable<T> readAll() {
		return getRepository().findAll();
	}

	@Override
	public T readById(Id id) throws EntityNotFoundException {
		Optional<T> entity = getRepository().findById(id);
		if(entity.isEmpty()) {
			throw new EntityNotFoundException("[" + getClass().getSimpleName() + "] Entity with id " + id + " does not exist.");
		}

		return entity.get();
	}

	@Override
	public T updateById(Id id, T entity) throws IllegalArgumentException, EntityNotFoundException, InvalidEntityException {
		if(!getRepository().existsById(id)) {
			throw new EntityNotFoundException("[" + getClass().getSimpleName() + "] Entity with id " + id + " does not exists");
		}

		validateUpdate(entity);

		return getRepository().save(entity);
	}

	@Override
	public void deleteById(Id id) throws EntityNotFoundException, InvalidEntityException {
		Optional<T> entity = getRepository().findById(id);
		if(entity.isEmpty()) {
			throw new EntityNotFoundException("[" + getClass().getSimpleName() + "] Entity with id " + id + " does not exists");
		}

		validateDeletion(entity.get());

		getRepository().deleteById(id);
	}

	/**
	 * Mostly it's enough to validate entity by its creation-validation method.
	 * However, for cases when there should be different validator, this method should be overridden.
	 */
	protected void validateUpdate(T entity) throws InvalidEntityException {
		validateCreation(entity);
	}

	protected abstract CrudRepository<T, Id> getRepository();
}
