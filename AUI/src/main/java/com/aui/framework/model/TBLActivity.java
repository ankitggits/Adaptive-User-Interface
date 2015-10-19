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
	private boolean hasEverBooked=false;
	
	public boolean isHasEverBooked() {
		return hasEverBooked;
	}

	public void setHasEverBooked(boolean hasEverBooked) {
		this.hasEverBooked = hasEverBooked;
	}

	public Date getlastSuccessfullTransaction() {
		return lastSuccessfullTransaction;
	}

	public void setlastSuccessfullTransaction(Date lastSuccessfullTransaction) {
		this.lastSuccessfullTransaction = lastSuccessfullTransaction;
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
	
}
