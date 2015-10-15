package com.aui.framwork.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.aui.framework.service.ActivityService;
import com.aui.pojo.ResponseData;

public class ActivityListener implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	ActivityService activityService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		ApplicationContext applicationContext = event.getApplicationContext();
		if(applicationContext.getParent() != null){
			ResponseData data = applicationContext.getBean(ResponseData.class);
			System.out.println(data.getMessage());
		}
	}

		/*@Override
		public void contextDestroyed(ServletContextEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void contextInitialized(ServletContextEvent event) {
			ServletContext context = event.getServletContext();
			ResponseData data = context.getBean(ResponseData.class);
			System.out.println(data.getMessage());
		}*/

	}