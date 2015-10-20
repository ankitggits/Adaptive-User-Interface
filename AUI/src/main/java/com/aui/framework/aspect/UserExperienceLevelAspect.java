package com.aui.framework.aspect;

import java.util.Calendar;
import java.util.Date;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.aui.framework.config.FrameworkConstants;
import com.aui.framework.dao.ActivityDao;
import com.aui.framework.model.TBLActivity;
import com.aui.pojo.ResponseData;
import com.aui.util.Constants;

@Aspect 
public class UserExperienceLevelAspect {

	@Value("${threshold.frequency.low}")
	private long thresholdFrequencyForLow;
	
	@Value("${threshold.frequency.high}")
	private long thresholdFrequencyForHigh;
	
	@Value("${threshold.lastTransaction}") 
	private int thresholdLastTransactionDate;
	
	@Autowired
	private ActivityDao activityDao;
	
	@AfterReturning(pointcut="profilePointcut()", returning="returnedResponse")
	public void maintainLoginInfo(ResponseData returnedResponse){
		TBLActivity tblActivity = null;
		if(returnedResponse!=null && returnedResponse.getStatus()!=null && returnedResponse.getStatus().equals(Constants.STATUS_SUCCESS)){
			String userName = (String)returnedResponse.getData();
			tblActivity = activityDao.retrieveActivityByUserName(userName);
			String userLevel = evaluateUserLevel(tblActivity);
			returnedResponse.setUserLevelIndicator(evaluateUserLevel(tblActivity));
			if(tblActivity.getUserLevel()!=null && !tblActivity.getUserLevel().equals(userLevel)){
				returnedResponse.setUserLevelChanged(true);
				tblActivity.setUserLevel(userLevel);
				activityDao.logActivity(tblActivity);
			}
		}
	}
	
	@Pointcut("execution(* com.aui.service.ProfileService.getUserLevel(..))")
	public void profilePointcut(){
	}
	
	private String evaluateUserLevel(TBLActivity tblActivity){
		String userLevel = FrameworkConstants.USER_LEVEL_MODERATE;
		if(tblActivity!=null){
			if(tblActivity.getLevelFactor()<= thresholdFrequencyForLow){
				userLevel = FrameworkConstants.USER_LEVEL_INITIAL;
			}else if(tblActivity.getLevelFactor()>=thresholdFrequencyForHigh){
				Date lastSuccessfulTransDate = tblActivity.getLastSuccessfullTransaction();
				if(tblActivity.getLastSuccessfullTransaction()!=null && calculateThresholdDate().before(dateToCalendar(lastSuccessfulTransDate)) && tblActivity.hasEverBooked()){
					userLevel = FrameworkConstants.USER_LEVEL_EXPERT;
				}else{
					tblActivity.setLevelFactor(tblActivity.getLevelFactor()-1);
					tblActivity.postDateModification();
					activityDao.logActivity(tblActivity);
					userLevel = evaluateUserLevel(tblActivity);
				}
			}else{
				Date lastSuccessfulTransDate = tblActivity.getLastSuccessfullTransaction();
				if(tblActivity.getLastSuccessfullTransaction()!=null && calculateThresholdDate().before(dateToCalendar(lastSuccessfulTransDate))){
					userLevel = FrameworkConstants.USER_LEVEL_MODERATE;
				}else{
					tblActivity.setLevelFactor(tblActivity.getLevelFactor()-1);
					tblActivity.postDateModification();
					activityDao.logActivity(tblActivity);
					userLevel = evaluateUserLevel(tblActivity);
				}
			}
		}
		return userLevel;
	}
	
	private Calendar dateToCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
	}
	
	private Calendar calculateThresholdDate(){
		Calendar thresholdLastDate = Calendar.getInstance();
		thresholdLastDate.add(Calendar.DATE, thresholdLastTransactionDate);
		return thresholdLastDate;
	}
}
