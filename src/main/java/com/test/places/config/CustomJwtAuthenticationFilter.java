package com.test.places.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	JwtUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
		String jwtToken = extractJwtFromRequest(request);

		// to check if the given token is valid or not....
		if (StringUtils.hasText(jwtToken) && jwtTokenUtil.validateToken(jwtToken)) {

			// if the token is valid well be creating the User Object.
			UserDetails userDetails = new User(jwtTokenUtil.getUsernameFromToken(jwtToken), "",
					jwtTokenUtil.getRoleFromToken(jwtToken));

			// using the above created UserObject we will create User-name password
			// authentication token..
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, "", jwtTokenUtil.getRoleFromToken(jwtToken));

			// now storing this user-name password authentication token object in the
			// security context.
			// this is done to tell spring security that this particular user is authorized.
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		} else {
			System.out.println("Cannot set the Security Context");
		}
		}catch(ExpiredJwtException e) {
			request.setAttribute("exception", e);
		}
		catch(BadCredentialsException e) {
			request.setAttribute("exception", e);
		}
		
//calling the other filters in the filter chain
		filterChain.doFilter(request, response);

	}

	//extract the token content which contains ("bearer "+token value).
	//Separating the token value and storing in the jwtToken object in doFilterInternal method above
	private String extractJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}

		return null;
	}

}
