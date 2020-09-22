package com.example.AAprojectWeb1.facades;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.AAprojectWeb1.beans.Category;
import com.example.AAprojectWeb1.beans.Company;
import com.example.AAprojectWeb1.beans.Coupon;
import com.example.AAprojectWeb1.exceptions.CompanyNotFoundException;
import com.example.AAprojectWeb1.exceptions.CouponDoesNotBelongToYouException;
import com.example.AAprojectWeb1.exceptions.CouponNotFoundException;
import com.example.AAprojectWeb1.exceptions.TitleAlreadyUsedException;
import com.example.AAprojectWeb1.exceptions.idChangeException;
import com.example.AAprojectWeb1.repository.CompanyRepository;
import com.example.AAprojectWeb1.repository.CouponRepository;
import com.example.AAprojectWeb1.repository.CustomerRepository;
import com.example.AAprojectWeb1.repository.shoppingCartRepository;

@Service
@Scope("prototype")
public class CompanyFacade extends ClientFacade {

	private int companyId;

	public CompanyFacade(CompanyRepository compRepo, CouponRepository coupRepo, CustomerRepository custRepo,
			shoppingCartRepository shopRepo) {
		super(compRepo, coupRepo, custRepo, shopRepo);

	}

	public boolean login(String email, String password) {
		if (compRepo.existsByEmailAndPassword(email, password)) {// asks if the company exists by email and password
			this.companyId = compRepo.findIdByEmailAndPassword(email, password); // Checks that the email and password I
																					// got is equal to the email and
																					// password I have
			return true;
		}

		return false;
	}

	public void addCoupon(Coupon coupon) throws TitleAlreadyUsedException {
		Company c = getOneCompany(companyId);
		for (Coupon coup : c.getCuopons()) {
			if ((coup.getTitle().equals(coupon.getTitle()))) {// Checks if the title is the same as an existing title in
																// our company's coupons
				throw new TitleAlreadyUsedException();
			}

		}
		coupon.setCompanyId(companyId); // if the title not the same you can set the company
		coupRepo.save(coupon);// and add the coupon

	}

	public void updateCoupon(Coupon coupon) throws CouponNotFoundException, idChangeException {
		if (coupRepo.existsById(coupon.getCouponId())) {
			for (Coupon coup : coupRepo.findAll()) {
				if (coup.getCouponId() == coupon.getCouponId() && coup.getCompanyId() == coupon.getCompanyId()) {// The
																													// coupon
																													// id
																													// and
																													// company
																													// id
																													// cannot
																													// be
																													// updated
					coupRepo.save(coupon);
					return;
				}
				throw new idChangeException();
			}
		} else {
			throw new CouponNotFoundException();
		}
	}

	public void deleteCoupon(int couponId) throws CouponDoesNotBelongToYouException, CouponNotFoundException {
		Coupon coup = coupRepo.findById(couponId).orElseThrow(CouponNotFoundException::new);
		if (this.companyId == coup.getCompanyId()) {// Checks that each company can only delete its coupons
			coupRepo.deleteCustomerCoupons(couponId);// Deletes the history from the linked table
			coupRepo.deleteById(couponId);

		} else {
			throw new CouponDoesNotBelongToYouException();
		}
	}

	public List<Coupon> getCompanyCoupons() throws CompanyNotFoundException {
		Company c = getCompanyDetails();
		return c.getCuopons();
	}

	public List<Coupon> getCompanyCouponsByCategory(Category categoryType) {
		Company c = getOneCompany(companyId);
		List<Coupon> couponCategory = new ArrayList<Coupon>();
		for (Coupon coup : c.getCuopons()) {
			if (coup.getCategoryType().equals(categoryType)) {
				couponCategory.add(coup);
			}
		}
		return couponCategory;
	}

	public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) {
		Company c = getOneCompany(companyId);
		List<Coupon> couponMaxPrice = new ArrayList<Coupon>();
		for (Coupon coup : c.getCuopons()) {
			if (coup.getPrice() <= (maxPrice)) {
				couponMaxPrice.add(coup);

			}
		}
		return couponMaxPrice;

	}

	public Company getCompanyDetails() throws CompanyNotFoundException {
		return compRepo.findById(companyId).orElseThrow(CompanyNotFoundException::new);
	}

	public Company getOneCompany(int companyId) { // help method
		return compRepo.findById(companyId).orElse(null);

	}

	public Coupon getOneCoupon(int couponId) {
		return coupRepo.findById(couponId).orElse(null);
	}

}