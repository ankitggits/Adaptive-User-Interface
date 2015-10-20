package com.aui.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aui.pojo.Authentication;
import com.aui.pojo.ResponseData;


public interface AuthenticationService {

	String getAuthenticatedUserName();
	
	ResponseData doUserLogin(Authentication authentication, HttpServletRequest request, HttpServletResponse response);
	
}
