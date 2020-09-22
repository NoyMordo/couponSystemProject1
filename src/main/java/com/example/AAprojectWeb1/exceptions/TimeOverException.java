package com.example.AAprojectWeb1.exceptions;

public class TimeOverException extends Exception {
	
	public TimeOverException () {
		super("the time is over! Expiration date has passed. You can't buy this coupon");
	}

}
