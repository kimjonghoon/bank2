package net.java_school.commons;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;

import net.java_school.bank.Account;

import org.slf4j.Logger;

@Aspect
public class CommonLogger {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@AfterReturning("execution(* net.java_school.bank.BankService.deposit(..))")
	public void deposit1Log(JoinPoint point) {
		Object[] a = point.getArgs();
		Account account = (Account) a[0];
		Double amount = (Double) a[1];
		String typeName = point.getSignature().getDeclaringTypeName();
		String methodName = point.getSignature().getName();
		logger.debug("{}.{}| AccountNo: {}, Amount: {}", typeName, methodName, account.getAccountNo(), amount);
	}

	@AfterReturning("execution(* withdraw(..))")
	public void withdrawLog(JoinPoint point) {
		Object[] a = point.getArgs();
		Account account = (Account) a[0];
		Double amount = (Double) a[1];
		String typeName = point.getSignature().getDeclaringTypeName();
		String methodName = point.getSignature().getName();
		logger.debug("{}.{}| AccountNo: {}, Amount: {}", typeName, methodName, account.getAccountNo(), amount);
	}

	/*
	@AfterReturning("execution(* net.java_school.bank.MyBank.withdraw(..)) && args(accountNo, amount)")
	public void withdrawLog(String accountNo, double amount) {
		logger.debug("WITHDRAW|{}|{}", accountNo, amount); 
	}
	*/

}