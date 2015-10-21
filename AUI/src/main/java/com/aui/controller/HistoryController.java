package com.aui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aui.pojo.ResponseData;
import com.aui.service.AuthenticationService;
import com.aui.service.TicketService;

@Controller
@RequestMapping(value="/ticket")
public class HistoryController {

	@Autowired
	TicketService ticketService;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@RequestMapping(value="/getBooking", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseData getBookedTickets(){
		String userName=authenticationService.getAuthenticatedUserName();
		return ticketService.getBookedTickets(userName);
	}
	
	@RequestMapping(value="/getUpcomingBooking", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseData getUpcomingTickets(){
		String userName=authenticationService.getAuthenticatedUserName();
		return ticketService.getUpcomingTravelTickets(userName);
	}
}
