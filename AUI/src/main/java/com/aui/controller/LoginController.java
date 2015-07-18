/**
 * 
 */
package com.aui.controller;

import static com.aui.util.Constants.STATUS_SUCCESS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aui.pojo.ResponseData;
import com.aui.service.AuthenticationService;
import com.aui.util.Constants;



/**
 * @author sinankit
 *
 */
@Controller
@RequestMapping(value = "/security")
public class LoginController {

	@Autowired
	@Qualifier("authenticationManager")
	AuthenticationManager authenticationManager;
	
	@Autowired
	SecurityContextRepository securityContextRepository;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@RequestMapping(value="/login", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public @ResponseBody ResponseData doLogin(@RequestBody com.aui.pojo.Authentication authPojo, HttpServletRequest request, HttpServletResponse response){
        return authenticationService.doUserLogin(authPojo, request, response);
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseData doLogout(){
		ResponseData responseData = new ResponseData();
		try{
	        SecurityContextHolder.getContext().setAuthentication(null);
			responseData.setStatus(STATUS_SUCCESS);
		}catch(Exception e){
			responseData.setErrorMessage(e.toString());
			responseData.setStatus(Constants.STATUS_ERROR);
		}
        return responseData;
	}
	
	@RequestMapping(value = "/loggedUser", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseData getLoggedUser() {
		ResponseData responseData = new ResponseData();
		String userName = authenticationService.getAuthenticatedUserName();
		if(userName==null){
			responseData.setStatus(Constants.STATUS_FAILURE);
		}else{
			responseData.setData(userName);
			responseData.setStatus(STATUS_SUCCESS);
		}
		return responseData;
	}

	@RequestMapping(value = "/loginSuccess", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseData loginSuccess(HttpServletRequest request,HttpServletResponse response) {
		ResponseData responseData = new ResponseData();
		String userName = authenticationService.getAuthenticatedUserName();
		if(userName==null){
			responseData.setStatus(Constants.STATUS_ERROR);
		}else{
			responseData.setData(userName);
			responseData.setStatus(STATUS_SUCCESS);
		}
		return responseData;
	}
	
	@RequestMapping(value = "/loginFailed", method = RequestMethod.GET)
	public @ResponseBody ResponseData loginerror() {
		ResponseData responseData = new ResponseData();
		responseData.setErrorMessage("authentication failure");
		responseData.setStatus(Constants.STATUS_FAILURE);
		return responseData;
	}

	@RequestMapping(value = "/logoutSuccess", method = RequestMethod.GET)
	public @ResponseBody ResponseData logout() {
		ResponseData responseData = new ResponseData();
		responseData.setStatus(STATUS_SUCCESS);
		return responseData;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseData SpringSecurityTest() {
		ResponseData responseData = new ResponseData();
		responseData.setStatus(STATUS_SUCCESS);
		return responseData;
	}
	
	@RequestMapping(value = "/loginTest", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResponseData login(HttpServletRequest request, HttpServletResponse response) {
		ResponseData responseData = new ResponseData();
		Map<String, String> loginCredentials = readInputStreamForLoginCredentials(request);
        String username=loginCredentials.get("j_username");
        String password=loginCredentials.get("j_password");
        
        if (username == null) { username = ""; }

        if (password == null) { password = ""; }

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        
        Authentication auth = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
        
        if(auth.isAuthenticated()){
        	responseData.setData(auth.getName());
			responseData.setStatus(STATUS_SUCCESS);
        }else{
        	responseData.setErrorMessage("authentication failure");
    		responseData.setStatus(Constants.STATUS_FAILURE);
        }
        return responseData;
	}
	
	private Map<String, String> readInputStreamForLoginCredentials(HttpServletRequest request) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JSONObject jsonObject = null;
		String username = null, password = null;
		try {
			IOUtils.copyLarge(request.getInputStream(), out);
			byte[] buffer = out.toByteArray();
			
			jsonObject =  new JSONObject(new String(buffer));
			if(jsonObject != null) {
				username = jsonObject.getString("username");
				password = jsonObject.getString("password");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, String> map = new HashMap<String,String>();
		map.put("j_username", new String ( username ));
		map.put("j_password", new String ( password ));
		return map;
	}
}
