package com.reconnect.web.utils.mapper.exceptions;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
public class MapperException extends RuntimeException {

    public MapperException(final String msg) {
        super(msg);
    }

    public MapperException(final String msg, final Throwable e) {
        super(msg, e);
    }
}
