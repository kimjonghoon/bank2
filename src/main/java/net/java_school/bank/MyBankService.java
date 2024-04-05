package net.java_school.bank;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MyBankService implements BankService {
	private BankDao bankDao;

	public void setBankDao(BankDao bankDao) {
		this.bankDao = bankDao;
	}

	public void addAccount(Account account) throws DataAccessException {
		bankDao.addAccount(account);
	}

	public Account findAccount(String accountNo) throws DataAccessException {
		return bankDao.findAccount(accountNo);
	}

	public List<Account> findAccountByName(String name) throws DataAccessException {
		return bankDao.findAccountByName(name);
	}

	public List<Account> getAccounts() throws DataAccessException {
		return bankDao.getAccounts();
	}

	public int deposit(Account account, double amount) throws DataAccessException {
		return bankDao.deposit(account, amount);
	}

	public int withdraw(Account account, double amount) throws DataAccessException {
		return bankDao.withdraw(account, amount);
	}

	@Transactional
	public void transfer(Account from, Account to, double amount) throws DataAccessException {
		try {
			int check = bankDao.withdraw(from, amount);
			if (check < 1) throw new RuntimeException("출금 실패!");
			check = bankDao.deposit(to, amount);
			if (check < 1) throw new RuntimeException("입금 실패!"); 
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
