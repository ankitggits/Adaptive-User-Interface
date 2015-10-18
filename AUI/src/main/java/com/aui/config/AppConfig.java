package com.aui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import com.aui.framework.config.FrameworkConfig;
import com.aui.pojo.ResponseData;
import com.aui.service.AccountService;
import com.aui.service.AccountServiceImpl;
import com.aui.service.AuthenticationService;
import com.aui.service.AuthenticationServiceImpl;
import com.aui.service.FlightService;
import com.aui.service.FlightServiceImpl;
import com.aui.service.ForgotPasswordService;
import com.aui.service.ForgotPasswordServiceImpl;
import com.aui.service.ProfileService;
import com.aui.service.ProfileServiceImpl;
import com.aui.service.RegistrationService;
import com.aui.service.RegistrationServiceImpl;


@Configuration
@ComponentScan(basePackages = { "com.aui" })
@ImportResource(value="classpath:config/spring/AUI-security.xml")
@Import({RepositoryConfig.class,FrameworkConfig.class})
@EnableAspectJAutoProxy
//@Import({ MvcConfig.class, RepositoryConfig.class, SecurityConfiguration.class})
public class AppConfig {

	@Bean
	public SecurityContextRepository securityContextRepository() {
	    return new HttpSessionSecurityContextRepository();
	}
	
	@Bean
	@Scope(value="prototype")
	public ResponseData responseData(){
		ResponseData responseData = new ResponseData();
		return responseData;
	}
	
	/*-------SERVICES-------*/
	@Bean
	public ProfileService profileService(){
		return new ProfileServiceImpl();
	}
	
	@Bean
	public AuthenticationService authenticationService(){
		return new AuthenticationServiceImpl();
	}
	
	@Bean
	public RegistrationService registrationService(){
		return new RegistrationServiceImpl();
	}
	
	@Bean
	public FlightService flightService(){
		return new FlightServiceImpl();
	}
	
	@Bean
	public ForgotPasswordService forgotPasswordService(){
		return new ForgotPasswordServiceImpl();
	}
	
	@Bean
	public AccountService accountService(){
		return new AccountServiceImpl();
	}
	
}
