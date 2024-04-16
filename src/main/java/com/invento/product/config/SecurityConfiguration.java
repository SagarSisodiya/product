package com.invento.product.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.invento.product.filter.CsrfCookieFilter;
import com.invento.product.filter.JWTTokenValidatorFilter;
import com.invento.product.util.ProductConstants;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfiguration {
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		
		CsrfTokenRequestAttributeHandler handler = new CsrfTokenRequestAttributeHandler();
		handler.setCsrfRequestAttributeName("_csrf");
		
		http
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedMethods(Collections.singletonList("*"));					
					config.setAllowedOrigins(Arrays.asList("http://localhost:4200","http://localhost:8081"));	//Collections.singletonList("http://localhost:4200")					
					config.setAllowedHeaders(Collections.singletonList("*"));
					config.setAllowCredentials(true);
					config.setExposedHeaders(Arrays.asList(ProductConstants.JWT_HEADER));
					config.setMaxAge(3600L);
					return config;
				}
			}))
			.csrf(csrf -> csrf
				.csrfTokenRequestHandler(handler)
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.addFilterAfter(new CsrfCookieFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JWTTokenValidatorFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(request -> request
			.requestMatchers("/swagger-ui.html").permitAll()
			.requestMatchers("/product/**").authenticated())
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults());
		return http.build();
	}
	
	@Bean 
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
