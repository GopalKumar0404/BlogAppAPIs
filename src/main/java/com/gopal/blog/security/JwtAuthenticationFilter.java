package com.gopal.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gopal.blog.exceptions.TokenNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// get Token
		final String requestToken = request.getHeader("Authorization");

		
		// starts with Bearer

		System.out.println("Token " +requestToken);
		String userName=null;
		String token=null;
		if (requestToken != null && requestToken.startsWith("Bearer")) {

			token = requestToken.substring(7);
			System.out.println(token);

			try {
				userName = this.jwtTokenHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get Jwt token");
			} catch (ExpiredJwtException e) {
				System.out.println("Jwt token has expired");
				throw new ExpiredJwtException(null, null, requestToken);
				
			} catch (MalformedJwtException e) {
				System.out.println("invalid jwt");
				

			}

		} else {
			System.out.println("Jwt token does not begin with Bearer");
			
		}
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			

			if (this.jwtTokenHelper.validateToken(token, userDetails)) {
//				System.out.println("abc");
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				System.out.println("Invalid Jwt Token");
			}
		} else {
			System.out.println("Invalid username or Authentication");
		}
		filterChain.doFilter(request, response);
	}

}
