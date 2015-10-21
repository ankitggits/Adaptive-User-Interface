package com.aui.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.aui.framework.aspect.ActivityAspect;
import com.aui.framework.dao.ActivityDao;
import com.aui.framework.dao.ActivityDaoImpl;
import com.aui.framework.service.ActivityService;
import com.aui.framework.service.ActivityServiceImpl;
import com.aui.framwork.listener.ActivityListener;

@Configuration
public class FrameworkConfig {

	@Autowired
	LocalSessionFactoryBean sessionFactory;
	
	/***************Aspects**************/
	@Bean
	public ActivityAspect activityAspect(){
		return new ActivityAspect();
	}
	
	
	/***************Repository**************/
	@Bean
	public ActivityDao activityDao(){
		
		ActivityDaoImpl dao = new ActivityDaoImpl();
		dao.setSessionFactory(sessionFactory.getObject());
		return dao;
	}
	
	/***************Listener**************/
	@Bean
	public ActivityListener activityListener(){
		return new ActivityListener();
	}
	
	/***************Service**************/
	@Bean
	public ActivityService activityService(){
		return new ActivityServiceImpl();
	}
}
