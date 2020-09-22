package com.example.AAprojectWeb1.exceptions;

public class CouponAlreadyExsistException extends Exception {

	public CouponAlreadyExsistException () {
		super("coupon already exsist! "
				+ "The same coupon cannot be purchased more than once");
			
		}
	}
	
	

