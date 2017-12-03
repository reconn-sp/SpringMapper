package com.reconnect.web.utils.mapper;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
public interface SimpleMapper<D extends MappedDto, E extends MappedEntity>
        extends SimpleToDtoMapper<D, E>, SimpleToEntityMapper<D, E>, Mapper<D, E> {
}
