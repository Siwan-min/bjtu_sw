package Banking;
import java.util.ArrayList;
import java.io.Serializable;

public class Customer implements Serializable {

	/**
	 * Customer number
	 */
	private int Id;
	/**
	 * Customer name
	 */
	private String Name;
	/**
	 * Phone number
	 */
	private String Tel;
	/**
	 * Address
	 */
	private String Address;
	
	/**
	 * Array List that stores the Account information.
	 */
	ArrayList<Account> accounts;
	
	/**
	 * Customer The constructor of the class. Create a Customer object. Initialize customer information and account list.
	 * @param id Customer number
	 * @param name Customer name
	 * @param tel Phone number
	 * @param add Address
	 */
	public Customer(int id, String name, String tel, String add)
	{
		Id = id;
		Name = name;
		Tel = tel;
		Address = add;
		
		accounts = new ArrayList<Account>();
	}
	
	/**
	 * Get customer number
	 * @return Customer number
	 */
	public int getId()
	{
		return Id;
	}
	
	/**
	 * Get the customer's phone number
	 * @return Customer;s phone number
	 */
	public String getTel()
	{
		return Tel;
	}
	
	/**
	 * Get the address of the customer
	 * @return Customer's address
	 */
	public String getAddress()
	{
		return Address;
	}
	
	/**
	 * Get Customer name
	 * @return Customer name
	 */
	public String getName()
	{
		return Name;
	}
	
	
	/**
	 * Change customer phone number
	 * @param tel Phone number to change
	 */
	public void setTel(String tel)
	{
		Tel = tel;
	}
	
	/**
	 * Change customer's phone number
	 * @param add Address to change
	 */
	public void setAddress(String add)
	{
		Address = add;
	}
	
	/**
	 * Add an account.
	 * @param account Account object to add
	 */
	public void addAccount(Account account)
	{
			accounts.add(account);
	}
	
	/**
	 * Delete account
	 * @param account The account object to delete.
	 */
	public void delAccount(Account account)
	{
		accounts.remove(account);
	}
	
	/**
	 * Get account object through account list index
	 * @param index Index of account list
	 * @return Account object
	 */
	public Account getAccount(int index)
	{
		return accounts.get(index);
	}
	
	/**
	 * Get account object through account number
	 * @param accnum Account Number
	 * @return Account object
	 */
	public Account getAccount(String accnum)
	{
		for(int i=0; i<accounts.size(); i++)
		{
			if(accounts.get(i).getAcctNum().equals(accnum))
			{
				return accounts.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Get the number of owned accounts
	 * @return Owned account number
	 */
	public int getNumOfAccounts()
	{
		return accounts.size();
	}
	

}
