package com.example.AAprojectWeb1.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AAprojectWeb1.beans.Company;
import com.example.AAprojectWeb1.beans.Coupon;



public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	
	
	public List<Coupon> findCouponsByCompanyId(int companyId);
	

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value= "delete from customers_coupons where coupons_coupon_id = ?") //delete company
	void deleteCustomerCoupons(int couponId);
	
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value= "delete from coupons where company_id = ?") //delete company
	void deleteCompanyCoupons(int companyId);
	
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value= "delete from shopping_cart_coupons where coupons_coupon_id = ?") //delete this company coupon from cart
	void deleteShoppingCartCoupons(int couponId);
	
	
	
	
}
