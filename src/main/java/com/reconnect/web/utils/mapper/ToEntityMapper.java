package com.reconnect.web.utils.mapper;

import com.reconnect.web.utils.mapper.exceptions.MapperException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
@SuppressWarnings("unchecked")
public interface ToEntityMapper<D extends MappedDto, E extends MappedEntity> extends MapProcessor {

    /**
     * Custom conversion, will be called after copying any data.
     *
     * @param from - object from which should be copied data
     * @param to   - object where should be copied data
     */
    void customConversion(D from, E to);

    /**
     * Method that copies values from DTO to entity, excluding some specified fields.
     * Soft copy via setters.
     *
     * @param from - object from will be copied values
     * @param to   - object where will be copied values
     */
    default void map(final D from, final E to) {

        final Class toClass = to.getClass();
        final Class fromClass = from.getClass();
        final Method[] methods = toClass.getDeclaredMethods();

        Arrays.stream(methods).forEach(setter -> {
            setter.setAccessible(true);
            final String name = setter.getName();
            try {
                if (name.startsWith("set")) {
                    final String methodName = name.substring(3);
                    final String c = String.valueOf(methodName.charAt(0));
                    if (getIgnored().contains(methodName.replaceFirst(c, c.toUpperCase()))) {
                        return;
                    }

                    final Method getter = fromClass.getDeclaredMethod(
                            (boolean.class.equals(setter.getParameterTypes()[0]) ? "is" : "get") + methodName);
                    getter.setAccessible(true);
                    setter.invoke(to, getter.invoke(from));
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
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
    default E map(final D from) {

        final Type[] arg = ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments();
        final E to;
        final Class<E> toClass = (Class<E>) arg[ARG_ENTITY];

        try {
            to = toClass.newInstance();
        } catch (final Exception e) {
            throw new MapperException("Unable to instantiate " + toClass.getClass().getSimpleName(), e);
        }
        map(from, to);
        return to;
    }
}
