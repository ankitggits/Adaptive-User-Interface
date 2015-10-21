package com.aui.framework.service;

import com.aui.pojo.ResponseData;

public interface ActivityService {

	long getUserExperienceRating(String userName);
	
	ResponseData saveUserExperienceRating(String userName,long rating);
	
	ResponseData getUserLevel(String userName);
}
