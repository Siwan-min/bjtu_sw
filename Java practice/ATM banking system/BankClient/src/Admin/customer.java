package Admin;

import java.util.ArrayList;

public class customer {

	private int id;
	private String name;
	private String phone;
	private String addr;
	private ArrayList<String> acct;
	private ArrayList<String> balance;
	private int cntAcct;
	
	public customer( int aId, String aName, String aPhone, String aAddr )
	{
		this();
		id = aId;
		name = aName;
		phone = aPhone;
		addr = aAddr;
	}
	
	public customer()
	{
		acct = new ArrayList<String>();
		balance = new ArrayList<String>();
	}
	
	public void setId( int aId )
	{
		id = aId;
	}
	
	public void setName( String aName )
	{
		name = aName;
	}
	
	public void setPhone( String aPhone )
	{
		phone = aPhone;
	}
	
	public void setAddr( String aAddr )
	{
		addr = aAddr;
	}
	
	public void setCntAcct( int aCntAcct )
	{
		cntAcct = aCntAcct;
	}
	
	public void setAcct( String aAcct )
	{
		acct.add( new String( aAcct ) );
	}
	
	public void setBalance( String aBalance )
	{
		balance.add( new String( aBalance ) );
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public String getAddr()
	{
		return addr;
	}
	
	public int getCntAcct()
	{
		return cntAcct;
	}
	
	public String getAcct( int i )
	{
		return acct.get( i );
	}
	
	public String getBalance( int i )
	{
		return balance.get( i );
	}

}
