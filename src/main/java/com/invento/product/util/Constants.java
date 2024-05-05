package com.invento.product.util;

import java.util.Arrays;
import java.util.List;

public class Constants {

	public static final String JWT_KEY = "JDIwZGVkaWNhdGVkU2VjcmV0LjAyY3VzdG9tZXIlMjRsb2dpbkBrZXk=";
	public static final String JWT_HEADER = "Authorization";
	public static final String CSRF_REQUEST_ATTR_NAME = "_csrf";
	public static final String ADMIN = "ADMIN";
	public static final String READ = "READ";
	public static final String ASTERISK = "*";
	public static final String USERNAME = "username";
	public static final String AUTHORITIES = "authorities";
	
	public static final String DEFAULT_PAGE_NUMBER_VALUE = "0";
	public static final String DEFAULT_PAGE_SIZE_VALUE = "5";
	public static final String DEFAULT_CATEGORY = "category";
	public static final String DESC = "DESC";
	
	public static final String PRODUCT_COLLECTION = "product";
	public static final List<String> SEARCH_PRODUCT_FIELDS = 
			Arrays.asList("category", "spec", "brand");
	public static final List<String> INCLUDE_PRODUCT_FIELDS = 
			Arrays.asList("category", "spec", "brand", "imagename");
}
