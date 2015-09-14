package com.github.boot.tomcat

import spock.lang.Specification
import spock.lang.Subject

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Stefan Lotties
 */
class RemoteAddrFilterSpec extends Specification {
    @Subject
    RemoteAddrFilter filter = new RemoteAddrFilter()

    def "allow ip from HttpServletRequest by headers and fallback"() {
        given:
        filter.usingRequestRemoteAddr = true
        filter.clientIpHeaderNames = [ "foo", "bar" ]
        def request = Mock(HttpServletRequest)
        request.getHeader("foo") >> null
        request.getHeader("bar") >> "127.0.0.1"

        filter.allow = "127\\.0\\.0\\.1"

        def chain = Mock(FilterChain)
        def response = Mock(HttpServletResponse)

        when:
        filter.doFilter(request, response, chain)

        then:
        1 * chain.doFilter(request, response)
        0 * response.sendError(_)
    }

    def "deny ip from HttpServletRequest by headers and fallback"() {
        given:
        filter.usingRequestRemoteAddr = true
        filter.clientIpHeaderNames = [ "foo", "bar" ]
        def request = Mock(HttpServletRequest)
        request.getHeader("foo") >> "192.168.0.1"
        request.getHeader("bar") >> "127.0.0.1"

        filter.allow = "127\\.0\\.0\\.1"

        def chain = Mock(FilterChain)
        def response = Mock(HttpServletResponse)

        when:
        filter.doFilter(request, response, chain)

        then:
        0 * chain.doFilter(request, response)
        1 * response.sendError(403)
    }
}
