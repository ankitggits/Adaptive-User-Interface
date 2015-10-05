package com.aui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aui.pojo.ResponseData;
import com.aui.pojo.SecurityQuestion;
import com.aui.pojo.User;
import com.aui.service.AuthenticationService;
import com.aui.service.ProfileService;
import com.aui.service.RegistrationService;

@Controller
public class RegistrationController {

	@Autowired
	RegistrationService registrationService;
	
	@Autowired
	ProfileService profileService;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@RequestMapping(value="/register", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public @ResponseBody ResponseData doRegister(@RequestBody User user,HttpServletRequest request, HttpServletResponse response){
		return registrationService.doRegistration(user,request, response);
	}
	
	@RequestMapping(value="/securityQuestions", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseData getSecurityQues(){
		return registrationService.retriveAllSecurityQues();
	}
	
	@RequestMapping(value="/populateSecurityQues", method = RequestMethod.POST, produces="application/json",consumes="application/json")
	public @ResponseBody ResponseData saveSecurityQues(@RequestBody List<SecurityQuestion> questions){
		return registrationService.saveSecurityQuestions(questions);
	}
	
	@RequestMapping(value="/userLevel", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseData getUserLevel(){
		String userName = authenticationService.getAuthenticatedUserName();
		return profileService.getUserLevel(userName);
	}
}
