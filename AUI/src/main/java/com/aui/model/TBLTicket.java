package com.aui.model;


import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="TICKET")
public class TBLTicket extends TBLRoot implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="USER_NAME")	
	private String userName;
	
	@Column(name="FLIGHT_CODE")
	private String flightCode;
	
	@Column(name="SOURCE")
	private String source;
	
	@Column(name="DESTINATION")
	private String destination;
	
	@Column(name="PASSENGERS")	
	private int passengers;
	
	@Column(name="JOURNEY_DATE")
	@Type(type = "date")
	private Date journeyDate;
	
	@Column(name="BASE_FARE")
	private long baseFare;
	
	@Column(name="TOTAL_FARE")
	private long totalFare;
	
	@Column(name="PASSENGER_SERVICE_FEES")
	private long passServiceFees;
	
	@Column(name="GOVN_SERVICE_FEES")
	private long govnServiceFees;
	

	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public Date getJourneyDate() {
		return journeyDate;
	}

	public void setJourneyDate(Date journeyDate) {
		this.journeyDate = journeyDate;
	}

	public long getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(long baseFare) {
		this.baseFare = baseFare;
	}

	public long getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(long totalFare) {
		this.totalFare = totalFare;
	}

	public long getPassServiceFees() {
		return passServiceFees;
	}

	public void setPassServiceFees(long passServiceFees) {
		this.passServiceFees = passServiceFees;
	}

	public long getGovnServiceFees() {
		return govnServiceFees;
	}

	public void setGovnServiceFees(long govnServiceFees) {
		this.govnServiceFees = govnServiceFees;
	}


	
	
}
