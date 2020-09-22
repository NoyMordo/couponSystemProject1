package com.example.AAprojectWeb1.loginManager;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.example.AAprojectWeb1.exceptions.LoginFailedException;
import com.example.AAprojectWeb1.facades.AdminFacade;
import com.example.AAprojectWeb1.facades.ClientFacade;
import com.example.AAprojectWeb1.facades.CompanyFacade;
import com.example.AAprojectWeb1.facades.CustomerFacade;

@Service
public class LoginManager {

	private ConfigurableApplicationContext ctx;

	public LoginManager(ConfigurableApplicationContext ctx) {
		super();
		this.ctx = ctx;
	}

	public ClientFacade login(String email, String password, ClientType type) throws LoginFailedException {
		switch (type) {
		case Administrator:
			AdminFacade admin = ctx.getBean(AdminFacade.class);
			if (admin.login(email, password))
				return admin;
			break;
		case Customer:
			CustomerFacade customer = ctx.getBean(CustomerFacade.class);
			if (customer.login(email, password))
				return customer;
			break;

		case Company:
			CompanyFacade company = ctx.getBean(CompanyFacade.class);
			if (company.login(email, password))
				return company;
			break;
		}
		throw new LoginFailedException();

	}

}