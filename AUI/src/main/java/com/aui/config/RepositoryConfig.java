/**
 * 
 */
package com.aui.config;

import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.aui.dao.AuthenticationDao;
import com.aui.dao.AuthenticationDaoImpl;
import com.aui.dao.FlightDao;
import com.aui.dao.FlightDaoImpl;
import com.aui.dao.SecurityQuesDao;
import com.aui.dao.SecurityQuesDaoImpl;
import com.aui.dao.TicketDao;
import com.aui.dao.TicketDaoImpl;
import com.aui.dao.UserDao;
import com.aui.dao.UserDaoImpl;
import com.aui.dao.UserSecurityQuesAnsDao;
import com.aui.dao.UserSecurityQuesAnsDaoImpl;

/**
 * @author ANKIT
 *
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:config/database/properties/database.properties")
public class RepositoryConfig {

	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	@Value("${jdbc.url}")
	private String url;
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	private String pwd;

	@Value("${hibernate.dialect}")
	private String hibernateDialect;
	@Value("${hibernate.show_sql}")
	private String hibernateShowSql;
	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernateHbm2ddlAuto;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	BasicDataSource dataSource() {

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(driverClassName);
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(pwd);
		return basicDataSource;
	}
	
	@Bean
	public Properties hibernateProperties() {

		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", hibernateDialect);
		properties.setProperty("hibernate.show_sql", hibernateShowSql);
		properties.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
		return properties;
	}

	@Bean
	public HibernateTransactionManager txmanager() {

		HibernateTransactionManager txmanager = new HibernateTransactionManager();
		txmanager.setSessionFactory(sessionFactory().getObject());
		return txmanager;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {

		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(dataSource());
		localSessionFactoryBean.setHibernateProperties(hibernateProperties());
		localSessionFactoryBean.setPackagesToScan("com.aui.*");
		return localSessionFactoryBean;
	}
	
	
	@Bean
	public AuthenticationDao authenticationDao() {

		AuthenticationDaoImpl dao = new AuthenticationDaoImpl();
		dao.setSessionFactory(sessionFactory().getObject());
		return dao;
	}
	
	@Bean
	public TicketDao ticketDao() {

		TicketDaoImpl dao = new TicketDaoImpl();
		dao.setSessionFactory(sessionFactory().getObject());
		return dao;
	}
	
	@Bean
	public UserDao UserDao() {

		UserDaoImpl dao = new UserDaoImpl();
		dao.setSessionFactory(sessionFactory().getObject());
		return dao;
	}
	
	@Bean
	public SecurityQuesDao  securityQuesDao() {

		SecurityQuesDaoImpl dao = new SecurityQuesDaoImpl();
		dao.setSessionFactory(sessionFactory().getObject());
		return dao;
	}
	
	@Bean
	public FlightDao  flightDao() {

		FlightDaoImpl dao = new FlightDaoImpl();
		dao.setSessionFactory(sessionFactory().getObject());
		return dao;
	}
	
	@Bean
	public UserSecurityQuesAnsDao userSecurityQuesAnsDao() {

		UserSecurityQuesAnsDaoImpl dao = new UserSecurityQuesAnsDaoImpl();
		dao.setSessionFactory(sessionFactory().getObject());
		return dao;
	}
	
}
