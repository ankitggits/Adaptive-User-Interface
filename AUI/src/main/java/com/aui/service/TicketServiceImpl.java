package com.aui.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aui.dao.TicketDao;
import com.aui.model.TBLTicket;
import com.aui.pojo.BookedTicket;
import com.aui.pojo.ResponseData;
import com.aui.transform.TransformService;
import com.aui.util.Constants;

public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketDao ticketDao;
	
	@Autowired
	TransformService transformService;
	
	@Autowired
	ApplicationContext context;



	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData getBookedTickets(String userName) {
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			List<TBLTicket> tblTickets = ticketDao.getTicketsByUsername(userName);
			
			if(null!=tblTickets && tblTickets.size()>0){
				List<BookedTicket> bookedTicketList= new ArrayList<BookedTicket>();
				for(TBLTicket ticket: tblTickets){
					 BookedTicket bookedTicket=transformService.transformTBLBookedTicket(ticket);
					 bookedTicketList.add(bookedTicket);
				}
				responseData.setData(bookedTicketList);
				responseData.setStatus(Constants.STATUS_SUCCESS);
			}
			else
			{	
				responseData.setMessage("No booked Tickets");
				responseData.setStatus(Constants.STATUS_FAILURE);
			}
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}



	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData getUpcomingTravelTickets(String userName) {
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			List<TBLTicket> tblTickets = ticketDao.getTicketsByUsername(userName);
			
			if(null!=tblTickets && tblTickets.size()>0){
				List<BookedTicket> bookedTicketList= new ArrayList<BookedTicket>();
				Date todayDate = new Date();
				for(TBLTicket ticket: tblTickets){
					BookedTicket bookedTicket=transformService.transformTBLBookedTicket(ticket);
					if(bookedTicket.getJourneyDate().after(todayDate)){
						bookedTicketList.add(bookedTicket);
					}else if(bookedTicket.getJourneyDate().equals(todayDate)){
						bookedTicketList.add(bookedTicket);
					}
				}
				responseData.setData(bookedTicketList);
				responseData.setStatus(Constants.STATUS_SUCCESS);
			}
			else{
				responseData.setMessage("No booked Tickets");
				responseData.setStatus(Constants.STATUS_FAILURE);
			}
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}
}
