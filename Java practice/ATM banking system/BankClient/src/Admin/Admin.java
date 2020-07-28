package Admin;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import Protocol.Request;
import Protocol.Reply;

public class Admin extends JFrame{

	private static final String title = "Administrator";
	private configDialog configDialog;
	private loginDialog loginDialog;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private customers customers;
	private customerDialog customerDialog;
	private accountDialog accountDialog;
	private addAccountDialog addAccountDialog;
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private static Socket socket;
	
	private boolean config = false;
	private boolean login = false;

	
	private JMenuItem loginItem;
	private JMenuItem logoutItem;
	private JMenuItem customerItem;
	private JMenuItem addAccountItem;
	private JMenuItem delAccountItem;
	private JMenuItem configItem;
	
	private String id;

	
	public Admin()
	{
		super();
		
		setSize( 500, 600 );
		setTitle( title );
		setLayout( new BorderLayout() );
		
		
		// 중간 
		JPanel panel = new JPanel();
		panel.setLayout( new BorderLayout() );
		panel.setBorder( BorderFactory.createTitledBorder( "Action Window" ) );
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textArea.setEditable(false);
		panel.add(scrollPane);
		
		add( panel, BorderLayout.CENTER );
		// 중간 끝
		
		// 하단 패널에 추가
		JLabel label_bottom = new JLabel( "Version 1.0" );
		label_bottom.setHorizontalAlignment(JLabel.CENTER);
		JPanel panel2 = new JPanel();
		panel2.add(label_bottom, BorderLayout.CENTER);
		add(panel2, BorderLayout.SOUTH);
		// 하단 끝
		
		
		
		// 메뉴바
		JMenuBar mbar = new JMenuBar();
		setJMenuBar( mbar );
				
		
		// 연결 메뉴
		JMenu fileMenu = new JMenu( "connect" );
		mbar.add( fileMenu );
		
		configItem = new JMenuItem( "Connection setting" );
		configItem.addActionListener( new
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						if( configDialog == null )
							configDialog = new configDialog( Admin.this );
						
						configDialog.showDialog( Admin.this, "Connect setting" );
						socket = configDialog.getSocket();
						out = configDialog.getOutput();
						in = configDialog.getInput();
						
						config = configDialog.getConfig();
						configItem.setEnabled( !config );
						login = !config;
					}
				}
		);
		fileMenu.add( configItem );
		
		loginItem = new JMenuItem( "Login" );
		loginItem.addActionListener( new 
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						if( loginDialog == null )
							loginDialog = new loginDialog( Admin.this, socket, out, in );

						loginDialog.setSocket( socket, out, in );
						loginDialog.showDialog( Admin.this, "Login - ID" );
						
						
						if( login = loginDialog.getLogin() )
						{
							if( customers == null )
								customers = new customers( Admin.this, socket, out, in );
							else
							{
								customers.setSocket( socket, out, in );
								customers.info();
							}
							id = loginDialog.getId();
							
							if( customers.getCnt() == 0 )
								addAccountItem.setEnabled( false );
							else
								addAccountItem.setEnabled( true );
						}
						
						loginItem.setEnabled( !login );
						logoutItem.setEnabled( login );
						customerItem.setEnabled( login );
						// addAccountItem.setEnabled( login );
						delAccountItem.setEnabled( login );
					}
				}
		);
		fileMenu.add( loginItem );
		
		logoutItem = new JMenuItem( "Log out" );
		logoutItem.addActionListener( new 
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						try {
							
							String str[] = new String[1];
							str[0] = id;
							
							out.writeObject( new Request( Request.commands.ADMIN_LOGOUT, str ) );
							Reply reply = (Reply)in.readObject();
							if( reply.getReturnType() == Reply.return_value.ADMIN_LOGOUT_VALID )
								appendTextArea( id + "has logged out.\n" );
							else
								appendTextArea( "It is not a normal shutdown.\n" );
							
							out.writeObject( new Request( Request.commands.ATM_EXIT, null ) );
							
							out.close();
							in.close();
							socket.close();
							System.exit( 0 );
							
							login = !login;
							loginItem.setEnabled( !login );
							logoutItem.setEnabled( login );
							customerItem.setEnabled( login );
							addAccountItem.setEnabled( login );
							delAccountItem.setEnabled( login );
							
							} catch ( IOException e ) {
								e.printStackTrace();
							}catch (ClassNotFoundException e) {
								e.printStackTrace();
							} 
					}
				}
		);
		fileMenu.add( logoutItem );
		
		JMenuItem exitItem = new JMenuItem( "End" );
		exitItem.addActionListener( new 
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						System.exit( 0 );
					}
				}
		);
		fileMenu.add( exitItem );
		//  파일 메뉴 끝
		
		
		// 고객 메뉴
		JMenu customerMenu = new JMenu( "Customer" );
		mbar.add( customerMenu );
	
		customerItem = new JMenuItem( "Customer Management" );
		customerItem.addActionListener( new
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						customerDialog = new customerDialog( Admin.this, socket, out, in );
						customerDialog.showDialog( Admin.this, "Customer" );
						
						customers.info();
						if( customers.getCnt() == 0 )
							addAccountItem.setEnabled( false );
						else
							addAccountItem.setEnabled( true );
					}
				}
		);
		customerMenu.add( customerItem );
		// 고객 메뉴 끝
		
		JMenu accountMenu = new JMenu( "account" );
		mbar.add( accountMenu );
		
		addAccountItem = new JMenuItem( "Account registration" );
		addAccountItem.addActionListener( new
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						addAccountDialog = new addAccountDialog( Admin.this, socket, out, in );
						addAccountDialog.showDialog( Admin.this, "Customer" );
					}
				}
		);
		accountMenu.add( addAccountItem );

		delAccountItem = new JMenuItem( "View and delete your account" );
		delAccountItem.addActionListener( new
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						accountDialog = new accountDialog( Admin.this, socket, out, in );		
						accountDialog.showDialog( Admin.this, "Customer" );
					}
				}
		);
		accountMenu.add( delAccountItem );
		
		logoutItem.setEnabled( login );
		customerItem.setEnabled( login );
		addAccountItem.setEnabled( login );
		delAccountItem.setEnabled( login );
		
		setVisible( true );
		
	}
	
	public void appendTextArea(String s)
	{
		textArea.append(s);
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
	}
	
	public static void main(String[] args) {
		
		Admin admin = new Admin();
		admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}

