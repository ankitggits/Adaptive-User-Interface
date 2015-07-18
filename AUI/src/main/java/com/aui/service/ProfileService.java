package com.aui.service;

import com.aui.pojo.ResponseData;
import com.aui.pojo.User;

public interface ProfileService {

	ResponseData isUsernameAvailable(String username);
	
	ResponseData getUserDetails(String userName);
	
	ResponseData updateProfile(User user, String userName);

	ResponseData getUserLevel(String userName);
}
