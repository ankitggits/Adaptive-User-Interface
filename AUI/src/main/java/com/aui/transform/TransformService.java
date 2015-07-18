package com.aui.transform;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.aui.model.TBLAddress;
import com.aui.model.TBLAuthentication;
import com.aui.model.TBLFlight;
import com.aui.model.TBLFlightLogo;
import com.aui.model.TBLSecurityQuestion;
import com.aui.model.TBLUser;
import com.aui.model.TBLUserQuesAns;
import com.aui.pojo.Address;
import com.aui.pojo.Authentication;
import com.aui.pojo.Flight;
import com.aui.pojo.FlightLogo;
import com.aui.pojo.SecurityQuestion;
import com.aui.pojo.User;
import com.aui.pojo.UserQuesAns;

@Component
public class TransformService {

	public TBLUser transformUser(User user){
		TBLUser tblUser = new TBLUser();
		BeanUtils.copyProperties(user, tblUser,new String[]{"authentication","address"});
		TBLAuthentication tblAuthentication = transformAuthentication(user.getAuthentication());
		TBLAddress tblAddress = transformAddress(user.getAddress());
		tblUser.setTBLAuthentication(tblAuthentication);
		tblUser.setTBLAddress(tblAddress);
		return tblUser;
	}
	
	public User transformTBLUser(TBLUser tblUser){
		User user = new User();
		BeanUtils.copyProperties(tblUser, user, new String[]{"tblAuthentication","tblAddress"});
		Authentication authentication = transformTBLAuthentication(tblUser.getTblAuthentication());
		Address address = transformTBLAddress(tblUser.getTBLAddress());
		user.setAuthentication(authentication);
		user.setAddress(address);
		return user;
	}
	
	public Address transformTBLAddress(TBLAddress tblAddress){
		Address address = new Address();
		BeanUtils.copyProperties(tblAddress, address);
		return address;
	}
	
	public TBLAddress transformAddress(Address address){
		TBLAddress tblAddress = new TBLAddress();
		BeanUtils.copyProperties(address, tblAddress);
		return tblAddress;
	}
	
	public Authentication transformTBLAuthentication(TBLAuthentication tblAuthentication){
		Authentication authentication = new Authentication();
		BeanUtils.copyProperties(tblAuthentication, authentication, new String[]{"tblUserQuesAns"});
		List<UserQuesAns> userQuesAnsList = null;
		if(tblAuthentication.getTblUserQuesAns()!=null && tblAuthentication.getTblUserQuesAns().size()>0){
			userQuesAnsList = new ArrayList<UserQuesAns>();
			for(TBLUserQuesAns tblUserQuesAns:tblAuthentication.getTblUserQuesAns()){
				userQuesAnsList.add(transformTBLUserQuesAns(tblUserQuesAns));
			}
		}
		authentication.setUserQuesAns(userQuesAnsList);
		return authentication;
	}
	
	public TBLAuthentication transformAuthentication(Authentication authentication){
		TBLAuthentication tblAuthentication = new TBLAuthentication();
		BeanUtils.copyProperties(authentication, tblAuthentication, new String[]{"userQuesAns"});
		List<TBLUserQuesAns> tblUserQuesAnsList = null;
		if(authentication.getUserQuesAns()!=null && authentication.getUserQuesAns().size()>0){
			tblUserQuesAnsList = new ArrayList<TBLUserQuesAns>();
			for(UserQuesAns userQuesAns:authentication.getUserQuesAns()){
				tblUserQuesAnsList.add(transformUserQuesAns(userQuesAns));
			}
		}
		tblAuthentication.setTblUserQuesAns(tblUserQuesAnsList);
		return tblAuthentication;
	}
	
	public SecurityQuestion transformTBLSecurityQuestion(TBLSecurityQuestion tblSecurityQuestion){
		SecurityQuestion securityQuestion = new SecurityQuestion();
		BeanUtils.copyProperties(tblSecurityQuestion, securityQuestion);
		return securityQuestion;
	}
	
	public TBLSecurityQuestion transformSecurityQuestion(SecurityQuestion securityQuestion){
		TBLSecurityQuestion tblSecurityQuestion = new TBLSecurityQuestion();
		BeanUtils.copyProperties(securityQuestion, tblSecurityQuestion);
		return tblSecurityQuestion;
	}

	public TBLFlight transformFlight(Flight flight) {
		TBLFlight tblFlight = new TBLFlight();
		BeanUtils.copyProperties(flight, tblFlight);
		return tblFlight;
	}
	
	public Flight transformTBLFlight(TBLFlight tblFlight) {
		Flight flight = new Flight();
		BeanUtils.copyProperties(tblFlight, flight);
		return flight;
	}
	
	public UserQuesAns transformTBLUserQuesAns(TBLUserQuesAns tblUserQuesAns){
		UserQuesAns userQuesAns = new UserQuesAns();
		BeanUtils.copyProperties(tblUserQuesAns, userQuesAns);
		return userQuesAns;
	}
	
	public TBLUserQuesAns transformUserQuesAns(UserQuesAns userQuesAns){
		TBLUserQuesAns tblUserQuesAns = new TBLUserQuesAns();
		BeanUtils.copyProperties(userQuesAns, tblUserQuesAns);
		return tblUserQuesAns;
	}
	
	public TBLFlightLogo transformFlightLogo(FlightLogo logo){
		TBLFlightLogo tblFlightLogo = new TBLFlightLogo();
		BeanUtils.copyProperties(logo, tblFlightLogo, "logoImg");
		try {
			//tblFlightLogo.setLogoImg(logo.getLogoImg().getBytes());
			Blob blob = new javax.sql.rowset.serial.SerialBlob(logo.getLogoImg().getBytes());
			tblFlightLogo.setLogoImg(blob);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tblFlightLogo;
	}
	

	public FlightLogo transformFlightLogo(TBLFlightLogo tblFlightLogo){
		FlightLogo flightLogo = new FlightLogo();
		BeanUtils.copyProperties(tblFlightLogo, flightLogo, "logoImg");
		flightLogo.setLogoImg(tblFlightLogo.getLogoImg().toString());
		return flightLogo;
	}
	
}
