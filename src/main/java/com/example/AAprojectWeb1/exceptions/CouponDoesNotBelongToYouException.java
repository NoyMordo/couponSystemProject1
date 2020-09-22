package com.example.AAprojectWeb1.exceptions;

public class CouponDoesNotBelongToYouException extends Exception {
	
	public CouponDoesNotBelongToYouException () {
		super("you cannot delete a coupon that does not belong to your company!! ");
	}

}
