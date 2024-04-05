package net.java_school.config;

import javax.sql.DataSource;

import net.java_school.commons.CommonLogger;
import net.java_school.bank.BankDao;
import net.java_school.bank.BankService;
import net.java_school.bank.BankUi;
import net.java_school.bank.MyBankDao;
import net.java_school.bank.MyBankService;
import net.java_school.bank.MyBankUi;
import net.java_school.commons.BankLogger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class BankConfig {

	@Bean
	public CommonLogger commonLogger() {
		return new CommonLogger();
	}

	@Bean
	public BankLogger bankLogger() {
		return new BankLogger();
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:XE");
		dataSource.setUsername("scott");
		dataSource.setPassword("tiger");
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public BankDao bankDao() {
		MyBankDao bankDao = new MyBankDao();
		bankDao.setDataSource(dataSource());
		return bankDao;
	}

	@Bean
	public BankService bankService() {
		BankService bankService = new MyBankService();
		bankService.setBankDao(bankDao());
		return bankService;
	}

	@Bean
	public BankUi bankUi() {
		BankUi bankUi = new MyBankUi();
		bankUi.setBankService(bankService());
		return bankUi;
	}
}