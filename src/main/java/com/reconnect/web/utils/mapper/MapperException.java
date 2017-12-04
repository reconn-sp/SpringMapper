package com.reconnect.web.utils.mapper;

/**
 * Default internal exception.
 *
 * @author s.vareyko
 * @since 06.02.18
 */
class MapperException extends RuntimeException {

    /**
     * Constructor with error message.
     *
     * @param msg string message
     * @param ex  original exception, if was something else
     */
    MapperException(final String msg, final Throwable ex) {
        super(msg, ex);
    }
}
