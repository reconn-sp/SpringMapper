package com.reconnect.web.utils.mapper;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * Bean class for storing global configurations of mapper and collecting optimization info.
 *
 * <p>For initialization required {@link MapperProperties} instance.
 * By default it's contain values from application properties. There
 * is only global properties.
 *
 * <p>Configuration examples:
 * <ul>
 * <li>Java-configuration: {@code @Bean
 * public MapperBean mapperBean() {
 * final MapperProperties properties = new MapperProperties();
 * properties.getPackages().add("com.reconnect.web.utils.example.auth.mappers");
 * properties.getPackages().add("com.reconnect.web.utils.example.mappers");
 * properties.setIgnoreToEntity(Collections.singletonList("id"));
 * return new MapperBean(properties);
 * }}</li>
 * <li>Properties configuration: {@code
 * spring.mapper.ignore-to-entity=id
 * spring.mapper.ignore-to-dto=password, ssn
 * spring.mapper.packages=com.reconnect.web.utils.example.auth.mappers, com.reconnect.web.utils.example.mappers
 * }</li>
 * </ul>
 *
 * @author s.vareyko
 * @since 19.02.18
 */
@Component
public class MapperBean {

    private static final Map<Class, Set<String>> IGNORE_TO_ENTITY = new HashMap<>();
    private static final Map<Class, Set<String>> IGNORE_TO_DTO = new HashMap<>();
    private static MapperBean INSTANCE;

    private List<String> defaultIgnoredToDto;
    private List<String> defaultIgnoredToEntity;
    private List<String> packagesToScan;

    /**
     * Default constructor that just write in the static field {@link #INSTANCE} list to himself.
     * Field used for accessing methods inside of bean.
     *
     * @param mapperProperties configuration bean that read properties
     */
    public MapperBean(final MapperProperties mapperProperties) {
        INSTANCE = this;
        defaultIgnoredToDto = mapperProperties.getIgnoreToDto();
        defaultIgnoredToEntity = mapperProperties.getIgnoreToEntity();
        packagesToScan = mapperProperties.getPackages();
        readPackages();
    }

    /**
     * Method for obtaining list of ignored field to DTO. Additionally
     * can register mapper that not registered yet.
     *
     * @param cls mapper class for obtaining list of fields or registration
     * @return list of ignored fields
     */
    public static Set<String> getIgnoredToDto(final Class cls) {
        return Optional.ofNullable(IGNORE_TO_DTO.get(cls)).orElseGet(() -> {
            final MapperComponent annotation = (MapperComponent) cls.getDeclaredAnnotation(MapperComponent.class);
            MapperBean.INSTANCE.registerMapper(cls, annotation);
            return IGNORE_TO_DTO.get(cls);
        });
    }


    /**
     * Method for obtaining list of ignored field to Entity. Additionally can
     * register mapper that not registered yet.
     *
     * @param cls mapper class for obtaining list of fields or registration
     * @return list of ignored fields
     */
    public static Set<String> getIgnoredToEntity(final Class cls) {
        return Optional.ofNullable(IGNORE_TO_ENTITY.get(cls)).orElseGet(() -> {
            final MapperComponent annotation = (MapperComponent) cls.getDeclaredAnnotation(MapperComponent.class);
            MapperBean.INSTANCE.registerMapper(cls, annotation);
            return IGNORE_TO_ENTITY.get(cls);
        });
    }

    /**
     * Helper method for registration of mapper instance. Registration just collect
     * all ignored fields and save into special map for quick access in the future.
     *
     * @param type   class that implements mapper
     * @param mapper annotation
     */
    private void registerMapper(final Class type, final MapperComponent mapper) {
        final Set<String> toDto = new TreeSet<>();
        final Set<String> toEntity = new TreeSet<>();

        if (Objects.nonNull(mapper)) {
            toDto.addAll(Arrays.asList(mapper.ignoreToDto()));
            toEntity.addAll(Arrays.asList(mapper.ignoreToEntity()));
        }

        toDto.addAll(defaultIgnoredToDto);
        toEntity.addAll(defaultIgnoredToEntity);

        IGNORE_TO_DTO.put(type, toDto);
        IGNORE_TO_ENTITY.put(type, toEntity);
    }

    /**
     * Helper method for reading specified packages and registration of all found
     * Mapper instances. Registration collect into special maps info, such as list
     * of ignored fields. Method automatically reads {@link #packagesToScan} property
     * of {@link MapperBean}.
     */
    private void readPackages() {
        if (Objects.isNull(packagesToScan)) {
            return;
        }

        final ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(true);

        scanner.addIncludeFilter(new AnnotationTypeFilter(MapperComponent.class));

        for (final String packageName : packagesToScan) {
            for (final BeanDefinition definition : scanner.findCandidateComponents(packageName)) {

                final Class<?> resolvedClass;

                try {
                    final ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) definition;
                    resolvedClass = beanDefinition.resolveBeanClass(ClassUtils.getDefaultClassLoader());
                } catch (final ClassNotFoundException e) {
                    throw new MapperException("Something went wrong when tried to resolve bean class", e);
                }

                final MapperComponent[] annotations = resolvedClass.getAnnotationsByType(MapperComponent.class);
                if (annotations.length > 0) {
                    final MapperComponent mapper = annotations[0];
                    registerMapper(resolvedClass, mapper);
                }
            }
        }
    }
}
