package com.gopal.blog.config;

public class AppConstants {

	public static final String PAGE_NUMBER = "0";
	public static final String PAGE_SIZE = "5";
	public static final String SORT_BY ="postId";
	public static final String SORT_DIRECTION = "ASC";
	public static final Integer NORMAL_USER = 501;
	public static final Integer ADMIN_USER = 502;
	public static final String[] PUBLIC_URLS = {
		"/api/v1/auth/login",
		"/v3/api-docs",
		"/v2/api-docs",
		"/swagger-ui/**",
		"/swagger-resources/**",
		"/api/users/registerUser",
		"/webjars/**",
		"/api/post/image/{imageName}"
		
		
	};
}
