package com.reconnect.web.utils.mapper;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
public interface ConvertToDtoMapper<D extends MappedDto, E extends MappedEntity>
        extends ToDtoMapper<D, E>, IgnoreHelper<D, E> {
}
