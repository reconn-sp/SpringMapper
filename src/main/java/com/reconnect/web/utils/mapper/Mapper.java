package com.reconnect.web.utils.mapper;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
public interface Mapper<D extends MappedDto, E extends MappedEntity>
        extends MapProcessor, ToDtoMapper<D, E>, ToEntityMapper<D, E> {
}
