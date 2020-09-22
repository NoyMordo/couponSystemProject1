package com.example.AAprojectWeb1.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.AAprojectWeb1.beans.Customer;
import com.example.AAprojectWeb1.beans.shoppingCart;
import com.google.common.base.Optional;

public interface shoppingCartRepository extends JpaRepository<shoppingCart, Long> {
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value= "delete from shopping_cart_coupons where coupons_coupon_id = ?") //delete this company coupon from cart
	void deleteShoppingCartCoupons(int couponId);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update shopping_cart c1 set c1.total_price=IFNULL((select sum(prices) from ( select price as prices from coupons c2 where c2.coupon_id in (select coupons_coupon_id from shopping_cart_coupons c3 where c3.shopping_cart_id=c1.id) ) tmp),0) where c1.id > 0")
	void updateCartTotal( ); //delete company

}