package com.norhan.demo.booking;



import com.norhan.demo.common.SecurityRules;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class BookingSecurityRules implements SecurityRules {
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry r) {
        // Customer endpoints
        r.requestMatchers(HttpMethod.POST,   "/bookings").hasRole("CUSTOMER");   // create
        r.requestMatchers(HttpMethod.GET,    "/bookings/me").hasRole("CUSTOMER"); // list my bookings
        r.requestMatchers(HttpMethod.GET,    "/bookings/**").hasRole("CUSTOMER"); // get my booking by id
        r.requestMatchers(HttpMethod.PUT,    "/bookings/**").hasRole("CUSTOMER"); // update my booking
        r.requestMatchers(HttpMethod.DELETE, "/bookings/**").hasRole("CUSTOMER"); // delete my booking


        r.requestMatchers("/manager/bookings/**").hasRole("MANAGER");
    }
}
