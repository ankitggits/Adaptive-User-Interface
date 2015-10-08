package com.aui.model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

@Entity
@Table(name="TICKET")
public class TBLBookedTicket extends TBLRoot{

	
	
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
	
	@Column(name="TICKET_CANCELLED")
	private boolean ticketCancelled;
	
	

	

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

	public boolean isTicketCancelled() {
		return ticketCancelled;
	}

	public void setTicketCancelled(boolean ticketCancelled) {
		this.ticketCancelled = ticketCancelled;
	}

	
	
	
	
	
	
	
	
	
	
	
}
