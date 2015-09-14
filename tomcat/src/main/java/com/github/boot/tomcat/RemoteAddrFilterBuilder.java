package com.github.boot.tomcat;

import java.util.Arrays;

/**
 * TODO
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

    public RemoteAddrFilter create() {
        return this.filter;
    }

    public void usingRequestRemoteAddr(boolean v) {
        filter.setUsingRequestRemoteAddr(v);
    }

    public void clientIpHeaders(String... headers) {
        if (headers == null) {
            filter.setClientIpHeaderNames(null);
        } else {
            filter.setClientIpHeaderNames(Arrays.asList(headers));
        }
    }
}
