package com.aui.framework.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.aui.framework.config.FrameworkConstants;
import com.aui.framework.dao.ActivityDao;
import com.aui.framework.model.TBLActivity;
import com.aui.pojo.ResponseData;
import com.aui.util.Constants;

public class ActivityServiceImpl implements ActivityService{

	@Autowired
	ApplicationContext context;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Value("${threshold.frequency.low}")
	private long thresholdFrequencyForLow;
	
	@Value("${threshold.frequency.high}")
	private long thresholdFrequencyForHigh;
	
	@Value("${threshold.lastTransaction}") 
	private int thresholdLastTransactionDate;
	
	@Override
	public long getUserExperienceRating(String userName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResponseData saveUserExperienceRating(String userName, long rating) {
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			if(userName==null || userName==""){
				responseData.setMessage("Login Required");
				responseData.setStatus(Constants.STATUS_FAILURE);
				return responseData;
			}
			TBLActivity tblActivity = activityDao.retrieveActivityByUserName(userName);
			tblActivity.setUserExperienceRating(rating);
			activityDao.logActivity(tblActivity);
			responseData.setStatus(Constants.STATUS_SUCCESS);
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}
	
	@Override
	public ResponseData getUserLevel(String userName) {
		
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			if(userName==null || userName==""){
				responseData.setMessage("Login Required");
				responseData.setStatus(Constants.STATUS_FAILURE);
				return responseData;
			}
			TBLActivity tblActivity = null;
			tblActivity = activityDao.retrieveActivityByUserName(userName);
			String userLevel = evaluateUserLevel(tblActivity);
			responseData.setUserLevelIndicator(userLevel);
			if(tblActivity.getUserLevel()!=null && !tblActivity.getUserLevel().equals(userLevel)){
				responseData.setUserLevelChanged(true);
				tblActivity.setUserLevel(userLevel);
				activityDao.logActivity(tblActivity);
			}
			responseData.setStatus(Constants.STATUS_SUCCESS);
		}catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
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
