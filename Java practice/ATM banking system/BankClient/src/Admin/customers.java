package Admin;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.*;

import Protocol.Request;
import Protocol.Reply;

import java.util.*;

public class customers {

	private static Socket socket;
	private static Admin admin;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	private static int cnt;
	
	public static ArrayList <customer> customers;

	public customers()
	{
		// customers.
	}
	
	public customers( Admin ad, Socket s, ObjectOutputStream o, ObjectInputStream i )
	{
		socket = s;
		out = o;
		in = i;
		admin = ad;
		
		info();
	}
	
	public void info()
	{
		try {
			
			customers = new ArrayList<customer>();
			
			out.writeObject( new Request(Request.commands.CNT_CUSTOMER, null ) );
			Reply reply = (Reply)in.readObject();
			
			if(reply.getReturnType() == Reply.return_value.CNT_CUSTOMER_VALID)
			{
				println( "All customer information is brought." );
				cnt = Integer.parseInt( reply.getParameter()[0] );
				println( "Customers are all " +  cnt + " names." );
				
				// Store customer ID
				for( int j = 0; j < cnt; j++ )
				{
					int tmp = Integer.parseInt( reply.getParameter()[j+1] );
					customers.add( new customer() );
					customers.get( j ).setId( Integer.parseInt( reply.getParameter()[j+1] ) );
				}
				
				// Customer Information
				for( int j = 0; j < cnt; j++ )
				{
					String id[] = new String[1];					
					id[0] = Integer.toString( customers.get( j ).getId() );
					out.writeObject( new Request( Request.commands.CUSTOMER, id ) );
					reply = ( Reply )in.readObject();
					
					if( reply.getReturnType() == Reply.return_value.CUSTOMER_VALID )
					{
						println( "Customer information with id " + id[0] + " has been imported." );
						
						customers.get( j ).setName( reply.getParameter()[0] );
						customers.get( j ).setPhone( reply.getParameter()[1] );
						customers.get( j ).setAddr( reply.getParameter()[2] );
						customers.get( j ).setCntAcct( Integer.parseInt( reply.getParameter()[3] ) );
						
						// Account
						for( int k = 0; k < customers.get( j ).getCntAcct(); k++ )
							customers.get( j ).setAcct( reply.getParameter()[k+4] );
			 			
					}
					else
					{
						println( reply.getParameter()[0] );
						println( "Could not import customer information with id " + id[0] );
					}
				}
				// Save Balance
				for( int j = 0; j < cnt; j++ )
				{
					for( int k = 0; k < customers.get(j).getCntAcct(); k++ )
					{
						String acc[] = new String[1];
						acc[0] = customers.get(j).getAcct(k);
						out.writeObject( new Request( Request.commands.BALANCE, acc ) );
						reply = ( Reply )in.readObject();
						
						if( reply.getReturnType() == Reply.return_value.BALANCE_VALID )
							customers.get(j).setBalance( reply.getParameter()[0] );
					}
				}
			}
						
		} catch ( IOException e ){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int getCnt()
	{
		return cnt;
	}
	
	public void setSocket( Socket s, ObjectOutputStream o, ObjectInputStream i )
	{
		socket = s;
		out = o;
		in = i;
	}
	
	public void println(String s)
	{
		admin.appendTextArea(s + "\n");
	}
	
	public void print(String s)
	{
		admin.appendTextArea(s);
	}
}
