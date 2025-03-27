package com.tmdigital.gestiondestock.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tmdigital.gestiondestock.services.auth.ApplicationUserDetailsService;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    final String APP_ROOT = "/api/v1";

    @Autowired
    private ApplicationRequestFilter applicationRequestFilter;

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        final String[] routesAllowed = {
            APP_ROOT+ "/register",
            APP_ROOT+ "/authenticate",
            // // SWAGGER
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // "/swagger-ui.html",

        };

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers(routesAllowed).permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(applicationRequestFilter, UsernamePasswordAuthenticationFilter.class)
            ;

        return http.build();
    }

    @Bean
	public AuthenticationManager authenticationManager(ApplicationUserDetailsService app, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(app);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}

    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}    