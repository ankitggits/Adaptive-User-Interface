/**
 * 
 */
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

/**
 * @author sinankit
 *
 */
@Entity
@Table(name="USER")
public class TBLUser extends TBLRoot implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@OneToOne(fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SELECT)
	@Cascade(value=CascadeType.ALL)
	private TBLAuthentication tblAuthentication;
	@Column(name="FIRSTNAME")
	private String firstName;
	@Column(name="LASTNAME")
	private String lastName;
	@OneToOne(fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SELECT)
	@Cascade(value=CascadeType.ALL)
	private TBLAddress tblAddress;
	@Column(name="PHONE")
	private String phone;
	@Column(name="DATEOFBIRTH")
	private String dob;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public TBLAuthentication getTblAuthentication() {
		return tblAuthentication;
	}
	public void setTBLAuthentication(TBLAuthentication tblAuthentication) {
		this.tblAuthentication = tblAuthentication;
	}
	public TBLAddress getTBLAddress() {
		return tblAddress;
	}
	public void setTBLAddress(TBLAddress tblAddress) {
		this.tblAddress = tblAddress;
	}
}
