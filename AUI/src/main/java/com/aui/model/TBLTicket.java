package com.aui.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="PAYMENT")
public class TBLTicket extends TBLRoot implements Serializable{

	private static final long serialVersionUID = 1L;

	@OneToMany(fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SELECT)
	private List<TBLFlight> flights;
	
	@OneToOne(fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SELECT)
	@Cascade(value=CascadeType.ALL)
	private TBLFundingSource tblFundingSource;
	
	@Column(name="AMOUNT")
	private double amount;
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public List<TBLFlight> getFlights() {
		return flights;
	}

	public void setFlights(List<TBLFlight> flights) {
		this.flights = flights;
	}

	public TBLFundingSource getTblFundingSource() {
		return tblFundingSource;
	}

	public void setTblFundingSource(TBLFundingSource tblFundingSource) {
		this.tblFundingSource = tblFundingSource;
	}
}
