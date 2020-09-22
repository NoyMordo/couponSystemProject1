package com.example.AAprojectWeb1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class globalExceptionHendler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CompanyExistsException.class)
	public ResponseEntity<String> companyExistsException(CompanyExistsException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<String> companyNotFoundException(CompanyNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(CompanyNameChangeException.class)
	public ResponseEntity<String> CompanyNameChangeException(CompanyNameChangeException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(EmailAlreadyUsedException.class)
	public ResponseEntity<String> EmailAlreadyUsedException(EmailAlreadyUsedException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<String> CustomerNotFoundException(CustomerNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(LoginFailedException.class)
	public ResponseEntity<String> LoginFailedException(LoginFailedException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	@ExceptionHandler(TitleAlreadyUsedException.class)
	public ResponseEntity<String> TitleAlreadyUsedException(TitleAlreadyUsedException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(CouponNotFoundException.class)
	public ResponseEntity<String> CouponNotFoundException(CouponNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//		return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(e.getMessage());
	}

	@ExceptionHandler(idChangeException.class)
	public ResponseEntity<String> idChangeException(idChangeException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(CouponDoesNotBelongToYouException.class)
	public ResponseEntity<String> CouponDoesNotBelongToYouException(CouponDoesNotBelongToYouException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(CouponAlreadyExsistException.class)
	public ResponseEntity<String> CouponAlreadyExsistException(CouponAlreadyExsistException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(TimeOverException.class)
	public ResponseEntity<String> TimeOverException(TimeOverException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(invalidTokenException.class)
	public ResponseEntity<String> invalidTokenException(invalidTokenException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	@ExceptionHandler(timeOutExeption.class)
	public ResponseEntity<String> timeOutExeption(timeOutExeption e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
}
