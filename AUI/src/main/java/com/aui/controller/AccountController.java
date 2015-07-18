package com.aui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aui.pojo.Authentication;
import com.aui.pojo.ResponseData;
import com.aui.service.AccountService;
import com.aui.service.AuthenticationService;
import com.aui.service.ForgotPasswordService;


@Controller
@RequestMapping(value="/account")
public class AccountController {

@Autowired
AccountService accountService;

@Autowired
AuthenticationService authenticationService;

@Autowired
ForgotPasswordService forgotPasswordService;

@RequestMapping(value="/userQuestions", method = RequestMethod.GET, produces="application/json")
public @ResponseBody ResponseData getUserQusetion(){
	String userName=authenticationService.getAuthenticatedUserName();
	return accountService.existingUserQuestionAnswer(userName);
}

@RequestMapping(value="/validateAuthentication", method = RequestMethod.POST, produces="application/json", consumes="application/json")
public @ResponseBody ResponseData validatePassword(@RequestBody Authentication auth){
	String userName=authenticationService.getAuthenticatedUserName();
	auth.setUsername(userName);
	return accountService.validatePassword(auth);
}

@RequestMapping(value="/updateSecurityQuestions", method = RequestMethod.POST, produces="application/json", consumes="application/json")
public @ResponseBody ResponseData updateSecurityQuestions(@RequestBody Authentication authentication){
	String userName=authenticationService.getAuthenticatedUserName();
	return accountService.updateSecurityQuestions(authentication, userName);
}

}
