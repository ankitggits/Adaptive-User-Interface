package com.aui.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="FUNDING_SOURCE")
public class TBLFundingSource extends TBLRoot implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="TYPE")
	private String type;
	@Column(name="FUNDING_NUMBER")
	private String fundingNumber;
	@Column(name="EXPIRY_DATE")
	private String expiryDate;
	@Column(name="NAME_ON_SOURCE")
	private String nameOnSource;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFundingNumber() {
		return fundingNumber;
	}

	public void setFundingNumber(String fundingNumber) {
		this.fundingNumber = fundingNumber;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getNameOnSource() {
		return nameOnSource;
	}

	public void setNameOnSource(String nameOnSource) {
		this.nameOnSource = nameOnSource;
	}

}
