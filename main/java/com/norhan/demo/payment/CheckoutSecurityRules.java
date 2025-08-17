package com.norhan.demo.payment;

import com.norhan.demo.common.SecurityRules;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

public class CheckoutSecurityRules implements SecurityRules {


    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry r) {

        r.requestMatchers(HttpMethod.POST, "/checkout").hasRole("CUSTOMER");
        r.requestMatchers(HttpMethod.POST, "/checkout/webhook").permitAll();
    }
}
