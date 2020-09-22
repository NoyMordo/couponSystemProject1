package com.example.AAprojectWeb1.web;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AAprojectWeb1.exceptions.LoginFailedException;
import com.example.AAprojectWeb1.facades.ClientFacade;
import com.example.AAprojectWeb1.loginManager.ClientType;
import com.example.AAprojectWeb1.loginManager.LoginManager;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

	private LoginManager loginManager;
	private Map<String, ourSessions> sessions;

	public LoginController(LoginManager loginManager, Map<String, ourSessions> sessions) {
		super();
		this.loginManager = loginManager;
		this.sessions = sessions;
	}

	@PostMapping("/login/{email}/{password}/{clientType}")
	public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password,
			@PathVariable ClientType clientType) throws LoginFailedException {
		String token = UUID.randomUUID().toString();
		sessions.put(token, new ourSessions(loginManager.login(email, password, clientType), System.currentTimeMillis()));
		return ResponseEntity.ok(token);
	}

	@PostMapping("logout/{token}")
	public ResponseEntity<String> logout(@PathVariable String token) {
		sessions.remove(token);
		return ResponseEntity.ok().body("logged out");
	}
	
	
	
}
