package com.reconnect.web.utils.mapper;

import com.reconnect.web.utils.mapper.annotation.Mapper;
import com.reconnect.web.utils.mapper.exceptions.MapperException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
public class MapperPackages {

    static final Map<Class<? extends MappedDto>, Class<? extends MappedEntity>> DTO_CNV = new HashMap<>();
    static final Map<Class<? extends MappedEntity>, Class<? extends MappedDto>> ENTITY_CNV = new HashMap<>();
    private final Map<Class<? extends MappedEntity>, Boolean> REGISTERED = new HashMap<>();

    /**
     * Constructor if you need just one package.
     *
     * @param pack - package name for scanning
     */
    public MapperPackages(final String pack) {
        new MapperPackages(Collections.singletonList(pack));
    }

    /**
     * Constructor for list of packages
     *
     * @param packages - package names for scanning
     * @throws MapperException if specified class not inherited from Mapper Entity or DTO
     */
    @SuppressWarnings("unchecked")
    public MapperPackages(final List<String> packages) {

        final ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(true);

        scanner.addIncludeFilter(new AnnotationTypeFilter(Mapper.class));

        for (final String packageName : packages) {
            for (final BeanDefinition definition : scanner.findCandidateComponents(packageName)) {

                final ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) definition;
                final Class<?> resolvedClass;

                try {
                    resolvedClass = beanDefinition.resolveBeanClass(ClassUtils.getDefaultClassLoader());
                } catch (final ClassNotFoundException e) {
                    throw new MapperException("Something went wrong when tried to resolve bean class", e);
                }

                final Mapper mapper = resolvedClass.getAnnotationsByType(Mapper.class)[0];
                final Class<? extends MappedDto> target = mapper.target();
                final Type[] arguments =
                        ((ParameterizedType) resolvedClass.getGenericInterfaces()[0]).getActualTypeArguments();

                final Class<MappedDto> dto = (Class<MappedDto>) arguments[0];
                final Class<MappedEntity> entity = (Class<MappedEntity>) arguments[1];

                if (target.equals(MappedDto.class)) {
                    checkAssignation(dto, MappedDto.class);
                    checkAssignation(entity, MappedEntity.class);
                    DTO_CNV.put(dto, entity);
                    ENTITY_CNV.put(entity, dto);
                    registerEntity(entity, false);
                    continue;
                }
                registerEntity(entity, true);
            }
        }
    }

    /**
     * Helper method, that check marks on classes and throw exception if it's not marked.
     *
     * @param cls    - class for check
     * @param target - required class
     * @throws MapperException that explain problem
     */
    private void checkAssignation(final Class<?> cls, final Class<?> target) {
        if (!target.isAssignableFrom(cls)) {
            throw new MapperException("Mapped class \"" + cls.getSimpleName() + "\" not extended from "
                    + target.getClass().getTypeName());
        }
    }

    /**
     * Helper method, that check for already registered throw an exception if there is
     */
    private void registerEntity(final Class<? extends MappedEntity> entity, final boolean byTarget) {
        final Boolean isAddedWithTarget = REGISTERED.get(entity);

        if (Objects.isNull(isAddedWithTarget)) {
            REGISTERED.put(entity, byTarget);
            return;
        }

        if (!isAddedWithTarget || !byTarget) {
            throw new MapperException("There is already exists mapped entity " + entity.getTypeName() +
                    ". Please specify target for each of mappers");
        }
    }
}
