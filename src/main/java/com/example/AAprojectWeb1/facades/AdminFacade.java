package com.example.AAprojectWeb1.facades;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.transform.ToListResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.AAprojectWeb1.beans.Company;
import com.example.AAprojectWeb1.beans.Coupon;
import com.example.AAprojectWeb1.beans.Customer;
import com.example.AAprojectWeb1.beans.shoppingCart;
import com.example.AAprojectWeb1.exceptions.CompanyExistsException;
import com.example.AAprojectWeb1.exceptions.CompanyNameChangeException;
import com.example.AAprojectWeb1.exceptions.CompanyNotFoundException;
import com.example.AAprojectWeb1.exceptions.CustomerNotFoundException;
import com.example.AAprojectWeb1.exceptions.EmailAlreadyUsedException;
import com.example.AAprojectWeb1.exceptions.LoginFailedException;
import com.example.AAprojectWeb1.repository.CompanyRepository;
import com.example.AAprojectWeb1.repository.CouponRepository;
import com.example.AAprojectWeb1.repository.CustomerRepository;
import com.example.AAprojectWeb1.repository.shoppingCartRepository;

@Service
@Scope("prototype")
public class AdminFacade extends ClientFacade {

	
	public AdminFacade(CompanyRepository compRepo, CouponRepository coupRepo, CustomerRepository custRepo, shoppingCartRepository shopRepo) {
		super(compRepo, coupRepo, custRepo, shopRepo);
	}

	public boolean login(String email, String password) throws LoginFailedException {
		if (email.equals("admin@admin.com") && password.equals("admin")) { // Hard-Coded - Must be this specific email and password
																			
			return true;
		}
		throw new LoginFailedException();

	}

	public void addCompany(Company company) throws CompanyExistsException {
		List<Company> companies = compRepo.findAll();
		for (Company comp : companies) {
			// Checks that the name and email of the new company is not the same as the email of  an existing company
			if (comp.getName().equals(company.getName()) || comp.getEmail().equals(company.getEmail())) { 
				throw new CompanyExistsException();
			}

		}
		compRepo.save(company); // if is not the same-> you can add the new company
	}

	public void updateCompany(Company company) throws CompanyNameChangeException, CompanyNotFoundException {
		Company comp = compRepo.findById(company.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
		if (comp.getName().equals(company.getName())) {// Checking if the name stays the same (name cannot be updated)
			compRepo.save(company);
		} else {
			throw new CompanyNameChangeException();
		}
	}

	public void deleteCompany(int companyId) throws CompanyNotFoundException {
		Company c = compRepo.findById(companyId).orElseThrow(CompanyNotFoundException::new);
		for (Coupon coup : c.getCuopons()) {
			coupRepo.deleteCustomerCoupons(coup.getCouponId()); //Delete from the linking table of coupon and customer
			shopRepo.deleteShoppingCartCoupons(coup.getCouponId()); //Delete from the linking table of coupon and shopping cart
		}
		shopRepo.updateCartTotal(); //update the total price
		coupRepo.deleteCompanyCoupons(companyId); //delete the coupon
		compRepo.deleteById(companyId); //and after all -> delete the company
		
		
	}

	public List<Company> getAllCompanies() {
		return compRepo.findAll();
	}

	public Company getOneCompany(int companyId) throws CompanyNotFoundException {
		return compRepo.findById(companyId).orElseThrow(CompanyNotFoundException::new);
	}

	public void addCustomer(Customer customer) throws EmailAlreadyUsedException {
		shoppingCart shop = new shoppingCart();
		shopRepo.save(shop);//save the shopping cart and add the customer 
		customer.setShoppingCart(shop); //update to this shopping cart
		List<Customer> customers = custRepo.findAll();
		for (Customer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail())) {// Check that the new customer's email is not the same as an existing customer's email
																
				throw new EmailAlreadyUsedException();
			}
		}

		custRepo.save(customer);// if is not the same email-> you can add the customer

		

	}

	public void updateCustomer(Customer customer) throws CustomerNotFoundException {
		Customer c = custRepo.findById(customer.getCustomerId()).orElseThrow(CustomerNotFoundException::new);
		if (c != null) {
			custRepo.save(customer);
		}
	}

	public void deleteCustomer(int customerId) throws CustomerNotFoundException {
		Customer c = custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new);
		for (Coupon coup : c.getCoupons()) {
			coup.getCustomers().remove(c);// Deletes customer purchase history from the linking table
			coupRepo.save(coup);
		}
		long shopId = c.getShoppingCart().getId();
		custRepo.deleteById(customerId);//delete the customer first
		shopRepo.deleteById(shopId);//delete the reference shopping cart to customer  
	}

	public List<Customer> getAllCustomers() {
		return custRepo.findAll();
	}

	public Customer getCustomerById(int customerId) throws CustomerNotFoundException {
		return custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new);
	}
	
	public List<Coupon> getCompanyCoupons(int companyId) throws CompanyNotFoundException { //help method
		Company c = getOneCompany(companyId);
		return c.getCuopons();
	}
	
	public Set<Coupon> getCustomerCoupons(int customerId) throws CustomerNotFoundException { //help method
		Customer c = getCustomerById(customerId);
		return c.getCoupons();
	}
}
