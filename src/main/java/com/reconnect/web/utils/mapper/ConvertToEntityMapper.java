package com.reconnect.web.utils.mapper;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
public interface ConvertToEntityMapper<D extends MappedDto, E extends MappedEntity>
        extends ToEntityMapper<D, E>, IgnoreHelper<D, E> {
}
