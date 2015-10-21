package com.aui.service;

import com.aui.pojo.ResponseData;

public interface TicketService {

	
	ResponseData getBookedTickets(String userName);
	
	ResponseData getUpcomingTravelTickets(String userName);
	
	
}
