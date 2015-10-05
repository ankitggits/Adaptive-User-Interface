package com.aui.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aui.pojo.ResponseData;
import com.aui.pojo.SecurityQuestion;
import com.aui.pojo.User;

public interface RegistrationService {

	ResponseData doRegistration(User user,HttpServletRequest request, HttpServletResponse response);
	
	ResponseData retriveAllSecurityQues();
	
	ResponseData retriveSecurityQuesByGroup(String group);
	
	ResponseData retriveSecurityQuesAnsByUserName(String userName);

	ResponseData saveSecurityQuestions(List<SecurityQuestion> questions);
	
}
