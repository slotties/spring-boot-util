package com.github.boot.tomcat

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

/**
 * @author Stefan Lotties
 */
class RemoteAddrFilterBuilderSpec extends Specification {
    @Unroll("usingRequestRemoteAddr=#usingRequestRemoteAddr")
    def "usingRequestRemoteAddr"(boolean usingRequestRemoteAddr) {
        given:
        def builder = RemoteAddrFilterBuilder.build()

        when:
        builder.usingRequestRemoteAddr(usingRequestRemoteAddr)
        def filter = builder.create()

        then:
        filter.usingRequestRemoteAddr == usingRequestRemoteAddr

        where:
        usingRequestRemoteAddr << [ true, false ]
    }

    @Unroll("clientIpHeaders=#clientIpHeaders")
    def "clientIpHeaders"(String[] clientIpHeaders) {
        given:
        def builder = RemoteAddrFilterBuilder.build()

        when:
        builder.clientIpHeaders(clientIpHeaders)
        def filter = builder.create()

        then:
        filter.clientIpHeaderNames == clientIpHeaders

        where:
        clientIpHeaders << [
                null,
                [ "foo" ].toArray(new String[1]),
                [ "foo", "bar" ].toArray(new String[2])
        ]
    }
}
