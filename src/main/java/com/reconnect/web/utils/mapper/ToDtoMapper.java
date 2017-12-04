package com.reconnect.web.utils.mapper;

import com.reconnect.web.utils.mapper.exceptions.MapperException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
public interface ToDtoMapper<D extends MappedDto, E extends MappedEntity> extends MapProcessor {

    /**
     * Custom conversion, will be called after copying any data.
     *
     * @param from - object from which should be copied data
     * @param to   - object where should be copied data
     */
    void customConversion(E from, D to);

    /**
     * Method that copies values from DTO to entity, excluding some specified fields.
     * Hard copy, because we don't care about connection with proxy-classes.
     *
     * @param from - object from will be copied values
     * @param to   - object where will be copied values
     */
    default void map(final E from, final D to) {

        final Class toClass = to.getClass();
        final Class fromClass = from.getClass();
        final Field[] fields = toClass.getDeclaredFields();

        Arrays.stream(fields).forEach(target -> {
            target.setAccessible(true);
            final String name = target.getName();
            try {
                final Field source = fromClass.getDeclaredField(name);
                source.setAccessible(true);
                target.set(to, source.get(from));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                // expected exceptions
            }
        });

        customConversion(from, to);
    }

    /**
     * Method that copies values from DTO to entity, excluding some specified fields.
     *
     * @param from - object from will be copied values
     */
    @SuppressWarnings("unchecked")
    default D map(final E from) {

        final Type[] arg = ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments();
        final D to;
        final Class<D> toClass = (Class<D>) arg[ARG_DTO];

        try {
            to = toClass.newInstance();
        } catch (final Exception e) {
            throw new MapperException("Unable to instantiate " + toClass.getClass().getSimpleName(), e);
        }

        map(from, to);
        return to;
    }
}
