/**
 * 
 */
package com.kamble.app.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kamble.app.filter.JwtFilter;
import com.kamble.app.service.MyUserDetailsService;

/**
 * @author 1997s
 *
 */

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter{

	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;


	//For Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//Passing MyUserDetailsService object, so spring will interally call its loadUsername method and the get the user
		auth.userDetailsService(myUserDetailsService);
		
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		//Disable security for "authenticate" url, for rest all do authentication
		http.csrf().disable()
			.authorizeRequests().antMatchers("/authenticate").permitAll()
				.anyRequest().authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);	//Telling Spring not to bother about sessions
		
				//Adding Our filter to every requests
				http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	
	
	//Creating Bean for AuthenticationManager
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManager();
	}


	//We are telling Spring not to encode the passwords
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
}
