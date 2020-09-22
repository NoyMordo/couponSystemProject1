package com.example.AAprojectWeb1.repository;



import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.AAprojectWeb1.beans.Company;
import com.example.AAprojectWeb1.beans.Customer;


public interface CompanyRepository extends JpaRepository<Company, Integer> {

	public boolean existsByEmailAndPassword (String email, String password);
	@Query("select c.id from Company c where c.password = :password and c.email = :email") //hql
	public int findIdByEmailAndPassword(String email, String password);
	
	public Company findByName(String name);
	
//	@Transactional
//	@Modifying
//	@Query(nativeQuery = true, value = "SELECT c.customer_id as customerId, c.email, c.first_name as firstName, c.last_name as lastName, c.password, c.shopping_cart_id as shoppingCart FROM customers c join coupons_customers c2 on c.customer_id = c2.customers_customer_id join coupons c3 on c3.coupon_id = c2.coupons_coupon_id where c3.company_id = ?")
//	//We received the company's customer list through the linking tables We took the list of all the customers we consolidated the list of the linking table and made a third consolidation because the company ID only
//	//exists in the coupons and because of this we had to go through the first 2 tables
//	//update the total price in the shopping cart table
//	public List<Customer> companyCustomers (int companyId);
	
}

