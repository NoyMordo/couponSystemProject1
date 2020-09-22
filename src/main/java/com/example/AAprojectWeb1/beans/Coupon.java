package com.example.AAprojectWeb1.beans;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "coupons")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int couponId;
	@Column
	private String title;
	@Column
	private String description;
	@Column
	private Date startDate;
	@Column
	private Date endDate;
	@Column
	private int amount;
	@Column
	private double price;
	@Column
	private String image;
	@Column
	private Category categoryType;
//	@ManyToOne
	private int companyId;
	@ManyToMany(mappedBy = "coupons",fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Customer> customers;
	
	
	public Coupon () {}

	public Coupon(int couponId, String title, String description, Date startDate, Date endDate, int amount,
			double price, String image, Category categoryType, int companyId, Set<Customer> customers) {
		super();
		this.couponId = couponId;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
		this.categoryType = categoryType;
		this.companyId = companyId;
		this.customers = customers;
	}

	public Coupon(int couponId, String title, String description, Date startDate, Date endDate, int amount,
			double price, String image, Category categoryType, Set<Customer> customers) {
		super();
		this.couponId = couponId;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
		this.categoryType = categoryType;
		this.customers = customers;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Category getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(Category categoryType) {
		this.categoryType = categoryType;
	}

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

	public int getCouponId() {
		return couponId;
	}

	public int getCompanyId() {
		return companyId;
	}
	

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + couponId;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		if (couponId != other.couponId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", title=" + title + ", description=" + description + ", startDate="
				+ startDate + ", endDate=" + endDate + ", amount=" + amount + ", price=" + price + ", image=" + image
				+ ", categoryType=" + categoryType + ", companyId=" + companyId + "]";
	}
 
	
	

}
