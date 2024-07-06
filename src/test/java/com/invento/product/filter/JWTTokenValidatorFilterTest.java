package com.invento.product.filter;

import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.invento.product.util.Constants;
import com.invento.product.util.TestConstants;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenValidatorFilterTest {
	
//	@Test
//	public void testDoFilter() throws ServletException, IOException {
//		
//		JWTTokenValidatorFilter filter = new JWTTokenValidatorFilter();
//		 
//        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
//        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
//        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
//        Mockito.when(mockReq.getRequestURI()).thenReturn("/");
//        Mockito.when(mockReq.getHeader(Mockito.anyString())).thenReturn(TestConstants.MOCK_JWT_TOKEN);
//      	filter.doFilter(mockReq, mockResp, mockFilterChain);
//        filter.destroy();
//	}
	
	@Test
	public void testDoFilter_JwtException() throws ServletException, IOException {
		
		JWTTokenValidatorFilter filter = new JWTTokenValidatorFilter();
		 
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
        Mockito.when(mockReq.getRequestURI()).thenReturn("/");

        Assertions.assertThrows(JwtException.class, 
        	() -> filter.doFilter(mockReq, mockResp, mockFilterChain));
        filter.destroy();
	}
	
	@Test
	public void testDoFilter_SwaggerURI() throws ServletException, IOException {
		
		JWTTokenValidatorFilter filter = new JWTTokenValidatorFilter();
		 
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
        Mockito.when(mockReq.getRequestURI()).thenReturn("/swagger-ui/");

        Assertions.assertThrows(JwtException.class, 
        	() -> filter.doFilter(mockReq, mockResp, mockFilterChain));
        filter.destroy();
	}
	
	@Test
	public void testDoFilter_InvalidJwt() throws ServletException, IOException {
		
		JWTTokenValidatorFilter filter = new JWTTokenValidatorFilter();
		 
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
        when(mockReq.getRequestURI()).thenReturn("/");
        when(mockReq.getHeader(TestConstants.JWT_HEADER))
        .thenReturn(TestConstants.INVALID_MOCK_JWT_TOKEN);
        
        Assertions.assertThrows(SignatureException.class, 
        	() -> filter.doFilter(mockReq, mockResp, mockFilterChain));
        filter.destroy();
	}
	
	@Test
	public void testDoFilter_JwtWithBearer() throws ServletException, IOException {
		
		JWTTokenValidatorFilter filter = new JWTTokenValidatorFilter();
		 
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
        when(mockReq.getRequestURI()).thenReturn("/");
        when(mockReq.getHeader(TestConstants.JWT_HEADER))
        .thenReturn(TestConstants.BEARER +  TestConstants.INVALID_MOCK_JWT_TOKEN);
        
        Assertions.assertThrows(SignatureException.class, 
        	() -> filter.doFilter(mockReq, mockResp, mockFilterChain));
        filter.destroy();
	}
}
