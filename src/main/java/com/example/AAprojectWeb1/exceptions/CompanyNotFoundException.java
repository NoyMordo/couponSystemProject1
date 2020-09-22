package com.example.AAprojectWeb1.exceptions;

public class CompanyNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1;

	public CompanyNotFoundException() {
		super("company not found!");
	}

}
