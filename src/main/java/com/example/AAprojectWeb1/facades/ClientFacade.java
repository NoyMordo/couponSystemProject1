package com.example.AAprojectWeb1.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.AAprojectWeb1.exceptions.LoginFailedException;
import com.example.AAprojectWeb1.repository.CompanyRepository;
import com.example.AAprojectWeb1.repository.CouponRepository;
import com.example.AAprojectWeb1.repository.CustomerRepository;
import com.example.AAprojectWeb1.repository.shoppingCartRepository;

@Service
@Scope("prototype")
public abstract class ClientFacade {

	protected CompanyRepository compRepo;
	protected CouponRepository coupRepo;
	protected CustomerRepository custRepo;
	protected shoppingCartRepository shopRepo;

	

	public ClientFacade(CompanyRepository compRepo, CouponRepository coupRepo, CustomerRepository custRepo, shoppingCartRepository shopRepo) {
		super();
		this.compRepo = compRepo;
		this.coupRepo = coupRepo;
		this.custRepo = custRepo;
		this.shopRepo = shopRepo;
	}




	public abstract boolean login(String email, String password) throws LoginFailedException;
	
}
