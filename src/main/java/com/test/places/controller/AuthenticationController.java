package com.test.places.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.test.places.config.CustomUserDetailService;
import com.test.places.config.JwtUtil;
import com.test.places.model.AuthenticationRequest;
import com.test.places.model.AuthenticationResponse;
import com.test.places.model.DataTransferObjectUsers;

@RestController
public class AuthenticationController implements RequestHandler<AuthenticationRequest, String> {

	@Autowired	
	AuthenticationManager authManager;
	@Autowired
	CustomUserDetailService userDetailService;
	@Autowired
	JwtUtil jwtToken;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
					authenticationRequest.getPassword()));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtToken.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> newUser(@RequestBody DataTransferObjectUsers usersDto) throws Exception{
		return ResponseEntity.ok(userDetailService.saveUser(usersDto));
	}

	@Override
	public String handleRequest(AuthenticationRequest event, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log("EVENT Count: " + event.toString());
		return event.toString();
	}

}
