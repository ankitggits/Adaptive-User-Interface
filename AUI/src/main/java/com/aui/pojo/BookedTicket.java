package com.aui.pojo;

import java.util.Date;



public class BookedTicket {

	private String userName;
	private String flightCode;
	private String source;
	private String destination;
	private int passengers;
	private Date journeyDate;
	private long baseFare;
	private long totalFare;
	private long passServiceFees;
	private long govnServiceFees;
	private String createdOn;
	
	public String getFlightCode() {
		return flightCode;
	}
	public void setFlightCode(String flightCode) {
		this.flightCode	 = flightCode;
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
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	
	
	
}
