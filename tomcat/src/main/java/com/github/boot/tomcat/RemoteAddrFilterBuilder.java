package com.github.boot.tomcat;

import java.util.Arrays;
import java.util.List;

/**
 * This builder is just another way to create a {@link RemoteAddrFilter}.
 * @author Stefan Lotties
 */
public class RemoteAddrFilterBuilder {
    private RemoteAddrFilter filter = new RemoteAddrFilter();

    /**
     * Create a new RemoteAddrFilterBuilder to configure and create a {@link RemoteAddrFilter}.
     * @return a new RemoteAddrFilterBuilder
     */
    public static RemoteAddrFilterBuilder build() {
        return new RemoteAddrFilterBuilder();
    }

    /**
     * Creates a {@link RemoteAddrFilter}.
     * @return never null
     */
    public RemoteAddrFilter create() {
        return this.filter;
    }

    /**
     * @see {@link RemoteAddrFilter#setUsingRequestRemoteAddr(boolean)}
     */
    public void usingRequestRemoteAddr(boolean v) {
        filter.setUsingRequestRemoteAddr(v);
    }

    /**
     * @see {@link RemoteAddrFilter#setClientIpHeaderNames(List)}
     */
    public void clientIpHeaders(String... headers) {
        if (headers == null) {
            filter.setClientIpHeaderNames(null);
        } else {
            filter.setClientIpHeaderNames(Arrays.asList(headers));
        }
    }
}
