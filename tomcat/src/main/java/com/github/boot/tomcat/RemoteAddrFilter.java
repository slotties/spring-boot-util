package com.github.boot.tomcat;

import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometFilterChain;
import org.apache.catalina.filters.RequestFilter;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * This filter allows/denies request based on a list of subnet masks.
 *
 * @author Stefan Lotties
 */
public class RemoteAddrFilter extends RequestFilter {
    private static final Log log = LogFactory.getLog(RemoteAddrFilter.class);

    private List<String> clientIpHeaderNames = null;
    private boolean usingRequestRemoteAddr = true;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.process(resolveClientIp(request), request, response, chain);
    }

    public void doFilterEvent(CometEvent event, CometFilterChain chain) throws IOException, ServletException {
        this.processCometEvent(resolveClientIp(event.getHttpServletRequest()), event, chain);
    }

    private String resolveClientIp(ServletRequest request) {
        String clientIp = null;

        if (request instanceof HttpServletRequest) {
            if (this.clientIpHeaderNames != null) {
                for (Iterator<String> headerName = this.clientIpHeaderNames.iterator(); headerName.hasNext() && clientIp == null; ) {
                    clientIp = ((HttpServletRequest) request).getHeader(headerName.next());
                }
            }
        }

        // Fallback to the remote address provided by the JEE API.
        if (clientIp == null && usingRequestRemoteAddr) {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;
    }

    protected Log getLogger() {
        return log;
    }

    public void setClientIpHeaderNames(List<String> clientIpHeaderNames) {
        this.clientIpHeaderNames = clientIpHeaderNames;
    }

    public void setUsingRequestRemoteAddr(boolean usingRequestRemoteAddr) {
        this.usingRequestRemoteAddr = usingRequestRemoteAddr;
    }

    public boolean isUsingRequestRemoteAddr() {
        return usingRequestRemoteAddr;
    }

    public List<String> getClientIpHeaderNames() {
        return clientIpHeaderNames;
    }
}
