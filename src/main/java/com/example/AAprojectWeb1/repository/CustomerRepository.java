package com.example.AAprojectWeb1.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AAprojectWeb1.beans.Coupon;
import com.example.AAprojectWeb1.beans.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	public boolean existsByEmailAndPassword (String email, String password);
	
	@Query("select c from Customer c where c.email = :email and c.password = :password")
	public Customer findIdByEmailAndPassword(String email, String password);
	
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete from projectspring1.customers_coupons"
			+ " where customers_customer_id = ?")
	void deletePurchesByCustomerId (int customerId);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "SELECT * FROM customers c join customers_coupons c2 on c.customer_id = c2.customers_customer_id join coupons c3 on c3.coupon_id = c2.coupons_coupon_id where c3.company_id = ?")
	//We received the company's customer list through the linking tables We took the list of all the customers we consolidated the list of the linking table and made a third consolidation because the company ID only
	//exists in the coupons and because of this we had to go through the first 2 tables
	//update the total price in the shopping cart table
	public List<Customer> companyCustomers (int companyId);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "SELECT * FROM customers c join shopping_cart_coupons c2 on c.shopping_cart_id = c2.shopping_cart_id join coupons c3 on c3.coupon_id = c2.coupons_coupon_id where c3.company_id = ?")
	public List<Customer> shoppingCartCustomers (int companyId);
	

	
}
