package com.aui.framework.model;

import java.io.Serializable;
import java.util.Date;

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
	
	@Column
	private Date lastSuccessfullTransaction;
	
	@Column
	private long levelFactor;
	
	@Column
	private String userLevel;
	
	@Column
	private boolean hasEverBooked=false;
	
	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public boolean hasEverBooked() {
		return hasEverBooked;
	}

	public void setEverBooked(boolean hasEverBooked) {
		this.hasEverBooked = hasEverBooked;
	}

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

	public Date getLastSuccessfullTransaction() {
		return lastSuccessfullTransaction;
	}

	public void setLastSuccessfullTransaction(Date lastSuccessfullTransaction) {
		this.lastSuccessfullTransaction = lastSuccessfullTransaction;
	}

	public long getLevelFactor() {
		return levelFactor;
	}

	public void setLevelFactor(long levelFactor) {
		this.levelFactor = levelFactor;
	}
	
}
