package com.aui.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private boolean postOnly = true;
	
    public CustomUsernamePasswordAuthenticationFilter() {
        super();
    }
  
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        
    	if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        Map<String, String> loginCredentials = readInputStreamForLoginCredentials(request);
        String username=loginCredentials.get("j_username");
        String password=loginCredentials.get("j_password");
        
        if (username == null) { username = ""; }

        if (password == null) { password = ""; }

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        System.out.println("customusernamepasswordauthentication" + username);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

	private Map<String, String> readInputStreamForLoginCredentials(HttpServletRequest request) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JSONObject jsonObject = null;
		String username = null, password = null;
		try {
			IOUtils.copyLarge(request.getInputStream(), out);
			byte[] buffer = out.toByteArray();
			parseRequestBody(buffer);
			
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
	
	private void parseRequestBody(byte[] buffer) {
		System.out.println("Request Data:"+ new String(buffer));
	}
    
}

