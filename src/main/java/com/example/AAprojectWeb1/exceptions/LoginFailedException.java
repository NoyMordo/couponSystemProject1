package com.example.AAprojectWeb1.exceptions;

public class LoginFailedException extends Exception {

	public LoginFailedException() {
		super("login failed! Check your email and password and try again ");
	}
}
