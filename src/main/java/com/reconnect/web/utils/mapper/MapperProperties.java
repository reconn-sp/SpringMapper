package com.reconnect.web.utils.mapper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration class for mapper, that automatically obtain values from properties.
 * Use new instance for configuring with Java-configuration.
 *
 * @author s.vareyko
 * @see MapperBean
 * @since 20.02.18
 */
@Configuration
@ConfigurationProperties(prefix = "spring.mapper")
public class MapperProperties {

    /**
     * List of fields for excluding during copying to {@link MappedEntity}.
     */
    private List<String> ignoreToEntity = new ArrayList<>();

    /**
     * List of fields for excluding during copying to {@link MappedDto}.
     */
    private List<String> ignoreToDto = new ArrayList<>();

    /**
     * List of packages for searching Mapper instances.
     */
    private List<String> packages = new ArrayList<>();

    public List<String> getIgnoreToEntity() {
        return ignoreToEntity;
    }

    public void setIgnoreToEntity(final List<String> ignoreToEntity) {
        this.ignoreToEntity = ignoreToEntity;
    }

    public List<String> getIgnoreToDto() {
        return ignoreToDto;
    }

    public void setIgnoreToDto(final List<String> ignoreToDto) {
        this.ignoreToDto = ignoreToDto;
    }

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(final List<String> packages) {
        this.packages = packages;
    }
}
