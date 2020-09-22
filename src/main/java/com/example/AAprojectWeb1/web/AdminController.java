package com.example.AAprojectWeb1.web;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AAprojectWeb1.beans.Company;
import com.example.AAprojectWeb1.beans.Customer;
import com.example.AAprojectWeb1.exceptions.CompanyExistsException;
import com.example.AAprojectWeb1.exceptions.CompanyNameChangeException;
import com.example.AAprojectWeb1.exceptions.CompanyNotFoundException;
import com.example.AAprojectWeb1.exceptions.CustomerNotFoundException;
import com.example.AAprojectWeb1.exceptions.EmailAlreadyUsedException;
import com.example.AAprojectWeb1.exceptions.invalidTokenException;
import com.example.AAprojectWeb1.facades.AdminFacade;
import com.example.AAprojectWeb1.facades.ClientFacade;
import com.example.AAprojectWeb1.loginManager.ClientType;
import com.example.AAprojectWeb1.loginManager.LoginManager;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	private Map<String, ourSessions> sessions;

	public AdminController(Map<String, ourSessions> sessions) {
		super();
		this.sessions = sessions;
	}

	@PostMapping("/addCompany/{token}")
	public ResponseEntity<?> addCompany(@PathVariable String token, @RequestBody Company company)
			throws CompanyExistsException, invalidTokenException {
		ourSessions session = sessions.get(token);
		((AdminFacade) session.getClientFacade()).addCompany(company);
		return ResponseEntity.ok(company);
	}

	@PutMapping("/updateCompany/{token}")
	public ResponseEntity<?> updateCompany(@PathVariable String token, @RequestBody Company company)
			throws CompanyNameChangeException, CompanyNotFoundException, invalidTokenException {
		ourSessions session = sessions.get(token);
		((AdminFacade) session.getClientFacade()).updateCompany(company);
		return ResponseEntity.ok(company);
	}

	@DeleteMapping("/deleteCompany/{companyId}/{token}")
	public ResponseEntity<?> deleteCompany(@PathVariable String token, @PathVariable int companyId)
			throws CompanyNotFoundException, invalidTokenException {
		ourSessions session = sessions.get(token);
		((AdminFacade) session.getClientFacade()).deleteCompany(companyId);
		return ResponseEntity.ok("company id: " + companyId + " deleted");
	}

	@GetMapping("/getAllCompnies/{token}")
	public ResponseEntity<List<Company>> getAllCompanies(@PathVariable String token) throws invalidTokenException { 
		ourSessions session = sessions.get(token);
		AdminFacade admin =(AdminFacade) session.getClientFacade();
		return ResponseEntity.ok(admin.getAllCompanies()); //ArrayList cannot be cast responseEntity

	}

	@GetMapping("/getOneCompany/{companyId}/{token}")
	public ResponseEntity<?> getOneCompany(@PathVariable String token, @PathVariable int companyId)
			throws CompanyNotFoundException, invalidTokenException {
		ourSessions session = sessions.get(token);
		AdminFacade admin = (AdminFacade) session.getClientFacade();
		return ResponseEntity.ok(admin.getOneCompany(companyId));
	}

	@PostMapping("/addCustomer/{token}")
	public ResponseEntity<?> addCustomer(@PathVariable String token, @RequestBody Customer customer)
			throws EmailAlreadyUsedException, invalidTokenException {
		ourSessions session = sessions.get(token);
		AdminFacade admin = (AdminFacade) session.getClientFacade();
		admin.addCustomer(customer);
		return ResponseEntity.ok(customer);
	}

	@PutMapping("/updateCustomer/{token}")
	public ResponseEntity<?> updateCustomer(@PathVariable String token, @RequestBody Customer customer)
			throws CustomerNotFoundException, invalidTokenException {
		ourSessions session = sessions.get(token);
		AdminFacade admin = (AdminFacade) session.getClientFacade();
		System.out.println(customer);
		admin.updateCustomer(customer);
		return ResponseEntity.ok(customer);
	}

	@DeleteMapping("/deleteCustomer/{customerId}/{token}")
	public ResponseEntity<?> deleteCustomer(@PathVariable String token, @PathVariable int customerId)
			throws CustomerNotFoundException, invalidTokenException {
		ourSessions session = sessions.get(token);
		AdminFacade admin = (AdminFacade) session.getClientFacade();
		admin.deleteCustomer(customerId);
		return ResponseEntity.ok("customer id: " + customerId + "deleted");
	}

	@GetMapping("/getAllCustomers/{token}")
	public ResponseEntity<List<Customer>> getAllCustomers(@PathVariable String token) throws invalidTokenException {
		ourSessions session = sessions.get(token);
		AdminFacade admin = (AdminFacade) session.getClientFacade();
		return ResponseEntity.ok(admin.getAllCustomers());
	}

	@GetMapping("/getOneCustomer/{customerId}/{token}")
	public ResponseEntity<?> getOneCustomer(@PathVariable String token, @PathVariable int customerId)
			throws CustomerNotFoundException, invalidTokenException {
		ourSessions session = sessions.get(token);
		AdminFacade admin = (AdminFacade) session.getClientFacade();
		Customer cust = admin.getCustomerById(customerId);
		System.out.println(cust);
		return ResponseEntity.ok(cust);
	}
	
	@GetMapping("/getCompanyCoupons/{companyId}/{token}")
	public ResponseEntity<?> getCompanyCoupons(@PathVariable String token, @PathVariable int companyId) throws CompanyNotFoundException{
		ourSessions session = sessions.get(token);
		AdminFacade admin = (AdminFacade) session.getClientFacade();
		return ResponseEntity.ok(admin.getCompanyCoupons(companyId));
	}
	
	@GetMapping("/getCustomerCoupons/{customerId}/{token}")
	public ResponseEntity<?> getCustomerCoupons(@PathVariable String token, @PathVariable int customerId) throws CustomerNotFoundException {
		ourSessions session = sessions.get(token);
		AdminFacade admin = (AdminFacade) session.getClientFacade();
		return ResponseEntity.ok(admin.getCustomerCoupons(customerId));
	}

}
