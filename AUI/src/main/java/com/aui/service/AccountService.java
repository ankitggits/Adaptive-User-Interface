package com.aui.service;

import com.aui.pojo.Authentication;
import com.aui.pojo.ResponseData;

public interface AccountService {

	ResponseData existingUserQuestionAnswer(String username);
	
	ResponseData validatePassword(Authentication authentication);
	
	ResponseData updateSecurityQuestions(Authentication authentication, String userName);
}
