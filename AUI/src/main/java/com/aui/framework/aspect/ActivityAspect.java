package com.aui.framework.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.aui.framework.dao.ActivityDao;
import com.aui.framework.model.TBLActivity;
import com.aui.pojo.ResponseData;
import com.aui.util.Constants;

@Aspect 
public class ActivityAspect {
	
	@Autowired
	private ActivityDao activityDao;

	@AfterReturning(pointcut="loginPointcut()", returning="returnedResponse")
	public void maintainLoginInfo(ResponseData returnedResponse){
		
		TBLActivity tblActivity = null;
		if(returnedResponse!=null && returnedResponse.getStatus()!=null && returnedResponse.getStatus().equals(Constants.STATUS_SUCCESS)){
			String userName = (String) returnedResponse.getData();
			tblActivity = activityDao.retrieveActivityByUserName(userName);
			if(tblActivity==null){
				tblActivity = new TBLActivity();
				tblActivity.setUserName(userName);
				tblActivity.setLoginFrequency(1);
				tblActivity.predateModification();
			}else{
				tblActivity.setLoginFrequency(tblActivity.getLoginFrequency()+1);
				tblActivity.postDateModification();
			}
			activityDao.logActivity(tblActivity);
		}
	}
	
	@Pointcut("execution(* com.aui.service.AuthenticationService.doUserLogin(..))")
	public void loginPointcut(){
	}
	
}
