package com.aui.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aui.dao.FlightDao;
import com.aui.dao.TicketDao;
import com.aui.framework.aspect.MaintainTransactionLog;
import com.aui.model.TBLFlight;
import com.aui.model.TBLFlightLogo;
import com.aui.model.TBLTicket;
import com.aui.pojo.BookedTicket;
import com.aui.pojo.Flight;
import com.aui.pojo.FlightLogo;
import com.aui.pojo.ResponseData;
import com.aui.pojo.SearchFlight;
import com.aui.transform.TransformService;
import com.aui.util.Constants;

public class FlightServiceImpl implements FlightService {

	@Autowired
	FlightDao flightDao;
	
	@Autowired
	TicketDao ticketDao;
	
	@Autowired
	TransformService transformService;
	
	@Autowired
	ApplicationContext context;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData searchFlights(SearchFlight searchflight) {
		
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			List<TBLFlight> searchedFlightResult = flightDao.searchFlights(searchflight);
			List<Flight> searchedFlights = null;
			if(searchedFlightResult!=null && searchedFlightResult.size()>0){
				searchedFlights = new ArrayList<Flight>();
				for(TBLFlight searchedTBLFlight : searchedFlightResult){
					Flight searchedFlight = transformService.transformTBLFlight(searchedTBLFlight);
					searchedFlights.add(searchedFlight);
				}
				responseData.setData(searchedFlights);
				responseData.setStatus(Constants.STATUS_SUCCESS);
			}else{
				responseData.setMessage("No Flights Found");
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
	public ResponseData saveFlights(List<Flight> flights) {
		
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			TBLFlight tblFlight = null;
			for(Flight flight:flights){
				tblFlight = transformService.transformFlight(flight);
				tblFlight.predateModification();
				flightDao.populateFlights(tblFlight);
			}
			responseData.setStatus(Constants.STATUS_SUCCESS);
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData saveFlightLogo(List<FlightLogo> logo) {
		
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			TBLFlightLogo tblFlightLogo = null;
			for(FlightLogo flightLogo:logo){
				tblFlightLogo = transformService.transformFlightLogo(flightLogo);
				tblFlightLogo.predateModification();
				flightDao.populateFlightLogo(tblFlightLogo);
			}
			responseData.setStatus(Constants.STATUS_SUCCESS);
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}

	@Override
	@MaintainTransactionLog
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData bookFlight(BookedTicket bookedTicket) {
		
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			TBLTicket tblBookedTicket = transformService.transformBookedTicket(bookedTicket);
			tblBookedTicket.predateModification();
			flightDao.bookFlight(tblBookedTicket);
			responseData.setStatus(Constants.STATUS_SUCCESS);
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData getCities(String userName) {
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			List<Collection<String>> prioritizedCities = new ArrayList<Collection<String>>();
			
			List<TBLFlight> tblFlights = flightDao.getFlights();
			HashSet<String> otherCities = new HashSet<String>();
			for(TBLFlight tblFlight: tblFlights){
				otherCities.add(tblFlight.getSource());
				otherCities.add(tblFlight.getDestination());
			}
			
			//Calculate Fav Cities based on history
			List<TBLTicket> tblTickets = ticketDao.getTicketsByUsername(userName);
			HashSet<String> bookedCities = new HashSet<String>();
			for(TBLTicket tblTicket: tblTickets){
				bookedCities.add(tblTicket.getSource());
				bookedCities.add(tblTicket.getDestination());
			}
			
			class PriorityCity{
				String city;
				int count;
				public String getCity() {
					return city;
				}
				public void setCity(String city) {
					this.city = city;
				}
				public int getCount() {
					return count;
				}
				public void setCount(int count) {
					this.count = count;
				}
				
				@Override
				public boolean equals(Object obj) {
					PriorityCity other = (PriorityCity) obj;
					if (city.equals(other.city)) return true;
					else return false;
				}
			}
			
			
			Set<PriorityCity> priorityCities = new HashSet<PriorityCity>();
			
			for(String city:bookedCities){
				for(TBLTicket tblTicket: tblTickets){
					if(tblTicket.getSource().equals(city) || tblTicket.getDestination().equals(city)){
						boolean cityFoundInPriorityList = false;
						
						for(PriorityCity priorityCity:priorityCities){
							if(priorityCity.getCity().equals(city)){
								priorityCity.setCount(priorityCity.getCount()+1);
								cityFoundInPriorityList = true;
								break;
							}
						}
						if(!cityFoundInPriorityList){
							PriorityCity pc = new PriorityCity();
							pc.setCity(city);
							pc.setCount(1);
							priorityCities.add(pc);
						}
					}
				}
			}
			
			ArrayList<PriorityCity> priorityCityList = new ArrayList<PriorityCity>(priorityCities);
			
			java.util.Collections.sort(priorityCityList, new Comparator<PriorityCity>() {

				@Override
				public int compare(PriorityCity o1, PriorityCity o2) {
					if(o1.count<o2.count) return 1;
					else if(o1.count>o2.count) return -1;
					else return 0;
				}
				
			});
			
			ArrayList<String> favCities = new ArrayList<String>();
			for(PriorityCity priorityCity:priorityCityList){
				favCities.add(priorityCity.getCity());
			}
					
			otherCities.removeAll(favCities);
			
			prioritizedCities.add(favCities);
			prioritizedCities.add(otherCities);
			
			responseData.setData(prioritizedCities);
			responseData.setStatus(Constants.STATUS_SUCCESS);
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData getFrequentCities(String userName) {
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			
			String mostBooked = "";
			
			Map<String, Integer> cityMap= null;
			List<TBLTicket> tblTickets = ticketDao.getTicketsByUsername(userName);
			
			if(null!=tblTickets && tblTickets.size()>0){
				cityMap= new HashMap<>();
				for(TBLTicket ticket: tblTickets){
					String sourceDestination= ticket.getSource()+"^"+ticket.getDestination();
					if(cityMap.containsKey(sourceDestination)){
						int count=cityMap.get(sourceDestination);
						cityMap.put(sourceDestination, count+1);
					}else{
						cityMap.put(sourceDestination, 1);
					}
				}
				int highestValue=0;
				
				for(String city:cityMap.keySet()){
					if(cityMap.get(city)>=highestValue){
						int noOfBooking = cityMap.get(city);
						highestValue=noOfBooking;
						mostBooked = city;
					}
				}
				responseData.setData(mostBooked);
				responseData.setStatus(Constants.STATUS_SUCCESS);
			}else{
				responseData.setStatus(Constants.STATUS_FAILURE);
				responseData.setMessage("No ticket booked earlier");
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
	public ResponseData hasEverTravelled(String userName) {
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			if(userName==null || userName==""){
				responseData.setStatus(Constants.STATUS_FAILURE);
				responseData.setMessage("Login required");
			}else{
				long noOfBookings = ticketDao.getNoOfBookings(userName);
				System.out.println("user: "+userName+" has booked "+noOfBookings+" tickets");
				if(noOfBookings>0){
					responseData.setData(noOfBookings);
					responseData.setStatus(Constants.STATUS_SUCCESS);
				}else{
					responseData.setStatus(Constants.STATUS_FAILURE);
					responseData.setMessage("No ticket booked earlier");
				}
			}
		}
		catch(Exception exception){
			responseData.setErrorMessage(exception.getMessage());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
		return responseData;
	}
}
