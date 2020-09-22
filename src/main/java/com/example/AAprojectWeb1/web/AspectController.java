package com.example.AAprojectWeb1.web;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.AAprojectWeb1.exceptions.invalidTokenException;
import com.example.AAprojectWeb1.exceptions.timeOutExeption;

@Component
@Aspect
public class AspectController {
	
	private Map<String, ourSessions> sessions;
	
	
	public AspectController(Map<String, ourSessions> sessions) {
		super();
		this.sessions = sessions;
	}

	public ResponseEntity<?> AOPtoken(ProceedingJoinPoint point) throws Throwable{ //ProceedingJoinPoint - It is an aop variable that gives an instruction that the test is fine and can be given to the method that implements it to continue
		String token = (String) point.getArgs()[0];
		ourSessions session = sessions.get(token);
		if (session != null) {
			if(System.currentTimeMillis() - session.getLastAcssse() > TimeUnit.MINUTES.toMillis(30)) {
				sessions.remove(token);
				throw new timeOutExeption();		
		}
			session.setLastAcssse(System.currentTimeMillis());
			return (ResponseEntity<?>) point.proceed(); //point.proceed - This means that the test in the aop has been successfully proceeded to continue the process
			
	}else {
		throw new invalidTokenException();	
	}
	}
	
	@Around("execution(* com.example.AAprojectWeb1.web.AdminController.*(..) )")
	public ResponseEntity<?> runAdmin(ProceedingJoinPoint point) throws Throwable{ 
		return AOPtoken(point);
	}
	
	@Around("execution(* com.example.AAprojectWeb1.web.CompanyController.*(..) )")
	public ResponseEntity<?> runCompany(ProceedingJoinPoint point) throws Throwable{ 
		return AOPtoken(point);
	}
	
	@Around("execution(* com.example.AAprojectWeb1.web.CustomerController.*(..) )")
	public ResponseEntity<?> runCustomer(ProceedingJoinPoint point) throws Throwable{ 
		return AOPtoken(point);
	}
}
