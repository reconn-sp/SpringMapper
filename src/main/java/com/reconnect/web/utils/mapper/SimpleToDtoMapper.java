package com.reconnect.web.utils.mapper;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
public interface SimpleToDtoMapper<D extends MappedDto, E extends MappedEntity>
        extends ToDtoMapper<D, E>, IgnoreHelper<D, E> {

    @Override
    default void customConversion(final E from, final D to) {
        // we don't need anything here
    }
}
