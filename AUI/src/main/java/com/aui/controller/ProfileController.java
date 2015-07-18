package com.aui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aui.pojo.Authentication;
import com.aui.pojo.ResponseData;
import com.aui.pojo.User;
import com.aui.service.AuthenticationService;
import com.aui.service.ProfileService;

@Controller
@RequestMapping(value="/profile")
public class ProfileController {
	
	@Autowired
	ProfileService profileService;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@RequestMapping(value="/usernameAvailability", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public @ResponseBody ResponseData isUsernameAvailable(@RequestBody Authentication auth){
		return profileService.isUsernameAvailable(auth.getUsername());
	}
	
	@RequestMapping(value="/details", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseData getUserDetails(){
		String userName = authenticationService.getAuthenticatedUserName();
		return profileService.getUserDetails(userName);
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public @ResponseBody ResponseData updateProfile(@RequestBody User user){
		String userName = authenticationService.getAuthenticatedUserName();
		return profileService.updateProfile(user, userName);
	}
}
