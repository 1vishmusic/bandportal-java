package cz.cvut.fit.stehlvo2.bandportal.service;

import cz.cvut.fit.stehlvo2.bandportal.utils.InvalidEntityException;
import cz.cvut.fit.stehlvo2.bandportal.domain.EntityWithId;

abstract public class EntityValidator<T extends EntityWithId<Id>, Id> {
	abstract protected void validateCreation(T entity) throws InvalidEntityException;

	abstract protected void validateUpdate(T entity) throws InvalidEntityException;

	abstract protected void validateDeletion(T entity) throws InvalidEntityException;

	protected void assertFieldNotEmpty(String fieldName, String field) throws InvalidEntityException {
		if(field == null || field.trim().isEmpty()) {
			throw new InvalidEntityException(fieldName + " must not be empty");
		}
	}
}
