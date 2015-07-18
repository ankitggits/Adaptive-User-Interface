package com.aui.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.aui.framework.aspect.ActivityAspect;
import com.aui.framework.aspect.UserExperienceLevelAspect;
import com.aui.framework.dao.ActivityDao;
import com.aui.framework.dao.ActivityDaoImpl;

@Configuration
public class FrameworkConfig {

	@Autowired
	LocalSessionFactoryBean sessionFactory;
	
	/***************Aspects**************/
	@Bean
	public ActivityAspect activityAspect(){
		return new ActivityAspect();
	}
	
	@Bean
	public UserExperienceLevelAspect userExperienceLevelAspect(){
		return new UserExperienceLevelAspect();
	}
	
	
	/***************Repository**************/
	@Bean
	public ActivityDao activityDao(){
		
		ActivityDaoImpl dao = new ActivityDaoImpl();
		dao.setSessionFactory(sessionFactory.getObject());
		return dao;
	}
}
