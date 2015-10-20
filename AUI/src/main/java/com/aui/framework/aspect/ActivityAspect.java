package com.aui.framework.aspect;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.aui.framework.config.FrameworkConstants;
import com.aui.framework.dao.ActivityDao;
import com.aui.framework.model.TBLActivity;
import com.aui.pojo.ResponseData;
import com.aui.service.AuthenticationService;
import com.aui.util.Constants;

@Aspect 
public class ActivityAspect {
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@AfterReturning(pointcut="loginPointcut()", returning="isAuthenticated")
	public void maintainLoginInfo(boolean isAuthenticated){
		
		TBLActivity tblActivity = null;
		
		if(isAuthenticated){
			String userName = authenticationService.getAuthenticatedUserName();
			tblActivity = activityDao.retrieveActivityByUserName(userName);
			if(tblActivity==null){
				tblActivity = new TBLActivity();
				tblActivity.setUserName(userName);
				tblActivity.setLoginFrequency(1);
				tblActivity.setLevelFactor(1);
				tblActivity.setUserLevel(FrameworkConstants.USER_LEVEL_INITIAL);
				tblActivity.predateModification();
			}else{
				tblActivity.setLoginFrequency(tblActivity.getLoginFrequency()+1);
				tblActivity.setLevelFactor(tblActivity.getLevelFactor()+1);
				tblActivity.postDateModification();
			}
			activityDao.logActivity(tblActivity);
		}
	}
	
	@AfterReturning(pointcut="maintainTransactionLog()", returning="responseData")
	public void maintainTransactionInfo(JoinPoint joinPoint,ResponseData responseData){
		
		TBLActivity tblActivity = null;
		if(responseData!=null && responseData.getStatus().equals(Constants.STATUS_SUCCESS)){
			String userName = authenticationService.getAuthenticatedUserName();
			tblActivity = activityDao.retrieveActivityByUserName(userName);
			if(tblActivity!=null){
				tblActivity.postDateModification();
				tblActivity.setLastSuccessfullTransaction(new Date());
				if(joinPoint.getSignature().getName().equals("bookFlight")){
					tblActivity.setEverBooked(true);
				}
			}
			activityDao.logActivity(tblActivity);
		}
	}
	
	@Pointcut("execution(* com.aui.security.AutoLogin.doAutoLogin(..))")
	public void loginPointcut(){
	}
	
	@Pointcut("@annotation(com.aui.framework.aspect.MaintainTransactionLog)")
    public void maintainTransactionLog(){
    }
	
}
