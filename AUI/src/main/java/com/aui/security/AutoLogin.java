package com.aui.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

/**
 * @author sinankit
 *
 */
@Component
public class AutoLogin {

	@Autowired
	@Qualifier("authenticationManager")
	AuthenticationManager authenticationManager;
	
	@Autowired
	SecurityContextRepository securityContextRepository;
	
	public boolean doAutoLogin(com.aui.pojo.Authentication authentication,HttpServletRequest request, HttpServletResponse response){
		String username=authentication.getUsername();
        String password=authentication.getPassword();
        username = username.trim();
        
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        
        Authentication auth = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
        
        return auth.isAuthenticated();
	}
	
	
	/*
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
	}*/
}
