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
import com.aui.framework.aspect.MaintainTransactionLog;
import com.aui.model.TBLAuthentication;
import com.aui.model.TBLUserQuesAns;
import com.aui.pojo.Authentication;
import com.aui.pojo.ResponseData;
import com.aui.pojo.UserQuesAns;
import com.aui.transform.TransformService;
import com.aui.util.Constants;

public class AccountServiceImpl implements AccountService{

	@Autowired
	ApplicationContext context;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	AuthenticationDao authenticationDao;
	
	@Autowired
	TransformService transformService;
	
	@Autowired
	SecurityQuesDao securityQuesDao;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData existingUserQuestionAnswer(String username) {
		
		ResponseData responseData = null;
		List<TBLUserQuesAns> tblUserQuesAnsList = null;
		List<TBLUserQuesAns> userQuesAns = null;
		try{
			responseData = context.getBean(ResponseData.class);
			TBLAuthentication tblAuthentication = authenticationDao.getAuthenticationByUserName(username);
			if(tblAuthentication==null){	
				responseData.setMessage("UserName not Available");
				responseData.setStatus(Constants.STATUS_FAILURE);
			}else{
				tblUserQuesAnsList = tblAuthentication.getTblUserQuesAns();
				
				if(tblUserQuesAnsList!= null && tblUserQuesAnsList.size()>0){
					TBLUserQuesAns localUserQuesAns=null;
					userQuesAns= new ArrayList<TBLUserQuesAns>();
					for(TBLUserQuesAns tblUserQuesAns: tblUserQuesAnsList)
					{	
						localUserQuesAns= new TBLUserQuesAns();
						localUserQuesAns.setQuestionId(tblUserQuesAns.getQuestionId());
						localUserQuesAns.setAnswer(tblUserQuesAns.getAnswer());
						userQuesAns.add(localUserQuesAns);
					}
				}else{
					responseData.setMessage("Security Questions  not Available");
					responseData.setStatus(Constants.STATUS_FAILURE);
				}
				
				if(userQuesAns!=null  && userQuesAns.size()>0 ){
					responseData.setData(userQuesAns);
					responseData.setStatus(Constants.STATUS_SUCCESS);
				}
			}
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData validatePassword(Authentication authentication) {
		
		ResponseData responseData = null;
		try{
			responseData = context.getBean(ResponseData.class);
			TBLAuthentication tblAuthentication = authenticationDao.getAuthenticationByUserName(authentication.getUsername());
			if(null != tblAuthentication){
				if(tblAuthentication.getPassword().equals(authentication.getPassword())){
					responseData.setStatus(Constants.STATUS_SUCCESS);
				}
				else{
					responseData.setStatus(Constants.STATUS_FAILURE);
				}
			}
			else{
				responseData.setStatus(Constants.STATUS_FAILURE);
				responseData.setMessage("UserName does not exist");
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
	public ResponseData updateSecurityQuestions(Authentication authentication, String userName) {
		ResponseData responseData=null;
		try{
			
			responseData = context.getBean(ResponseData.class);
			
			if(null != userName){
				
				TBLAuthentication tblAuthentication= authenticationDao.getAuthenticationByUserName(userName);
				List<TBLUserQuesAns> tblUserQuesAnsList = tblAuthentication.getTblUserQuesAns();
				List<UserQuesAns> userQuesAnsList= authentication.getUserQuesAns();
				
				int count=0;
				for(TBLUserQuesAns tblUserQuesAns: tblUserQuesAnsList ){
					
					tblUserQuesAns.setAnswer(userQuesAnsList.get(count).getAnswer());
					tblUserQuesAns.setQuestionId(userQuesAnsList.get(count).getQuestionId());
					tblUserQuesAns.postDateModification();
					count++;
				}
				
				tblAuthentication.setTblUserQuesAns(tblUserQuesAnsList);
				authenticationDao.saveOrUpdateAuthentication(tblAuthentication);
				responseData.setStatus(Constants.STATUS_SUCCESS);
				responseData.setMessage("Security Questions successfully Updated");
			}
			else{
				responseData.setStatus(Constants.STATUS_FAILURE);
				responseData.setMessage("Login Required!!");
			}
			
		}catch(Exception exception){
			responseData.setStatus(Constants.STATUS_ERROR);
			responseData.setMessage(exception.getMessage());
		}
		
		return responseData;
	}
}

