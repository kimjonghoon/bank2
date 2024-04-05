package net.java_school.bank;

import java.io.*;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.java_school.config.BankConfig;

public class MyBankUi implements BankUi {
	public static final String ENTER = System.getProperty("line.separator");
	private BankService bankService;

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	// 은행업무를 시작한다.
	@Override
	public void startWork() throws Exception {
		String menu = null;

		do {
			System.out.println(" ** 메뉴를 선택하세요 ** ");
			System.out.println(" 1 *** 계좌등록  ");
			System.out.println(" 2 *** 계좌목록  ");
			System.out.println(" 3 *** 입금  ");
			System.out.println(" 4 *** 출금  ");
			System.out.println(" 5 *** 이체  ");
			System.out.println(" q *** 종료  ");
			System.out.print("]");

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				menu = br.readLine();

				if (menu.equals("1")) {
					// 계좌등록
					System.out.print("계좌번호를 입력하세요]");
					String accountNo = br.readLine();
					System.out.print("이름을 입력하세요]");
					String name = br.readLine();
					System.out.print("일반계좌는 그냥 엔터, 마이너스 계좌는 2를 입력하세요]");
					String kind = br.readLine();
					if (kind != null && kind.equals("2")) {
						bankService.addAccount(new Account(accountNo, name, Account.MINUS));
					} else {
						bankService.addAccount(new Account(accountNo, name, Account.NORMAL));
					}
				} else if ( menu.equals("2") ) {
					// 계좌목록
					List<Account> accounts = bankService.getAccounts();
					int totalAccount = accounts.size();
					for ( int i = 0; i < totalAccount; i++ ) {
						System.out.println(accounts.get(i));
					}
				} else if (menu.equals("3")) {
					//입금
					System.out.print("계좌번호를 입력하세요]");
					String accountNo = br.readLine();
					Account account = bankService.findAccount(accountNo);
					if (account == null) throw new InvalidAccountException("계좌번호를 잘못 입력했습니다.");
					System.out.print("입금액을 입력하세요]");
					double amount = Double.parseDouble(br.readLine());
					bankService.deposit(account, amount);
				} else if (menu.equals("4")) {
					// 출금
					System.out.print("계좌번호를 입력하세요]");
					String accountNo = br.readLine();
					Account account = bankService.findAccount(accountNo);
					if (account == null) throw new InvalidAccountException("계좌번호를 잘못 입력했습니다.");
					System.out.print("출금액을 입력하세요]");
					double amount = Double.parseDouble(br.readLine());
					bankService.withdraw(account, amount);
				} else if (menu.equals("5")) {
					// 이체(같은 은행 계좌끼리)
					System.out.println("FROM 계좌에서 TO 계좌로 송금합니다.");
					System.out.print("FROM 계좌번호를 입력하세요]");
					String accountNo = br.readLine();
					Account from = bankService.findAccount(accountNo);
					if (from == null) throw new InvalidAccountException("계좌번호를 잘못 입력했습니다.");
					System.out.print("TO 계좌번호를 입력하세요]");
					accountNo = br.readLine();
					Account to = bankService.findAccount(accountNo);
					if (to == null) throw new InvalidAccountException("계좌번호를 잘못 입력했습니다.");
					System.out.print("이체금액을 입력하세요]");
					double amount = Double.parseDouble(br.readLine());
					bankService.transfer(from, to, amount);
				}
				System.out.println();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} while ( !menu.equals("q") );
	}

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BankConfig.class);
		//ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		MyBankUi ui = (MyBankUi) ctx.getBean("bankUi");
		try {
			ui.startWork();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("프로그램이 종료되었습니다.");
	}
}
