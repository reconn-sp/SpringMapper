package com.reconnect.web.utils.mapper;


import java.util.List;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
interface MapProcessor {

    /**
     * Return list of ignored field names.
     *
     * @return list of ignored fields
     */
    List<String> getIgnored();
}
