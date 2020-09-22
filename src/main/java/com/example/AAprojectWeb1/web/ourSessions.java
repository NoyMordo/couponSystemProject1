package com.example.AAprojectWeb1.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;

import com.example.AAprojectWeb1.facades.ClientFacade;

public class ourSessions {

	private ClientFacade clientFacade;
	private long lastAcssse;

	public ourSessions(ClientFacade clientFacade, long lastAcssse) {
		super();
		this.clientFacade = clientFacade;
		this.lastAcssse = lastAcssse;
	}

	public long getLastAcssse() {
		return lastAcssse;
	}

	public void setLastAcssse(long lastAcssse) {
		this.lastAcssse = lastAcssse;
	}

	public ClientFacade getClientFacade() {
		return clientFacade;
	}

}
