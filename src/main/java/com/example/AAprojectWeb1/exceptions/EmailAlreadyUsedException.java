package com.example.AAprojectWeb1.exceptions;

public class EmailAlreadyUsedException extends Exception {

	public EmailAlreadyUsedException () {
		super("EROR this email is already used in the system."
				+ " please try another email");
	}
}
