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

import com.example.AAprojectWeb1.beans.Category;
import com.example.AAprojectWeb1.beans.Coupon;
import com.example.AAprojectWeb1.exceptions.CompanyNotFoundException;
import com.example.AAprojectWeb1.exceptions.CouponDoesNotBelongToYouException;
import com.example.AAprojectWeb1.exceptions.CouponNotFoundException;
import com.example.AAprojectWeb1.exceptions.TitleAlreadyUsedException;
import com.example.AAprojectWeb1.exceptions.idChangeException;
import com.example.AAprojectWeb1.exceptions.invalidTokenException;
import com.example.AAprojectWeb1.facades.AdminFacade;
import com.example.AAprojectWeb1.facades.ClientFacade;
import com.example.AAprojectWeb1.facades.CompanyFacade;

@RestController
@RequestMapping("company")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {

	private Map<String, ourSessions> sessions;

	public CompanyController(Map<String, ourSessions> sessions) {
		super();
		this.sessions = sessions;
	}

	@PostMapping("/addCoupon/{token}")
	public ResponseEntity<?> addCoupon(@PathVariable String token, @RequestBody Coupon coupon)
			throws TitleAlreadyUsedException, invalidTokenException {
		ourSessions session = sessions.get(token);
		CompanyFacade company = (CompanyFacade) session.getClientFacade();
		company.addCoupon(coupon);
		return ResponseEntity.ok(coupon);
	}

	@PutMapping("/updateCoupon/{token}")
	public ResponseEntity<?> updateCoupon(@PathVariable String token, @RequestBody Coupon coupon)
			throws CouponNotFoundException, idChangeException, invalidTokenException {
		ourSessions session = sessions.get(token);
		CompanyFacade company = (CompanyFacade) session.getClientFacade();
		company.updateCoupon(coupon);
		return ResponseEntity.ok(coupon);
	}

	@DeleteMapping("/deleteCoupon/{couponId}/{token}")
	public ResponseEntity<?> deleteCoupon(@PathVariable String token, @PathVariable int couponId)
			throws CouponDoesNotBelongToYouException, CouponNotFoundException, invalidTokenException {
		ourSessions session = sessions.get(token);
		CompanyFacade company = (CompanyFacade) session.getClientFacade();
		company.deleteCoupon(couponId);
		return ResponseEntity.ok("coupon with id: " + couponId + "deleted");
	}

	@GetMapping("/getCompanyCoupons/{token}")
	public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable String token)
			throws CompanyNotFoundException, invalidTokenException {
		ourSessions session = sessions.get(token);
		CompanyFacade company = (CompanyFacade) session.getClientFacade();
		return ResponseEntity.ok(company.getCompanyCoupons());
	}

	@PostMapping("/getCompanyCouponsByCategory/{token}")
	public ResponseEntity<List<Coupon>> getCompanyCouponsByCategory(@PathVariable String token,
			@RequestBody Category categoryType) throws invalidTokenException {
		ourSessions session = sessions.get(token);
		CompanyFacade company = (CompanyFacade) session.getClientFacade();
		return ResponseEntity.ok(company.getCompanyCouponsByCategory(categoryType));
	}

	@GetMapping("/getCompanyCouponsByMaxPrice/{maxPrice}/{token}")
	public ResponseEntity<List<Coupon>> getCompanyCouponsByMaxPrice(@PathVariable String token,
			@PathVariable double maxPrice) throws invalidTokenException {
		ourSessions session = sessions.get(token);
		CompanyFacade company = (CompanyFacade) session.getClientFacade();
		return ResponseEntity.ok(company.getCompanyCouponsByMaxPrice(maxPrice));
	}

	@GetMapping("/companyDetails/{token}")
	public ResponseEntity<?> getCompanyDetails(@PathVariable String token)
			throws CompanyNotFoundException, invalidTokenException {
		ourSessions session = sessions.get(token);
		CompanyFacade company = (CompanyFacade) session.getClientFacade();
		return ResponseEntity.ok(company.getCompanyDetails());
	}

	@GetMapping("/oneCoupon/{couponId}/{token}")
	public ResponseEntity<?> getOneCoupon(@PathVariable String token, @PathVariable int couponId) {
		ourSessions session = sessions.get(token);
		CompanyFacade company = (CompanyFacade) session.getClientFacade();
		return ResponseEntity.ok(company.getOneCoupon(couponId));
	}
}
