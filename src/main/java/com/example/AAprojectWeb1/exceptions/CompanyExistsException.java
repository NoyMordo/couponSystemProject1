package com.example.AAprojectWeb1.exceptions;

public class CompanyExistsException extends Exception {

	public CompanyExistsException () {
		super("The company already exists in the system! (cannot add company with the same email or name)");
	}
}
