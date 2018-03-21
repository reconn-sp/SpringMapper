package com.reconnect.web.utils.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;

/**
 * Class for some internal static methods.
 *
 * @author s.vareyko
 * @since 19.02.18
 */
final class MapperUtils {

    static final int ARG_DTO = 0;
    static final int ARG_ENTITY = 1;

    private static final String JAVA_LAZY = "_jvst";
    private static final Logger log = LoggerFactory.getLogger("mapper");

    /**
     * Default constructor hidden.
     *
     * @throws UnsupportedOperationException on instantiation
     */
    private MapperUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates an instance of provided type.
     *
     * @param <T> generic type for creation
     * @param cls class objects
     * @return new Instance created from class
     */
    static <T> T instantiate(final Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (final Exception e) {
            throw new MapperException("Unable to instantiate " + cls.getClass().getSimpleName(), e);
        }
    }

    /**
     * Implementation of soft copy of fields using getters/setters.
     *
     * @param from   object from will be copied values
     * @param to     object where will be copied values
     * @param ignore list of ignored fields
     */
    static void copyByAccessors(final Object from, final Object to, final Set<String> ignore) {
        Arrays.stream(getFields(from, to))
                .filter(field -> !Modifier.isStatic(field.getModifiers())
                        && !Modifier.isFinal(field.getModifiers())
                        && !ignore.contains(field.getName()))
                .forEach(field -> copyValue(field, from, to));
    }


    /**
     * Implementation of soft copy of fields using getters/setters.
     *
     * @param field current field for copy
     * @param from  object from will be copied values
     * @param to    object where will be copied values
     */
    private static void copyValue(final Field field, final Object from, final Object to) {

        final String name = field.getName();
        final Class<?> fromClass = from.getClass();
        final Class<?> toClass = to.getClass();
        try {

            final Method readMethod = new PropertyDescriptor(name, fromClass).getReadMethod();
            final Method writeMethod = new PropertyDescriptor(name, toClass).getWriteMethod();

            readMethod.setAccessible(true);
            writeMethod.setAccessible(true);

            writeMethod.invoke(to, readMethod.invoke(from));

        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            // we don't need anything here
        } catch (IllegalArgumentException e) {
            log.warn(String.format("Copying ignored: Field \"%s\" have different types in the %s and %s. " +
                            "Implement custom conversion instead. Add field to \"ignore\" to stop seeing this warning.",
                    name, fromClass.getCanonicalName(), toClass.getCanonicalName()));
        }
    }

    /**
     * Helper method that load list of fields for copy.
     *
     * @param from object from will be copied values
     * @param to   object where will be copied values
     * @return array of fields
     */
    private static Field[] getFields(final Object from, final Object to) {
        final Class<?> toClass = to.getClass();
        if (toClass.getSimpleName().contains(JAVA_LAZY)) {
            return from.getClass().getDeclaredFields();
        }
        return toClass.getDeclaredFields();
    }
}
