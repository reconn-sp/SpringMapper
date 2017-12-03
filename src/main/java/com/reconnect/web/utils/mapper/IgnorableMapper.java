package com.reconnect.web.utils.mapper;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
public interface IgnorableMapper<D extends MappedDto, E extends MappedEntity>
        extends IgnorableToDtoMapper<D, E>, IgnorableToEntityMapper<D, E>, Mapper<D, E> {
}
