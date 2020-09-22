package com.example.AAprojectWeb1.web;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AAprojectWeb1.beans.Category;
import com.example.AAprojectWeb1.beans.Coupon;
import com.example.AAprojectWeb1.exceptions.CouponAlreadyExsistException;
import com.example.AAprojectWeb1.exceptions.CouponNotFoundException;
import com.example.AAprojectWeb1.exceptions.CustomerNotFoundException;
import com.example.AAprojectWeb1.exceptions.TimeOverException;
import com.example.AAprojectWeb1.exceptions.invalidTokenException;
import com.example.AAprojectWeb1.facades.ClientFacade;
import com.example.AAprojectWeb1.facades.CompanyFacade;
import com.example.AAprojectWeb1.facades.CustomerFacade;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

private Map<String, ourSessions> sessions;
	

	public CustomerController(Map<String, ourSessions> sessions) {
		super();
		this.sessions = sessions;
	}
	
	@PostMapping("/purchaseCoupon/{token}")
	public ResponseEntity<?> purchaseCoupon(@PathVariable String token, @RequestBody Coupon coupon) throws CouponAlreadyExsistException,
	CouponNotFoundException, TimeOverException, CustomerNotFoundException, invalidTokenException{
		ourSessions session = sessions.get(token);
			CustomerFacade customer = (CustomerFacade) session.getClientFacade();
			customer.CouponPurchase(coupon);
		return ResponseEntity.ok(coupon);
		}
	
	@GetMapping("/getCusomerCoupons/{token}")
	public ResponseEntity<Set<Coupon>> getCusomerCoupons (@PathVariable String token) throws CustomerNotFoundException, invalidTokenException{
		ourSessions session = sessions.get(token);
			CustomerFacade customer = (CustomerFacade) session.getClientFacade();
		return ResponseEntity.ok(customer.getCustomerCoupons());
		}

	
	@PostMapping("/getCusomerCouponsByCategory/{token}")
	public ResponseEntity<List<Coupon>> getCusomerCouponsByCategory(@PathVariable String token,@RequestBody Category categoryType) throws invalidTokenException{
		ourSessions session = sessions.get(token);
			CustomerFacade customer = (CustomerFacade) session.getClientFacade();
			return ResponseEntity.ok(customer.getCustomerCouponsByCategory(categoryType));
		}
	
	@GetMapping("/getCusomerCouponsByMaxPrice/{maxPrice}/{token}")
	public ResponseEntity<List<Coupon>> getCusomerCouponsByMaxPrice (@PathVariable String token, @PathVariable double maxPrice) throws invalidTokenException {
		ourSessions session = sessions.get(token);
			CustomerFacade customer = (CustomerFacade) session.getClientFacade();
			return ResponseEntity.ok(customer.getCustomerCouponsByMaxPrice(maxPrice));
		}
	
	@GetMapping("/getCustomerDetails/{token}")
	public ResponseEntity<?> getCustomerDetails(@PathVariable String token) throws CustomerNotFoundException, invalidTokenException{
		ourSessions session = sessions.get(token);
			CustomerFacade customer = (CustomerFacade) session.getClientFacade();
		return ResponseEntity.ok(customer.getCustomerDetails());
		}
	
	@PostMapping("/addCouponToCart/{token}")
	public ResponseEntity<?> addCouponToCart(@PathVariable String token, @RequestBody Coupon coupon){
		ourSessions session = sessions.get(token);
		CustomerFacade customer = (CustomerFacade) session.getClientFacade();
		customer.addCouponToCart(coupon);
		return ResponseEntity.ok(coupon);
		
	}
	
	@DeleteMapping("/removeCouponFromCart/{couponId}/{token}")
	public ResponseEntity<?> removeCouponFromCart(@PathVariable String token, @PathVariable int couponId) throws CouponNotFoundException, CustomerNotFoundException{
		ourSessions session = sessions.get(token);
		CustomerFacade customer = (CustomerFacade) session.getClientFacade();
		customer.removeCouponFromCart(couponId);
		return ResponseEntity.ok("coupon deleted from cart");
		
	}
	
	@GetMapping("/getAllCoupons/{token}")
	public ResponseEntity<?> getAllCoupons(@PathVariable String token){
		ourSessions session = sessions.get(token);
		CustomerFacade customer = (CustomerFacade) session.getClientFacade();
		return ResponseEntity.ok(customer.getAllCoupons());
	}

	@GetMapping("/getOneCoupon/{couponId}/{token}")
	public ResponseEntity<?> getOneCoupon (@PathVariable String token, @PathVariable int couponId) throws CouponNotFoundException{
		ourSessions session = sessions.get(token);
		CustomerFacade customer = (CustomerFacade) session.getClientFacade();
		return ResponseEntity.ok(customer.getOneCoupon(couponId));
	}
	
	@GetMapping("/getCouponCart/{token}")
	public ResponseEntity<?> getCouponCart(@PathVariable String token) throws CustomerNotFoundException{
		ourSessions session = sessions.get(token);
		CustomerFacade customer = (CustomerFacade) session.getClientFacade();
		return ResponseEntity.ok(customer.getCouponCart());
	}
	 
}
