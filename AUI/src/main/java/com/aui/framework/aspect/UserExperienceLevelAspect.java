package com.aui.framework.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import com.aui.framework.config.FrameworkConstants;
import com.aui.framework.dao.ActivityDao;
import com.aui.framework.model.TBLActivity;
import com.aui.pojo.ResponseData;
import com.aui.util.Constants;

@Aspect 
@PropertySource("classpath:config/properties/AUIFrameworkConfig.properties")
public class UserExperienceLevelAspect {

	@Value("${threshold.frequency.low}")
	private String thresholdFrequencyForLow = "2";
	
	@Value("${threshold.frequency.high}")
	private String thresholdFrequencyForHigh = "5";
	
	@Autowired
	private ActivityDao activityDao;
	
	@AfterReturning(pointcut="profilePointcut()", returning="returnedResponse")
	public void maintainLoginInfo(ResponseData returnedResponse){
		TBLActivity tblActivity = null;
		if(returnedResponse!=null && returnedResponse.getStatus()!=null && returnedResponse.getStatus().equals(Constants.STATUS_SUCCESS)){
			String userName = (String)returnedResponse.getData();
			tblActivity = activityDao.retrieveActivityByUserName(userName);
			returnedResponse.setUserLevelIndicator(evaluateUserLevel(tblActivity));
		}
	}
	
	@Pointcut("execution(* com.aui.service.ProfileService.getUserLevel(..))")
	public void profilePointcut(){
	}
	
	private String evaluateUserLevel(TBLActivity tblActivity){
		String userLevel = FrameworkConstants.USER_LEVEL_MODERATE;
		if(tblActivity!=null){
			if(tblActivity.getLoginFrequency()<= Long.valueOf(thresholdFrequencyForLow)){
				userLevel = FrameworkConstants.USER_LEVEL_INITIAL;
			}else if(tblActivity.getLoginFrequency()>Long.valueOf(thresholdFrequencyForHigh)){
				userLevel = FrameworkConstants.USER_LEVEL_EXPERT;
			}else{
				userLevel = FrameworkConstants.USER_LEVEL_MODERATE;
			}
		}
		return userLevel;
	}
}
