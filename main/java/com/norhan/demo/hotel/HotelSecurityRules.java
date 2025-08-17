package com.norhan.demo.hotel;

import com.norhan.demo.common.SecurityRules;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class HotelSecurityRules implements SecurityRules {
    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry r) {


        r.requestMatchers(HttpMethod.GET, "/hotels", "/hotels/**").permitAll();

        // Manager CRUD (إدارة فنادق المانجر الحالي)
        r.requestMatchers("/manager/hotels/**").hasRole("MANAGER");
    }
}