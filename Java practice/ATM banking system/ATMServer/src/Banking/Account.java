package Banking;
import java.io.Serializable;

public class Account implements Serializable{
	/** Account Balance */
	private double balance;
	/** Account Number */
	private String accountNumber;
	/** Account password */
	private String password;
	
	/**
	 * Account The constructor of the class. Create an account.
	 * @param accNo Account Number
	 * @param pass Account password
	 * @param money Account Balance
	 */
	public Account(String accNo, String pass, double money)
	{
		accountNumber = accNo;
		password = pass;
		balance = money;
	}
	
	/**
	 * Account The constructor of the class. Create an account. The balance is set to 0.
	 * @param accNo Account Number
	 * @param pass Account password
	 */
	public Account(String accNo, String pass)
	{
		accountNumber = accNo;
		password = pass;
		balance = 0;
	}
	
	/**
	 * Receive the balance of account
	 * @return Account Balance
	 */
	public double getBalance()
	{
		return balance;
	}
	
	/**
	 * Get the Account password
	 * @return Account Password
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * Change the account password
	 * @param pass Password to change
	 */
	public void changePassword(String pass)
	{
		password = pass;
	}
	
	/**
	 * Deposit
	 * @param amt Amount to deposit
	 * @return success or failure of deposit
	 */
	public synchronized boolean deposit(double amt)
	{
		if( amt >= 0 )
		{
			balance += amt;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Withdraw
	 * @param amt Amount to withdraw
	 * @return success or failure of deposit
	 */
	public  synchronized boolean withdraw(double amt)
	{
		if( balance >= amt )
		{
			balance -= amt;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Get the account number
	 * @return Account Number
	 */
	public String getAcctNum()
	{
		return accountNumber;
	}
}
