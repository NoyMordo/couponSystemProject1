package com.example.AAprojectWeb1.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "shoppingCart")
public class shoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Coupon> coupons;
	@Column
	private double totalPrice;

	public shoppingCart() {
	}

	public shoppingCart(Set<Coupon> coupons, Customer customer, double totalPrice) {
		super();
		this.coupons = coupons;
		this.totalPrice = totalPrice;
	}

	public void addCoupon(Coupon coupon) {
		if (this.coupons == null) {
			this.coupons = new HashSet<Coupon>();
		}
		this.coupons.add(coupon);
		recalculate();
	}

	public void recalculate() {
		totalPrice = 0;
		for (Coupon coupon : coupons) { // recalculate your value and update
			totalPrice += coupon.getPrice();
		}
	}

	public Set<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "shoppingCart [id=" + id + ", coupons=" + coupons + ", totalPrice=" + totalPrice + "]";
	}

	public void removeCoupon(Coupon coupon) {
		coupons.remove(coupon);
	}

}
