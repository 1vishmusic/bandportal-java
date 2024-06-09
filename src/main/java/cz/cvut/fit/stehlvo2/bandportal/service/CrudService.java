package cz.cvut.fit.stehlvo2.bandportal.service;

import cz.cvut.fit.stehlvo2.bandportal.utils.InvalidEntityException;
import jakarta.persistence.EntityNotFoundException;

public interface CrudService<T, Id> {
	/**
	 * Method which handles entity creation
	 *
	 * @param entity Entity to save
	 * @return Saved entity, will never be null
	 *
	 * @throws IllegalArgumentException when the entity is null
	 * @throws InvalidEntityException when the entity is incomplete, or contains invalid data
	 */
	T create(T entity) throws IllegalArgumentException, InvalidEntityException;

	/**
	 * @return Returns all the saved entities from specific repository
	 */
	Iterable<T> readAll();

	/**
	 * @param id Entity id
	 * @return Returns entity, will never be null
	 *
	 * @throws EntityNotFoundException when the entity is not found
	 */
	T readById(Id id) throws EntityNotFoundException;

	/**
	 * @param id Entity identiier
	 * @param entity Modified entity data
	 *
	 * @return Saved entity, will never be null
	 *
	 * @throws IllegalArgumentException in case entity is null
	 * @throws EntityNotFoundException when the entity with the id does not exist
	 * @throws InvalidEntityException when the entity data is invalid or incomplete
	 */
	T updateById(Id id, T entity) throws EntityNotFoundException, IllegalArgumentException, InvalidEntityException;

	/**
	 * Method which removes the entity
	 *
	 * @throws EntityNotFoundException when the entity with the id does not exist
	 */
	void deleteById(Id id) throws EntityNotFoundException, InvalidEntityException;
}
