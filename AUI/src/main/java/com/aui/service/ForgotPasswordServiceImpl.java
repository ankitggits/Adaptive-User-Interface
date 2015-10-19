package com.aui.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aui.dao.AuthenticationDao;
import com.aui.dao.SecurityQuesDao;
import com.aui.dao.UserDao;
import com.aui.dao.UserSecurityQuesAnsDao;
import com.aui.framework.aspect.MaintainTransactionLog;
import com.aui.model.TBLAuthentication;
import com.aui.model.TBLSecurityQuestion;
import com.aui.model.TBLUserQuesAns;
import com.aui.pojo.Authentication;
import com.aui.pojo.ResponseData;
import com.aui.pojo.SecurityQuestion;
import com.aui.pojo.UserQuesAns;
import com.aui.transform.TransformService;
import com.aui.util.Constants;


public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	AuthenticationDao authenticationDao;
	
	@Autowired
	TransformService transformService;
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	UserSecurityQuesAnsDao userSecurityQuesAnsDao;
	
	@Autowired
	SecurityQuesDao securityQuesDao;
	
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData existingUserQuestions(String username) {
		
		ResponseData responseData = null;
		List<TBLUserQuesAns> securityQuesIdList = null;
		List<TBLSecurityQuestion> securityQuesList = null;
		try{
			responseData = context.getBean(ResponseData.class);
			TBLAuthentication tblAuthentication = authenticationDao.getAuthenticationByUserName(username);
			if(tblAuthentication==null){	
				responseData.setMessage("UserName not Available");
				responseData.setStatus(Constants.STATUS_FAILURE);
			}else{
				securityQuesIdList = tblAuthentication.getTblUserQuesAns();
				if(securityQuesIdList!= null && securityQuesIdList.size()>0){
					securityQuesList= new ArrayList<TBLSecurityQuestion>();
					for(TBLUserQuesAns tblUserQuesAns: securityQuesIdList)
					{	
						TBLSecurityQuestion tblSecurityQuestion=securityQuesDao.retriveSecurityQuesById(tblUserQuesAns.getQuestionId());
						if(tblSecurityQuestion!=null) securityQuesList.add(tblSecurityQuestion);
					}
				}else{
					responseData.setMessage("Security Questions  not Available");
					responseData.setStatus(Constants.STATUS_FAILURE);
				}
				SecurityQuestion secQues = null;
				List<SecurityQuestion> userQuestion = new ArrayList<SecurityQuestion>();
				for(TBLSecurityQuestion tblSecurityQuestion:securityQuesList){
					secQues = transformService.transformTBLSecurityQuestion(tblSecurityQuestion);
					userQuestion.add(secQues);
				}
				responseData.setData(userQuestion);
				responseData.setStatus(Constants.STATUS_SUCCESS);
			}
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}



	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData validateSecurityQuesAns(Authentication authentication) {
		ResponseData responseData = null;
		try{
			boolean flag= false;
			responseData = context.getBean(ResponseData.class);
			TBLAuthentication tblAuthentication= authenticationDao.getAuthenticationByUserName(authentication.getUsername());
			if(tblAuthentication.getTblUserQuesAns() != null && tblAuthentication.getTblUserQuesAns().size()>0){
				
				z:for(UserQuesAns userQuesAns: authentication.getUserQuesAns()){
					for(TBLUserQuesAns tblUserQuesAns: tblAuthentication.getTblUserQuesAns()){
						if(userQuesAns.getQuestionId().equals(tblUserQuesAns.getQuestionId()) ){
							if(userQuesAns.getAnswer().equalsIgnoreCase(tblUserQuesAns.getAnswer())){
								flag=true;
								continue z;
							}
							else{
								flag=false;
								break z;
							}
						}
					}
				}
			}
				
			if(flag)
			{
				responseData.setStatus(Constants.STATUS_SUCCESS);
			}
			else
			{
				responseData.setMessage("Retry Count");
				responseData.setStatus(Constants.STATUS_FAILURE);
			}
			
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		
		return responseData;
	}



	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData resetPassword(Authentication authentication) {
		ResponseData responseData=null;
		try{
			responseData = context.getBean(ResponseData.class);
			TBLAuthentication tblAuthentication = authenticationDao.getAuthenticationByUserName(authentication.getUsername());
			if(null != tblAuthentication){
				tblAuthentication.setPassword(authentication.getPassword());
				authenticationDao.saveOrUpdateAuthentication(tblAuthentication);
				responseData.setStatus(Constants.STATUS_SUCCESS);
				responseData.setMessage("Password Changes Successfully!!!");
			}
			else{
				responseData.setMessage("Login Required");
				responseData.setStatus(Constants.STATUS_FAILURE);
			}
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setMessage(Constants.STATUS_ERROR);
		}
		return responseData;
	}

	@Override
	@MaintainTransactionLog
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData updatePassword(Authentication authentication) {
		ResponseData responseData = null;
		try{
			responseData = context.getBean(ResponseData.class);
			TBLAuthentication tblAuthentication = authenticationDao.getAuthenticationByUserName(authentication.getUsername());
			if(null != tblAuthentication){
				if(tblAuthentication.getPassword().equals(authentication.getOldPassword())){
					tblAuthentication.setPassword(authentication.getPassword());
					authenticationDao.saveOrUpdateAuthentication(tblAuthentication);
					responseData.setStatus(Constants.STATUS_SUCCESS);
				}
				else{
					responseData.setMessage("Old Password does not match");
					responseData.setStatus(Constants.STATUS_FAILURE);
				}
			}
			else{
				responseData.setMessage("Login Required");
				responseData.setStatus(Constants.STATUS_FAILURE);
			}
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setMessage(Constants.STATUS_ERROR);
		}
		return responseData;
	}
	
}

