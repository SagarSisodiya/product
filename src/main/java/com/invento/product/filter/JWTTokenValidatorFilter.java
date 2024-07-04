package com.invento.product.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.crypto.SecretKey;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.invento.product.util.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

	private final Logger log = LoggerFactory.getLogger(JWTTokenValidatorFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String jwt = request.getHeader(Constants.JWT_HEADER);
		String url = request.getRequestURI();

		if (Arrays.asList(Constants.SWAGGER_WHITELIST).contains(url) || url.contains(Constants.SWAGGER_UI)
				|| url.contains(Constants.API_DOCS)) {
			chain.doFilter(request, response);
		}
		if (Strings.isEmpty(jwt)) {
			throw new JwtException("Jwt token not found in header.");
		}
		if (jwt.startsWith(Constants.BEARER)) {
			jwt = jwt.substring(7);
		}
		SecretKey key = Keys.hmacShaKeyFor(Constants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		String username = String.valueOf(claims.get(Constants.USERNAME));
		String authorities = String.valueOf(claims.get(Constants.AUTHORITIES));
		Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
			AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(request, response);
		log.info("Token validated successfully");
	}
}