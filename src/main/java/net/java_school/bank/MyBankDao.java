package net.java_school.bank;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public class MyBankDao extends NamedParameterJdbcDaoSupport implements BankDao {
	private static final String SQL_DEPOSIT = 
			"UPDATE account " +
					"SET balance = balance + :amount " +
					"WHERE account_no = :accountNo";

	private static final String SQL_WITHDRAW = 
			"UPDATE account " +
					"SET balance = balance - :amount " +
					"WHERE account_no = :accountNo";

	private static final String SQL_INSERT_ACCOUNT = 
			"INSERT INTO " +
					"account(account_no,name,balance,account_type) " +
					"VALUES(:accountNo,:name,:balance,:kind)";

	private static final String SQL_FIND_ACCOUNT_BY_NO = 
			"SELECT account_no,name,balance,account_type " +
					"FROM account " +
					"WHERE account_no = :accountNo";

	private static final String SQL_FIND_ACCOUNT_BY_NAME = 
			"SELECT account_no,name,balance,account_type " +
					"FROM account " +
					"WHERE name = :name";

	private static final String SQL_ALL_ACCOUNT = 
			"SELECT account_no,name,balance,account_type " +
					"FROM account " +
					"ORDER BY account_no";

	public void addAccount(Account account) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", account.getAccountNo());
		params.put("name", account.getName());
		params.put("balance", account.getBalance());
		params.put("kind", account.getKind());
		getNamedParameterJdbcTemplate().update(SQL_INSERT_ACCOUNT, params);
	}

	public Account findAccount(String accountNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", accountNo);
		return getNamedParameterJdbcTemplate().queryForObject(
				SQL_FIND_ACCOUNT_BY_NO,
				params,
				new RowMapper<Account>() {
					public Account mapRow(ResultSet rs,int rowNum) throws SQLException {
						Account account = new Account();
						account.setAccountNo(rs.getString("account_no"));
						account.setName(rs.getString("name"));
						account.setBalance(rs.getDouble("balance"));
						account.setKind(rs.getString("account_type"));

						return account;
					}
				}
		);
	}

	public List<Account> findAccountByName(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		RowMapper<Account> rowMapper = new AccountRowMapper();
		return getNamedParameterJdbcTemplate().query(SQL_FIND_ACCOUNT_BY_NAME,params,rowMapper);
	}

	protected class AccountRowMapper implements RowMapper<Account> {
		public Account mapRow(ResultSet rs,int rowNum) throws SQLException {
			String accountNo = rs.getString("account_no");
			String name = rs.getString("name");
			double balance = rs.getDouble("balance");
			String kind = rs.getString("account_type");

			Account account = new Account();
			account.setAccountNo(accountNo);
			account.setName(name);
			account.setBalance(balance);
			account.setKind(kind);

			return account;
		}
	}

	public List<Account> getAccounts() {
		RowMapper<Account> rowMapper = new AccountRowMapper();
		return getJdbcTemplate().query(SQL_ALL_ACCOUNT,rowMapper);
	}

	public int deposit(Account account, double amount) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", amount);
		params.put("accountNo", account.getAccountNo());
		return getNamedParameterJdbcTemplate().update(SQL_DEPOSIT, params);
	}

	public int withdraw(Account account,double amount) throws DataAccessException {
		if (account.getKind().equals(Account.NORMAL) && 
				amount > account.getBalance()) {
			throw new InsufficientBalanceException("잔액이 부족합니다.");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", amount);
		params.put("accountNo", account.getAccountNo());
		return getNamedParameterJdbcTemplate().update(SQL_WITHDRAW, params);
	}

	public void transfer(Account from,Account to,double amount) throws DataAccessException {
		withdraw(from, amount);
		deposit(to, amount);
	}
}
