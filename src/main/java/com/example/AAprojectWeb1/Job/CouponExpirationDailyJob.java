package com.example.AAprojectWeb1.Job;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.AAprojectWeb1.beans.Coupon;
import com.example.AAprojectWeb1.repository.CouponRepository;

@Component
public class CouponExpirationDailyJob extends Thread {

	@Autowired
	private CouponRepository coupRepo;

	private boolean quit = false;

	public CouponExpirationDailyJob() {

	}

	@Override
	public void run() {
		while (this.quit == false) {
			List<Coupon> coupons = coupRepo.findAll();
			for (Coupon coupon : coupons) {
				
				synchronized (coupons) {
					if (coupon.getEndDate().before(new Date(Calendar.getInstance().getTimeInMillis()))) {
						
						coupRepo.deleteCustomerCoupons(coupon.getCouponId());
						coupRepo.deleteById(coupon.getCouponId());
					}
				}
			}
			try {
				 sleep(1000*60*60*60*24);
			} catch (InterruptedException e) {
				e.getMessage();

			}

		}
	}

	public synchronized void stopJob() {
		this.quit = true;
		try {
			sleep(3000);
		} catch (InterruptedException e) {

		}
		interrupt();
	}
}
