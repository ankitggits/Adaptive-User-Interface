package com.aui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aui.pojo.Authentication;
import com.aui.pojo.ResponseData;
import com.aui.service.ForgotPasswordService;





@Controller
@RequestMapping(value="/password")
public class ForgotPasswordController {

	@Autowired
	ForgotPasswordService forgotPasswordService;
	
	
	@RequestMapping(value="/forgot", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public @ResponseBody ResponseData existingUserQuestions(@RequestBody Authentication auth){
		return forgotPasswordService.existingUserQuestions(auth.getUsername());
	}
	
	@RequestMapping(value="/validateSecurityQuesAns", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public @ResponseBody ResponseData validateSecurityQuesAns(@RequestBody Authentication auth){
		return forgotPasswordService.validateSecurityQuesAns(auth);
	}
	
	@RequestMapping(value="/reset", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public @ResponseBody ResponseData resetPassword(@RequestBody Authentication auth){
		return forgotPasswordService.resetPassword(auth);
	}
	
	
	
}
