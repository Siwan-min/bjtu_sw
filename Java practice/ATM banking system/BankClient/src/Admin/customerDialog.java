package Admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Protocol.Reply;
import Protocol.Request;

public class customerDialog extends JPanel implements dialog //, ListSelectionListener
{

	private JTextField id;
	private JTextField name;
	private JTextField phone;
	private JTextField addr;
	private JList list;
	
	private String str[] = new String[4];
	
	private JButton modifyButton;
	private JButton delButton;
	
	private JButton okButton;
	private boolean ok;
	private JDialog dialog;
	private static Socket socket;
	private static Admin admin;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean button;
	
	private customers customers;
	
	private DefaultListModel listModel;
	private JScrollPane listScrollPane;
	
	public customerDialog( Admin ad, Socket s, ObjectOutputStream o, ObjectInputStream i )
	{
		socket = s;
		out = o;
		in = i;
		customers = new customers();
		dialog( ad );
	}
	
	public void dialog( Admin ad )
	{	
		setLayout( new BorderLayout() );
		admin = ad;
		
		JPanel panel = new JPanel();
		panel.setLayout( new BorderLayout() );
		panel.setBorder( BorderFactory.createTitledBorder( "" ) );
		panel.add( new JLabel( "Customer Management" ) );
		add( panel, BorderLayout.NORTH );
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout( new GridLayout( 1, 3, 10, 10 ) );
		centerPanel.setBorder( BorderFactory.createTitledBorder( "" ) );
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout( new BorderLayout() );
		leftPanel.setBorder( BorderFactory.createTitledBorder( "ID" ) );
		centerPanel.add( leftPanel );
		
		listModel = new DefaultListModel();
		listing();	
		leftPanel.add( listScrollPane );
	
		JPanel panel2 = new JPanel();
		panel2.setLayout( new GridLayout( 4, 1, 10, 10 ) );
		panel2.add( new JLabel( "   Identifier" ) );
		panel2.add( new JLabel( "   Name" ) );
		panel2.add( new JLabel( "   Phone number" ) );
		panel2.add( new JLabel( "   Address") );
		centerPanel.add( panel2 );
		
		JPanel panel3 = new JPanel();
		panel3.setLayout( new GridLayout( 4, 1, 10, 10 ) );
		panel3.add( id = new JTextField("") );
		panel3.add( name = new JTextField("") );
		panel3.add( phone = new JTextField("") );
		panel3.add( addr = new JTextField("" ) );
		centerPanel.add( panel3 );
	
		add( centerPanel, BorderLayout.CENTER );
	
		// 더블클릭시
		list.addMouseListener( new 
				MouseAdapter()
				{
					public void mouseClicked( MouseEvent e )
					{
						JList list = ( JList )e.getSource();
						
						if( e.getClickCount() == 2 )
						{
							int index = list.locationToIndex( e.getPoint() );
							
							id.setText( Integer.toString( customers.customers.get(index).getId() ) );
							name.setText( customers.customers.get(index).getName() );
							phone.setText( customers.customers.get(index).getPhone() );
							addr.setText( customers.customers.get(index).getAddr() );
						}
					}
				}
		);
		
		okButton = new JButton( "Registration" );
		okButton.addActionListener( new
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						ok = true;
						dialog.setVisible( false );
					
						
						try {
				
							if ( id.getText().equals( "" )  )
								JOptionPane.showMessageDialog(admin, "Please fill all fields");						
							else if (!id.getText().matches("[0-9]{1,}") )
								JOptionPane.showMessageDialog(admin, "Invalid id");
							else if( name.getText().matches("[0-9]{1,}") )
								JOptionPane.showMessageDialog(admin, "Invalid name");
							else if( !phone.getText().matches( "[0-9]{3,3}[-][0-9]{3,4}[-][0-9]{4,4}"))
								JOptionPane.showMessageDialog(admin, "Invalid phone number");
							else if( addr.getText().equals( "" ) )
								JOptionPane.showMessageDialog(admin, "Please fill address fields");
							else
							{	
								str[0] = id.getText();
								str[1] = name.getText();
								str[2] = phone.getText();
								str[3] = addr.getText();
								
								out.writeObject( new Request( Request.commands.ADD_CUSTOMER, str ) );
								println( "Register new customers." );
								Reply reply = (Reply)in.readObject();
								
								if( reply.getReturnType() == Reply.return_value.ADD_CUSTOMER_VALID )
									println( "New customer registered." );
								else
								{
									println( reply.getParameter()[0] );
									println( "Failed to register new customer." );
								}
								out.flush();
							}
							
						} catch ( IOException e ) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					
					}
				}
					
		);
		
		modifyButton = new JButton( "Modify" );
		modifyButton.addActionListener( new
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						ok = true;
						dialog.setVisible( false );

						
						if ( id.getText().equals( "" ) )
							JOptionPane.showMessageDialog(admin, "Please fill all fields");
						else if (!id.getText().matches("[0-9]{1,}") )
							JOptionPane.showMessageDialog(admin, "Invalid id");
						else if( !phone.getText().matches( "[0-9]{3,3}[-][0-9]{3,4}[-][0-9]{4,4}"))
							JOptionPane.showMessageDialog(admin, "Invalid phone number");
						else if( addr.getText().equals( "" ) )
							JOptionPane.showMessageDialog(admin, "Please fill address fields");
						else
						{
							try {							
								str[0] = id.getText();
								str[1] = phone.getText();
								str[2] = addr.getText();
								str[3] = null;
								
								out.writeObject( new Request( Request.commands.MOD_CUSTOMER, str ) );
								println("Edit customer information with" + "ID " + str[0]);
								Reply reply = (Reply)in.readObject();
								
								if( reply.getReturnType() == Reply.return_value.MOD_CUSTOMER_VALID )
									println( "Customer information has been modified." );
								else
								{
									println( reply.getParameter()[0] );
									println( "Failed to edit customer information." );
								}
								// listing();
								out.flush();
								
							} catch ( IOException e ) {
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						}
					}
				}
			
		);
		
		delButton = new JButton( "delete" );
		delButton.addActionListener( new
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						ok = true;
						dialog.setVisible( false );

						
						if ( id.getText().equals( "" ) )
							JOptionPane.showMessageDialog(admin, "Please fill all fields");
						else if (!id.getText().matches("[0-9]{1,}") )
							JOptionPane.showMessageDialog(admin, "Invalid id");
						
						else
						{
						
							try {
								
								str[0] = id.getText();
								str[1] = null;
								str[2] = null;
								str[3] = null;
								
								out.writeObject( new Request( Request.commands.DEL_CUSTOMER, str ) );
								println( "Delete customer information with " + "ID" + str[0] );
								Reply reply = (Reply)in.readObject();
								
								if( reply.getReturnType() == Reply.return_value.DEL_CUSTOMER_VALID )
									println( "Customer information has been deleted" );
								else
								{
									println( "reply.getParameter()[0]" );
									println( "Failed to delete customer information" );
								}
								// listing();
								out.flush();
								
							} catch ( IOException e ) {
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						}
					}
				}
			
		);
		
		JButton  cancelButton = new JButton( "close" );
		cancelButton.addActionListener( new 
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dialog.setVisible( false );
					}
				}
		);
		
		modifyButton.setEnabled( button );
		delButton.setEnabled( button );
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add( okButton );
		buttonPanel.add( modifyButton );
		buttonPanel.add( delButton );
		buttonPanel.add( cancelButton );
		add( buttonPanel, BorderLayout.SOUTH );
	}
	
	public void listing()
	{
		customers.info();
		listModel.removeAllElements();
		
		if( customers.getCnt() == 0 )
			button = false;
		else
			button = true;
		
		for( int j = 0; j < customers.getCnt(); j++ )
			listModel.addElement( Integer.toString( customers.customers.get( j ).getId() ) );
		
		list = new JList( listModel );
		listScrollPane = new JScrollPane( list );
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);

	}

	public void println(String s)
	{
		admin.appendTextArea(s + "\n");
	}
	
	public boolean showDialog( Component parent, String title )
	{
		ok = false;
		
		Frame owner = null;
		if( parent instanceof Frame )
			owner = ( Frame ) parent;
		else
			owner = ( Frame ) SwingUtilities.getAncestorOfClass( Frame.class, parent );
		
		if( dialog == null || dialog.getOwner() != owner )
		{
			dialog = new JDialog( owner, true );
			dialog.add( this );
			dialog.getRootPane().setDefaultButton( okButton );
			dialog.pack();
		}
		

		
		dialog.setSize( 550, 230 );
		dialog.setTitle( title );
		dialog.show();
		
		return ok;
	}
	
}
