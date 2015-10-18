package com.aui.framwork.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;

import com.aui.dao.UserDao;
import com.aui.framework.service.ActivityService;
import com.aui.pojo.Flight;
import com.aui.pojo.SecurityQuestion;
import com.aui.service.FlightService;
import com.aui.service.RegistrationService;
import com.aui.transform.ResourcePopulate;

@PropertySource("classpath:config/database/properties/database.properties")
public class ActivityListener implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	ActivityService activityService;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	FlightService flightService;
	
	@Autowired
	RegistrationService registrationService;
	
	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernateHbm2ddlAuto;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
				ApplicationContext applicationContext = event.getApplicationContext();
		if(applicationContext.getParent() != null){
			// TODO Auto-generated method stub
			if(hibernateHbm2ddlAuto.equals("create")){
				userDao.truncateTable("TBLFlight");
				List<Flight> flights = ResourcePopulate.getFlightsFromXML();
				flightService.saveFlights(flights);
				userDao.truncateTable("TBLSecurityQuestion");
				List<SecurityQuestion> questions = ResourcePopulate.getQuestionsFromXML();
				registrationService.saveSecurityQuestions(questions);
			}
		}
	}

}