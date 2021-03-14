/**
 * 
 */
package com.kamble.app.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.kamble.app.model.AuthenticationRequest;
import com.kamble.app.model.AuthenticationResponse;
import com.kamble.app.service.MyUserDetailsService;
import com.kamble.app.util.JwtUtil;

/**
 * @author 1997s
 *
 */

@RestController
public class HelloController {
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;

	
	@GetMapping("/")
	public String hello() {
		return "hello";
	}
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		
		//System.out.println(authenticationRequest.getUsername());
		
		try {
			
			
			
			authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
					);	
			
		} catch(Exception e) {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Username or Password");
		}
		
		
		final UserDetails userDetails = myUserDetailsService
											.loadUserByUsername(authenticationRequest.getUsername());
		
		//generate token
		final String jwt = jwtUtil.generateToken(userDetails);
		
		//return token
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(jwt), HttpStatus.OK);
		
	}
}
