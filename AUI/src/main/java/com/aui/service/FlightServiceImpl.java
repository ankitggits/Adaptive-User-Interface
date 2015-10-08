package com.aui.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aui.dao.FlightDao;
import com.aui.model.TBLBookedTicket;
import com.aui.model.TBLFlight;
import com.aui.model.TBLFlightLogo;
import com.aui.model.TBLUser;
import com.aui.model.TBLUserQuesAns;
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ResponseData bookFlight(BookedTicket bookedTicket) {
		
		ResponseData responseData = null; 
		try{
			responseData = context.getBean(ResponseData.class);
			TBLBookedTicket tblBookedTicket = transformService.transformBookedTicket(bookedTicket);
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

}
