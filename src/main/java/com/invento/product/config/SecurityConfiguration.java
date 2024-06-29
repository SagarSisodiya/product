package com.invento.product.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.invento.product.filter.JWTTokenValidatorFilter;
import com.invento.product.util.Constants;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfiguration {
		
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
				
		http
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedMethods(Collections.singletonList(Constants.ASTERISK));					
					config.setAllowedOrigins(Arrays.asList(Constants.ASTERISK));	
					config.setAllowedHeaders(Collections.singletonList(Constants.ASTERISK));
					config.setAllowCredentials(true);
					config.setExposedHeaders(Arrays.asList(Constants.JWT_HEADER));
					config.setMaxAge(3600L);
					return config;
				}
			}))
			.csrf(csrf -> csrf.disable())
			.addFilterBefore(new JWTTokenValidatorFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(request -> request
				.requestMatchers(Constants.SWAGGER_WHITELIST).permitAll()	
				.requestMatchers("/product/addProduct",
					"/product/updateProduct","/product/delete").hasRole(Constants.ADMIN)
				.requestMatchers("/product/**").hasAnyRole(Constants.ADMIN,Constants.READ))
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}
