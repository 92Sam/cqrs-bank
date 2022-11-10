package com.company.bankservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//@Configuration
@Component
public class SimpleCORSFilter extends WebSecurityConfigurerAdapter {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.POST.name()
                , HttpMethod.GET.name()
                , HttpMethod.PUT.name()
                , HttpMethod.PATCH.name()
                , HttpMethod.DELETE.name()
        ));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        configuration.setAllowedHeaders(
                Arrays.asList(
                        "Content-Type"
                        , "Cache-Control"
                        , "Authorization"
                )
        );
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().anyRequest().permitAll();
        http.cors();
    }
}