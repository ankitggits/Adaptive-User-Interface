package com.aui.service;

import com.aui.pojo.Authentication;
import com.aui.pojo.ResponseData;

public interface ForgotPasswordService {

	ResponseData existingUserQuestions(String username);
	
	ResponseData validateSecurityQuesAns(Authentication authentication);
	
	ResponseData resetPassword(Authentication authentication);
}
