package com.invento.product.util;

import java.util.Arrays;
import java.util.List;

public class ProductConstants {

	public static final String JWT_KEY = "JDIwZGVkaWNhdGVkU2VjcmV0LjAyY3VzdG9tZXIlMjRsb2dpbkBrZXk=";
	public static final String JWT_HEADER = "Authorization";
	
	public static final List<String> SEARCH_PRODUCT_FIELDS = 
			Arrays.asList("category", "spec", "brand");
	public static final List<String> INCLUDE_PRODUCT_FIELDS = 
			Arrays.asList("category", "spec", "brand", "imagename");
}
