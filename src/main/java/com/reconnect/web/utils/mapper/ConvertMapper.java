package com.reconnect.web.utils.mapper;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
public interface ConvertMapper<D extends MappedDto, E extends MappedEntity>
        extends ConvertToDtoMapper<D, E>, ConvertToEntityMapper<D, E>, Mapper<D, E> {
}
