package com.reconnect.web.utils.mapper.annotation;

import com.reconnect.web.utils.mapper.MappedDto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapper {

    /**
     * Should be specified if entity can be converted more than to one DTO.
     * Will be ignored if not overridden.
     *
     * @return type extended from dto.
     */
    Class<? extends MappedDto> target() default MappedDto.class;
}
