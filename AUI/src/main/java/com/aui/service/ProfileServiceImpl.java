package com.aui.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aui.dao.AuthenticationDao;
import com.aui.dao.UserDao;
import com.aui.framework.aspect.MaintainTransactionLog;
import com.aui.model.TBLUser;
import com.aui.pojo.ResponseData;
import com.aui.pojo.User;
import com.aui.transform.TransformService;
import com.aui.util.Constants;

public class ProfileServiceImpl implements ProfileService{

	@Autowired
	UserDao userDao;
	
	@Autowired
	AuthenticationDao authenticationDao;
	
	@Autowired
	TransformService transformService;
	
	@Autowired
	ApplicationContext context;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData isUsernameAvailable(String username) {
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			responseData.setStatus((authenticationDao.isUsernameExists(username)==false)?Constants.STATUS_SUCCESS:Constants.STATUS_FAILURE);
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData getUserDetails(String userName) {
		
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			if(userName==null || userName==""){
				responseData.setMessage("Login Required");
				responseData.setStatus(Constants.STATUS_FAILURE);
				return responseData;
			}
			TBLUser tblUser = userDao.getUserByUserName(userName);
			User user = transformService.transformTBLUser(tblUser);
			responseData.setData(user);
			responseData.setStatus(Constants.STATUS_SUCCESS);
			return responseData;
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
			return responseData;
		}
	}
	
	@Override
	@MaintainTransactionLog
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData updateProfile(User user, String userName) {
		ResponseData responseData= null;
		try{
			responseData= context.getBean(ResponseData.class);
			if(userName==null || userName==""){
				responseData.setMessage("Login Required");
				responseData.setStatus(Constants.STATUS_FAILURE);
				return responseData;
			}
			TBLUser tblUser= userDao.getUserByUserName(userName);
			BeanUtils.copyProperties(user, tblUser, new String[]{"authentication","address"});
			BeanUtils.copyProperties(user.getAddress(), tblUser.getTBLAddress());
			tblUser.postDateModification();
			tblUser.getTBLAddress().postDateModification();
			userDao.doRegister(tblUser);
			responseData.setMessage("Profile Updated Successfully");
			responseData.setStatus(Constants.STATUS_SUCCESS);
			return responseData;
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
			return responseData;
		}
		
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
			responseData.setData(userName);
			responseData.setStatus(Constants.STATUS_SUCCESS);
		}catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}
	
}
