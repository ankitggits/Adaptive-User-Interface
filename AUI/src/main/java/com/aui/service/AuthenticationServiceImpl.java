package com.aui.service;

import static com.aui.util.Constants.STATUS_SUCCESS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.aui.pojo.ResponseData;
import com.aui.security.AutoLogin;
import com.aui.util.Constants;

public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	AutoLogin autoLogin;
	
	@Autowired
	ApplicationContext context;
	
	@Override
	public String getAuthenticatedUserName() {
		
		String userName = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth!=null && !(auth instanceof AnonymousAuthenticationToken)){
			userName = (String)auth.getPrincipal();
		}
		/*userName = "ankit@abc.com";*/
		return userName;
	}

	@Override
	public ResponseData doUserLogin(com.aui.pojo.Authentication authentication,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseData responseData = null;
		try{
			responseData = context.getBean(ResponseData.class);
	        if(autoLogin.doAutoLogin(authentication,request,response)){
	        	responseData.setData(authentication.getUsername());
				responseData.setStatus(STATUS_SUCCESS);
	        }else{
	        	responseData.setMessage("authentication failure");
	    		responseData.setStatus(Constants.STATUS_FAILURE);
	        }
		}catch(Exception e){
			responseData.setErrorMessage(e.toString());
    		responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}
	
}
