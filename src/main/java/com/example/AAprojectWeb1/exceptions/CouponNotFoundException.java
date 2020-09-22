package com.example.AAprojectWeb1.exceptions;

public class CouponNotFoundException extends Exception {

	public CouponNotFoundException () {
		super("coupon not found !"
				+ "This coupon inventory is over, try another coupon :)");
	}
}
