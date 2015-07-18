package com.aui.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="FLIGHTLOGO")
public class TBLFlightLogo extends TBLRoot implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name="COMPANY")
	private String companyName;
	@Column(name="LOGO")
	private Blob logoImg;
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Blob getLogoImg() {
		return logoImg;
	}
	public void setLogoImg(Blob logoImg) {
		this.logoImg = logoImg;
	}
	
	
}
