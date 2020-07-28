package Banking;

import java.util.ArrayList;
import java.io.Serializable;

public class Bank implements Serializable{
	
	/**
	 * Array List for storing Customer information
	 */
	ArrayList<Customer> customers;
	
	/**
	 * The Bank class's constructor. Initialize Customer list.
	 */
	public Bank()
	{
		customers = new ArrayList<Customer>();
	}
	
	/**
	 * Customer Add
	 * @param id Customer number
	 * @param name name
	 * @param tel Phone number
	 * @param address address
	 * @return Customer success or failure of addition. Returns false if duplicate Customer ID exists.
	 */
	public boolean addCustomer(int id, String name, String tel, String address)
	{
		boolean exist = false;
		
		for(int i=0; i<customers.size(); i++)
		{
			if(customers.get(i).getId() == id)
			{
				exist = true;
				break;
			}
		}
		if(exist)
			return false;
		else
		{
			customers.add( new Customer(id,name,tel,address) );
			return true;
		}
	}
	
	/**
	 * Customer delete.
	 * @param customer The Customer object to delete.
	 */
	public void delCustomer(Customer customer)
	{
		customers.remove(customer);
	}
	
	/**
	 * Receive the number of customers in the bank.
	 * @return Total number of customers in the bank.
	 */
	public int getNumOfCustomers()
	{			
		return customers.size();
	}

	/**
	 * Get the number of accounts in the bank.
	 * @return Total number of bank accounts
	 */
	public int getNumOfAccounts()
	{			
		int sum = 0;
		for(int i=0; i < customers.size(); i++)
		{
			sum+=customers.get(i).getNumOfAccounts();
		}
		return sum;
	}
	
	/**
	 * Customer Get objects.
	 * @param index The index number of the ArrayList.
	 * @return index Customer object
	 */
	public Customer getCustomer(int index)
	{
		return customers.get(index);
	}
	
	/**
	 * Account Get object
	 * @param accnum Account number
	 * @return The object of the account with that account number
	 */
	public Account getAccount(String accnum)
	{
		for(int i=0; i < customers.size(); i++)
		{
			for(int j=0; j< customers.get(i).getNumOfAccounts(); j++)
			{
				if(customers.get(i).getAccount(j).getAcctNum().equals(accnum))
				{
					return customers.get(i).getAccount(j);
				}
			}
		}
		return null;
	}
	
	/**
	 * Acquire a Customer object with the account through account number.
	 * @param accnum Account number
	 * @return The object of the Customer that owns the account
	 */
	public Customer getCustomer(String accnum)
	{
		for(int i=0; i < customers.size(); i++)
		{
			for(int j=0; j< customers.get(i).getNumOfAccounts(); j++)
			{
				if(customers.get(i).getAccount(j).getAcctNum().equals(accnum))
				{
					return customers.get(i);
				}
			}
		}
		return null;
	}
	/**
	 * get the Customer object via the Customer's ID
	 * @param id Customer's ID
	 * @return Customer object
	 */
	public Customer getCustomerbyId(int id)
	{
		for(int i=0; i < customers.size(); i++)
		{
			
			if(customers.get(i).getId()==id)
			{
				return customers.get(i);
			}
			
		}
		return null;
	}
	
	/**
	 * get the bank's total balance
	 * @return Bank total balance.
	 */
	public double getTotalBalance()
	{
		double total = 0;
		for(int i=0; i < customers.size(); i++)
		{
			for(int j=0; j< customers.get(i).getNumOfAccounts(); j++)
			{
				total += customers.get(i).getAccount(j).getBalance();
			}
		}
		return total;
	}
	
	/**
	 * Inquire whether the account number is currently being used by the bank
	 * @param accnum Account number to view
	 * @return 사용여부. true in use, false Not in use
	 */
	public boolean Account_exist(String accnum)
	{
	
		for(int i=0; i < customers.size(); i++)
		{
			for(int j=0; j< customers.get(i).getNumOfAccounts(); j++)
			{
				if(customers.get(i).getAccount(j).getAcctNum().equals(accnum))
				{
					return true;
				}
			}
		}
		return false;
	}
	
}
