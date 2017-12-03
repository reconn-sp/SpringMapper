package com.reconnect.web.utils.mapper;

import java.util.Collections;
import java.util.List;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
interface IgnoreHelper<D extends MappedDto, E extends MappedEntity> extends MapProcessor {

    @Override
    default List<String> getIgnored() {
        return Collections.emptyList();
    }
}
