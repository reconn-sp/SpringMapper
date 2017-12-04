package com.reconnect.web.utils.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

import static com.reconnect.web.utils.mapper.MapperUtils.ARG_DTO;
import static com.reconnect.web.utils.mapper.MapperUtils.ARG_ENTITY;
import static com.reconnect.web.utils.mapper.MapperUtils.copyByAccessors;
import static com.reconnect.web.utils.mapper.MapperUtils.instantiate;

/**
 * Base interface that provide methods for object conversions.
 *
 * <p>Mappers should implement this interface with specified {@link MappedDto}
 * and {@link MappedEntity} classes. So each POJO must implement one of them.
 * Conversion made via field accessor, so each field should have getter and
 * setter. Fields without getters will be ignored. Other configurations is
 * optional and can be not specified.
 *
 * <p>Interface provide two groups of methods - conversion with return and
 * conversion with specifying of target object. Conversion with return:
 * <ul>
 * <li>{@link #map(MappedEntity)}</li>
 * <li>{@link #map(MappedDto)}</li>
 * </ul>
 * Both method return object of different type, but before it's create a new
 * instance required for return object. Conversion with target object:
 * <ul>
 * <li>{@link #map(MappedEntity, MappedDto)}</li>
 * <li>{@link #map(MappedDto, MappedEntity)}</li>
 * </ul>
 * Copies values to already created object
 *
 * <p>At first call of not registered mapper it's will be registered to increase
 * performance in the future. During registration will be collected all ignored
 * fields for current mapper.
 *
 * <p>Optionally can be configured list of ignored fields in the annotation
 * {@link MapperComponent}. These field will not be copied during conversion.
 * This provide additional flexibility, for example: when we don't need password
 * on user's DTO on the form, but it received from repository. Another one case
 * when we need prevent changing of some values in the Entity.
 *
 * <p>For extended conversion can be overridden methods:
 * <ul>
 * <li>{@link #customConversion(MappedDto, MappedEntity)}</li>
 * <li>{@link #customConversion(MappedEntity, MappedDto)}</li>
 * </ul>
 * These methods called after copying values, there is can be defined any
 * additional calculations and conversions. Main usage it's conversion of fields
 * with different types. {@link MapperComponent} extends
 * {@link org.springframework.stereotype.Component}, so there is can be injected
 * other beans and mappers without additional actions.
 *
 * @param <D> specify that there must be passed class marked as {@link MappedDto}
 * @param <E> specify that there must be passed class marked as {@link MappedEntity}
 * @author s.vareyko
 * @see MapperComponent
 * @see MappedDto
 * @see MappedEntity
 * @since 03.12.17
 */
@SuppressWarnings("unchecked")
public interface Mapper<D extends MappedDto, E extends MappedEntity> {

    /**
     * Method that copies values from entity to DTO, excluding some specified fields.
     *
     * @param from object from will be copied values
     * @param to   object where will be copied values
     */
    default void map(final E from, final D to) {

        final Set<String> ignore = MapperBean.getIgnoredToDto(this.getClass());
        copyByAccessors(from, to, ignore);
        customConversion(from, to);
    }

    /**
     * Method that copies values from DTO to entity, excluding some specified fields.
     *
     * @param from object from will be copied values
     * @param to   object where will be copied values
     */
    default void map(final D from, final E to) {

        final Set<String> ignore = MapperBean.getIgnoredToEntity(this.getClass());
        copyByAccessors(from, to, ignore);
        customConversion(from, to);
    }

    /**
     * Method that copies values from DTO to entity, excluding some specified fields.
     *
     * @param from object from will be copied values
     * @return new instance of required type
     */
    default D map(final E from) {

        final Type[] arg = ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments();
        final Class<D> toClass = (Class<D>) arg[ARG_DTO];
        final D to = instantiate(toClass);
        map(from, to);
        return to;
    }

    /**
     * Method that copies values from DTO to entity, excluding some specified fields.
     *
     * @param from object from will be copied values
     * @return new instance of required type
     */
    default E map(final D from) {

        final Type[] arg = ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments();
        final Class<E> toClass = (Class<E>) arg[ARG_ENTITY];
        final E to = instantiate(toClass);
        map(from, to);
        return to;
    }

    /**
     * Method for custom conversion of value from one object to second.
     *
     * @param from object for copying
     * @param to   target object
     */
    default void customConversion(final E from, final D to) {
        // override with custom implementation
    }

    /**
     * Method for custom conversion of value from one object to second.
     *
     * @param from object for copying
     * @param to   target object
     */
    default void customConversion(final D from, final E to) {
        // override with custom implementation
    }
}
