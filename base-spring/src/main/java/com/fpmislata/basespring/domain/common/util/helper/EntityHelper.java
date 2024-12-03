package com.fpmislata.basespring.domain.common.util.helper;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface EntityHelper {

    <T> void validateEntityExist(Function<Integer, Optional<T>> serviceMethod, Integer id, String entityName);

    <T> T findAndValidateEntity(Function<Integer, Optional<T>> serviceMethod, Integer id, String entityName);

    <T> void validateEntityDoesNotExist(Function<Integer, Optional<T>> serviceMethod, Integer id, String entityName);

    <T> void validateEntityNotInCollection(Collection<T> collection, Predicate<T> predicate, String entityName);

    <T> void validateEntityNotEqual(T existingEntity, Predicate<T> predicate, String entityName);
}
