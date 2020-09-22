package com.example.AAprojectWeb1.facades;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.AAprojectWeb1.beans.Category;
import com.example.AAprojectWeb1.beans.Coupon;
import com.example.AAprojectWeb1.beans.Customer;
import com.example.AAprojectWeb1.beans.shoppingCart;
import com.example.AAprojectWeb1.exceptions.CouponAlreadyExsistException;
import com.example.AAprojectWeb1.exceptions.CouponNotFoundException;
import com.example.AAprojectWeb1.exceptions.CustomerNotFoundException;
import com.example.AAprojectWeb1.exceptions.TimeOverException;
import com.example.AAprojectWeb1.repository.CompanyRepository;
import com.example.AAprojectWeb1.repository.CouponRepository;
import com.example.AAprojectWeb1.repository.CustomerRepository;
import com.example.AAprojectWeb1.repository.shoppingCartRepository;


@Service
@Scope("prototype")
public class CustomerFacade extends ClientFacade {

	
	public CustomerFacade(CompanyRepository compRepo, CouponRepository coupRepo, CustomerRepository custRepo,shoppingCartRepository shopRepo) {
		super(compRepo, coupRepo, custRepo, shopRepo);

	}

	private int customerId;

	public boolean login(String email, String password) {
		if (custRepo.existsByEmailAndPassword(email, password)) {// asks if the customer exists by email and password
			this.customerId = custRepo.findIdByEmailAndPassword(email, password).getCustomerId();//Checks that the email and password I got is equal to the email and password I have
			return true;
		}

		return false;
	}
	
	public void CouponPurchase(Coupon coupon)
			throws CouponAlreadyExsistException, CouponNotFoundException, TimeOverException, CustomerNotFoundException {
		Customer c = getCustomerDetails();
		for (Coupon coup : c.getCoupons()) {
			if ((coupon.getCouponId() == coup.getCouponId())) {
				throw new CouponAlreadyExsistException();// cannot buy the same coupon more than once
			}
		}

		if (coupon.getAmount() == 0) {
			throw new CouponNotFoundException(); // The coupon cannot be purchased if the amount is 0
		}

		if (coupon.getEndDate().before(new Date(Calendar.getInstance().getTimeInMillis()))) {
			throw new TimeOverException();// Checking expiration date(You can't buy if the date has passed)

		}
		c.getCoupons().add(coupon); //If all conditions are successful you can add a purchase
		custRepo.save(c);
		coupon.setAmount(coupon.getAmount() - 1);// remove one of the quantity
//		coupon.getCustomers().add(c); //add the customer-coupon link to the linking table
		coupRepo.save(coupon); // and update the list
	}

	public Set<Coupon> getCustomerCoupons() throws CustomerNotFoundException {
		Customer c = getCustomerDetails();
		return c.getCoupons();
	
	}

	public List<Coupon> getCustomerCouponsByCategory(Category categoryType) {
		Customer c = getOneCustomer(customerId);
		List<Coupon> couponsCategory = new ArrayList<Coupon>();
		for (Coupon coupon : c.getCoupons()) {
			if (coupon.getCategoryType().equals(categoryType)) {
				couponsCategory.add(coupon);
			}
		}
		return couponsCategory;

	}

	public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) {
		Customer c = getOneCustomer(customerId);
		List<Coupon> couponsMaxPrice = new ArrayList<Coupon>();
		for (Coupon coupon : c.getCoupons()) {
			if (coupon.getPrice() <= (maxPrice)) {
				couponsMaxPrice.add(coupon);
			}
		}
		return couponsMaxPrice;

	}

	public Customer getCustomerDetails() throws CustomerNotFoundException {
		return custRepo.findById(customerId).orElseThrow(CustomerNotFoundException::new);
	}

	public Customer getOneCustomer(int customerId) {
		return custRepo.findById(customerId).orElse(null);
	}

	public Coupon getOneCoupon(int id) throws CouponNotFoundException {
		return coupRepo.findById(id).orElseThrow(CouponNotFoundException::new);
	}
	
	public void addCouponToCart(Coupon coupon) {
		Customer cust = custRepo.findById(customerId).get();
		shoppingCart shop = cust.getShoppingCart();
		if(!shopRepo.findById(shop.getId()).isPresent()) {//is present - if is inside
			shopRepo.save(shop);
		}
		shop.addCoupon(coupon);
		custRepo.save(cust);
	}
	
	public void removeCouponFromCart(int couponId) throws CouponNotFoundException, CustomerNotFoundException {
		Customer cust = custRepo.findById(customerId).orElseThrow(CustomerNotFoundException ::new);
		Coupon coup = coupRepo.findById(couponId).orElseThrow(CouponNotFoundException :: new);
		cust.getShoppingCart().getCoupons().remove(coup);
		cust.getShoppingCart().setTotalPrice(cust.getShoppingCart().getTotalPrice() - coup.getPrice()); 
		shoppingCart c = cust.getShoppingCart();
		shopRepo.save(c);
		custRepo.save(cust);
	}

	public List<Coupon> getAllCoupons (){//help method 
		return coupRepo.findAll();
	}
	
	public shoppingCart getCouponCart() throws CustomerNotFoundException{
		Customer cust = custRepo.findById(customerId).orElseThrow(CustomerNotFoundException :: new);
		return cust.getShoppingCart();
	}
	
	
	
	
}
