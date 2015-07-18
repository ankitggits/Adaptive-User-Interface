package com.aui.framework.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.aui.model.TBLRoot;

@Entity
@Table(name="ACTIVITY")
public class TBLActivity extends TBLRoot implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	private String userName;
	
	@Column
	private long loginFrequency;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getLoginFrequency() {
		return loginFrequency;
	}

	public void setLoginFrequency(long loginFrequency) {
		this.loginFrequency = loginFrequency;
	}
	
}
