package com.aui.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aui.dao.SecurityQuesDao;
import com.aui.dao.UserDao;
import com.aui.model.TBLSecurityQuestion;
import com.aui.model.TBLUser;
import com.aui.model.TBLUserQuesAns;
import com.aui.pojo.ResponseData;
import com.aui.pojo.SecurityQuestion;
import com.aui.pojo.User;
import com.aui.security.AutoLogin;
import com.aui.transform.TransformService;
import com.aui.util.Constants;

public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	SecurityQuesDao securityQuesDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	TransformService transformService;
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	AutoLogin autoLogin;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData doRegistration(User user, HttpServletRequest request, HttpServletResponse response) {
		
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			TBLUser tblUser = transformService.transformUser(user);
			//setting create and update date-timings
			tblUser.predateModification();
			tblUser.getTBLAddress().predateModification();
			tblUser.getTblAuthentication().predateModification();
			if(tblUser.getTblAuthentication().getTblUserQuesAns()!=null){
				for(TBLUserQuesAns tblUserQuesAns : tblUser.getTblAuthentication().getTblUserQuesAns()){
					tblUserQuesAns.predateModification();
				}
			}
			userDao.doRegister(tblUser);
			autoLogin.doAutoLogin(user.getAuthentication(), request, response);
			responseData.setStatus(Constants.STATUS_SUCCESS);
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData retriveAllSecurityQues() {
		
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			
			List<TBLSecurityQuestion> secQues= securityQuesDao.retriveSecurityQues();
			
			Map<String, List<String>> secQuesMap= null;
			
			if(secQues!=null && secQues.size()>0){
				secQuesMap= new HashMap<String, List<String>>(); 
				List<String> questions = null;
				for(TBLSecurityQuestion tsq:secQues)
				{
					if(secQuesMap.containsKey(tsq.getGroup())){
						questions = secQuesMap.get(tsq.getGroup());
					}else{
						questions = new ArrayList<String>();
					}
					questions.add(tsq.getId()+"~"+tsq.getQuestion());
					secQuesMap.put(tsq.getGroup(), questions);
				}
				responseData.setData(secQuesMap);
				responseData.setStatus(Constants.STATUS_SUCCESS);
			}
			else
			{
				responseData.setMessage("Security Questions not Available");
				responseData.setStatus(Constants.STATUS_FAILURE);
			}
		}catch(Exception e){
			responseData.setErrorMessage(e.toString());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}

	@Override
	public ResponseData retriveSecurityQuesByGroup(String group) {
		return null;
	}

	@Override
	public ResponseData retriveSecurityQuesAnsByUserName(String userName) {
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData saveSecurityQuestions(List<SecurityQuestion> securityQuestions) {
		ResponseData responseData = null;
		try{
			responseData = context.getBean(ResponseData.class);
			if(securityQuestions!=null && securityQuestions.size()>0){
				TBLSecurityQuestion tblSecurityQuestion = null;
				for(SecurityQuestion question:securityQuestions){
					tblSecurityQuestion = transformService.transformSecurityQuestion(question);
					tblSecurityQuestion.predateModification();
					securityQuesDao.populateSecurityQues(tblSecurityQuestion);
				}
				responseData.setStatus(Constants.STATUS_SUCCESS);
			}else{
				responseData.setMessage("Security Questions missing!!!");
				responseData.setStatus(Constants.STATUS_FAILURE);
			}
			
		}catch(Exception e){
			responseData.setErrorMessage(e.toString());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}

}
