package com.fpmislata.basespring.domain.common.util.helper.impl;

import com.fpmislata.basespring.common.exception.ResourceAlreadyExistsException;
import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.domain.common.util.helper.EntityHelper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class EntityHelperImpl implements EntityHelper {

    @Override
    public <T> void validateEntityExist(Function<Integer, Optional<T>> serviceMethod, Integer id, String entityName) {
        if (serviceMethod.apply(id).isEmpty()) {
            throw new ResourceNotFoundException(entityName + " " + id + " not found");
        }
    }

    @Override
    public <T> T findAndValidateEntity(Function<Integer, Optional<T>> serviceMethod, Integer id, String entityName) {
        return serviceMethod.apply(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName + " " + id + " not found"));
    }

    @Override
    public <T> void validateEntityDoesNotExist(Function<Integer, Optional<T>> serviceMethod, Integer id, String entityName) {
        if (serviceMethod.apply(id).isPresent()) {
            throw new ResourceAlreadyExistsException(entityName + " with ID " + id + " already exists");
        }
    }

    @Override
    public <T> void validateEntityNotInCollection(Collection<T> collection, Predicate<T> predicate, String entityName) {
        if (collection != null && collection.stream().anyMatch(predicate)) {
            throw new ResourceAlreadyExistsException(entityName + " already exists in the collection");
        }
    }

    @Override
    public <T> void validateEntityNotEqual(T existingEntity, Predicate<T> predicate, String entityName) {
        if (existingEntity != null && predicate.test(existingEntity)) {
            throw new ResourceAlreadyExistsException(entityName + " already exists");
        }
    }
}
