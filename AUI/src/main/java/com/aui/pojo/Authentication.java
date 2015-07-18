/**
 * 
 */
package com.aui.pojo;

import java.util.List;



public class Authentication{
	
	private User user;
	private String username;
	private String password;
	private String status;
	private List<UserQuesAns> userQuesAns;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<UserQuesAns> getUserQuesAns() {
		return userQuesAns;
	}

	public void setUserQuesAns(List<UserQuesAns> userQuesAns) {
		this.userQuesAns = userQuesAns;
	}

	
	
	

}
