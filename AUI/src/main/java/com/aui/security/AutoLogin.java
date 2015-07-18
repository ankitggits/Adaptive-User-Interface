package com.aui.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sinankit
 *
 */
@Component
@Transactional
public class AutoLogin {

	@Autowired
	private UserDetailsService userDetailsService;
	
	public void doAutoLogin(String username, String password) {
	    try {
	        User user = (User) userDetailsService.loadUserByUsername(username);
	        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), password, user.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	    } catch (Exception e) {
	        SecurityContextHolder.getContext().setAuthentication(null);
	        System.out.println("Failure in autoLogin");
	    }
	}
}
