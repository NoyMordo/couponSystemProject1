package com.example.AAprojectWeb1.exceptions;

public class TitleAlreadyUsedException extends Exception {

	public TitleAlreadyUsedException() {
		super("title is already in used. Choose a different title");
	}
}
