package com.example.AAprojectWeb1.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "companies")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int companyId;
	@Column
	private String name;
	@Column
	private String email;
	@Column
	private String password;
	@OneToMany(mappedBy = "companyId", fetch = FetchType.EAGER)
	private List<Coupon> coupons;

	public Company() {
	}

	public Company(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCuopons() {
		return coupons;
	}

	public void setCuopons(List<Coupon> cuopons) {
		this.coupons = cuopons;
	}

	public int getCompanyId() {
		return companyId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Company) {
			Company c = (Company) obj;
			if (c.companyId == this.companyId && c.email == this.email) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return companyId * 13 + email.hashCode();
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", cuopons=" + coupons + "]";
	}

}
