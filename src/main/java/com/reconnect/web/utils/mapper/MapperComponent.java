package com.reconnect.web.utils.mapper;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotations for additional configuration.
 *
 * <p>Annotated classes must implement {@link Mapper} interface for
 * correct usage.
 *
 * <p>If configured packages with mappers, classes marked with this
 * annotation collected at Spring Boot startup. There is included
 * global ignored fields, which merged with specified in each
 * annotation fields list. Details in {@link MapperProperties}.
 *
 * @author s.vareyko
 * @see Mapper
 * @see MapperProperties
 * @since 06.02.18
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperComponent {

    /**
     * List of string names of fields that should not be mapped.
     *
     * @return array of field names
     */
    String[] ignoreToDto() default {};

    /**
     * List of string names of fields that should not be mapped.
     *
     * @return array of field names
     */
    String[] ignoreToEntity() default {};
}
