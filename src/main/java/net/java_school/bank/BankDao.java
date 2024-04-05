package net.java_school.bank;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface BankDao {

	public void addAccount(Account account) throws DataAccessException; 

	public Account findAccount(String accountNo) throws DataAccessException;

	public List<Account> findAccountByName(String name) throws DataAccessException;

	public List<Account> getAccounts() throws DataAccessException;

	public int deposit(Account account, double amount) throws DataAccessException;

	public int withdraw(Account account, double amount) throws DataAccessException;

	public void transfer(Account from, Account to, double amount) throws DataAccessException;
}
