package com.reconnect.web.utils.mapper;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
public interface IgnorableToEntityMapper<D extends MappedDto, E extends MappedEntity> extends ToEntityMapper<D, E> {

    @Override
    default void customConversion(final D from, final E to) {
        // we don't need anything here
    }
}
