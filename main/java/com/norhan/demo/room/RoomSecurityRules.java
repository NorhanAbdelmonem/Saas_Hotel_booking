package com.norhan.demo.room;


import com.norhan.demo.common.SecurityRules;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class RoomSecurityRules implements SecurityRules {
    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
      registry.requestMatchers(HttpMethod.GET, "/rooms", "/rooms/**", "/hotels/*/rooms", "/hotels/*/rooms/**").permitAll();

       // registry.requestMatchers("/rooms","/rooms/**").permitAll(); // Public
        registry.requestMatchers("/manager/rooms/**").hasRole("MANAGER"); // Manager only



    }
}