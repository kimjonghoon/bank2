# bank2
## Spring Transaction example

Connect to the Oracle scott account and create the following table.
To test Spring transactions, added the constraint of less than 3000 to the balance column.

	CREATE TABLE account (
	  account_no VARCHAR2(20),
	  name VARCHAR2(21),
	  balance NUMBER,
	  account_type VARCHAR2(20),
	  CONSTRAINT pk_account PRIMARY KEY(account_no),
	  CONSTRAINT ck_account CHECK(balance &lt; 3000)
	);

## How to run

mvn compile exec:java -Dexec.mainClass=net.java_school.bank.MyBankUi