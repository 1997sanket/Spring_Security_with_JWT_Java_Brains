/**
 * 
 */
package com.kamble.app.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 1997s
 *
 */


//Here we can Load user from the database and then return new User (Right now, I've just hardcoded)
@Service
public class MyUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 
		return new User("foo", "foo", new ArrayList<>());	//HardCoding
				
	}

	
}
