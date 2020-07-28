package Server;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

import Banking.Bank;
import java.text.*;
import java.util.*;
public class Server extends JFrame {
	private Container container;
	private Thread tmain,ttime;
	private JPanel panel1,panel2;
	private JButton StartButton,EndButton;
	private SockThread sockthread;

	/**
	 * Variable that stores the Bank object. 
	 * Declared as Static
	 */
	static Bank bank ;
	
	/** 
	 * The server's constructor.
	 * Create the interface needed to operate the server.
	 */
	public Server() {
		
		super("Banking Server");
		
		StartButton = new JButton("START");
		EndButton = new JButton("STOP");
	
		EndButton.setEnabled(false);
		
		StartButton.addActionListener(new ActionListener() {
			// Register action listener for action when start button is clicked
			
			public void actionPerformed(ActionEvent event) {
				
				ObjectInputStream input;
				try	{
					/*
					 * Generates an input stream from a file and reads the stored bank.
					 */
					input = new ObjectInputStream( new FileInputStream( "data.txt") );
					
					/*
					 * Back up data.txt file before opening data.txt.
					 */
				
					bank = (Bank)input.readObject();
					input.close();
					File data = new File("data.txt");
					if(data.exists())
					{
						SimpleDateFormat sdf = new SimpleDateFormat();
						sdf.applyPattern("yyyyMMdd");
						data.renameTo(new File(sdf.format(new Date(System.currentTimeMillis()))+"data.bak"));
					}
				}
				catch( Exception e )
				{/* If you can not read the object from the file, create a new object.*/
					File data = new File("data.txt");
					if(data.exists())
					{	
						try
						{
							input = new ObjectInputStream( new FileInputStream( "data.txt") );
							bank = (Bank)input.readObject();
							input.close();
						}
						catch( Exception e1 )
						{
							bank = new Bank();
						}
					}
					else
						bank = new Bank();
				}
				
				/* Create a thread that binds the server, accepts client connections, and creates a processing thread*/
				sockthread = new SockThread(9999);
				tmain = new Thread(sockthread);
				tmain.start();
				
				/* Create threads that store data every hour */
				ttime = new Thread(new TimeHandler(bank));
				ttime.start();
				
				StartButton.setEnabled(false);
				EndButton.setEnabled(true);
			}
		});
		
		EndButton.addActionListener(new ActionListener() { // inner class
			public void actionPerformed(ActionEvent event) {
				// Register action listener for action when end button is clicked
				
				/* Closes the bound socket and terminates the running thread. */
				sockthread.sockclose();
				tmain.stop();
				ttime.stop();
				
				/* Back up data before saving data at shutdown. Prepare for data loss due to exception */
				File data = new File("data.txt");
				if(data.exists())
					data.renameTo(new File("data.bak"));
				
				try	{
					/* Save the server's Bank object to a file */
					
					ObjectOutputStream output = new ObjectOutputStream( new FileOutputStream( "data.txt", false ) );
					output.writeObject( bank );
					output.close();
				}
				catch( IOException e )
				{
					/* Recovering backup files in case of data storage failure*/
					JOptionPane.showMessageDialog(null, "Data save fail");
					data = new File("data.bak");
					if(data.exists())
						data.renameTo(new File("data.txt"));
				}	
				StartButton.setEnabled(true);
				EndButton.setEnabled(false);
			}
		});
		
		/* Activate window, register panel, button on container */
		container = getContentPane();
		container.setLayout(new GridLayout(3,2));
	
		panel1 = new JPanel();
		panel1.add(StartButton);
		
		panel2 = new JPanel();
		panel2.add(EndButton);
		
		container.add(new JPanel());
		container.add(new JPanel());
		container.add(panel1);
		container.add(panel2);

		setSize(400, 180);
		setVisible(true);
	} 
	
	/* Runnable class that binds sockets and manages client connections  */
	public class SockThread implements Runnable  {
		
		private ServerSocket s;
		private int port;
		/**
		 * Constructor of SockThread, set port number
		 * @param portnum Port number to bind
		 */
		SockThread(int portnum){
			port = portnum;
		}
		
		/**
		 * Thread processing routines.
		 */
		public void run() {
			try {

				try {
					// Creating and binding server sockets
					s = new ServerSocket(port);

					while (true) {
					// Repeat and wait for client connections. Create a ThreadHandler thread on connection to process client requests.
						Socket incoming = s.accept();
						Thread t = new Thread(new ThreadHandler(incoming, bank));
						t.start();
					}
				}

				catch (Exception e) {
					System.out.println(e);
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		}
		/**
		 * Close socket.
		 */
		public void sockclose()
		{
			try{
				s.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Create main function server object.
	 * @param args
	 */
	public static void main(String args[]) {
		
		Server application = new Server();
		
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}