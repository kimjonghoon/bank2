package net.java_school.bank;

public class Account {
  private String accountNo;
  private String name;
  private double balance;
  private String kind;

  static final String NORMAL = "NORMAL";
  static final String MINUS = "MINUS";

  public Account() {}
    
  public Account(String accountNo, String name, String kind) {
    this.accountNo = accountNo;
    this.name = name;
    this.kind = kind;
  }

  public Account(String accountNo, String name, double balance, String kind) {
    this.accountNo = accountNo;
    this.name = name;
    this.balance = balance;
    this.kind = kind;
  }
    
  public String getAccountNo() {
    return accountNo;
  }
  
  public void setAccountNo(String accountNo) {
    this.accountNo = accountNo;
  }
    
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public double getBalance() {
    return balance;
  }
  
  public void setBalance(double balance) {
    this.balance = balance;
  }
  
  public String getKind() {
	  return kind;
  }
  public void setKind(String kind) {
	  this.kind = kind;
  }
    
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
        
    sb.append(accountNo);       
    sb.append("|");
    sb.append(name);
    sb.append("|");
    sb.append(balance);
    sb.append("|");
    sb.append(kind);
        
    return sb.toString();
  }
}