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

public class addAccountDialog extends JPanel implements dialog //, ListSelectionListener
{

	private JTextField id;
	private JTextField account;
	private JPasswordField pass;
	private JTextField balance;
	private JList list;
	
	private String str[] = new String[4];
	
	private JButton okButton;
	private boolean ok;
	private JDialog dialog;
	private static Socket socket;
	private static Admin admin;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private customers customers;
	
	private JComboBox idCombo;
	private JComboBox nameCombo;
	
	public addAccountDialog( Admin ad, Socket s, ObjectOutputStream o, ObjectInputStream i )
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
		panel.add( new JLabel( "Account registration" ) );
		add( panel, BorderLayout.NORTH );
		
		nameCombo = new JComboBox();
		nameCombo.addItem( "" );
		for( int j = 0; j < customers.getCnt(); j++ )
		{
			boolean add = true;
			for( int k = 0; k < j; k++ )
			{
				if( customers.customers.get( j ).getName().equals( customers.customers.get( k ).getName() ))
					add = false;
			}
			if( add )
				nameCombo.addItem( customers.customers.get(j).getName() );
			
		}

		idCombo = new JComboBox();
		idCombo.addItem( "" );
		id = new JTextField( (String)idCombo.getSelectedItem() );
		nameCombo.addActionListener( new 
				ActionListener()
				{
					public void actionPerformed( ActionEvent e )
					{
						idCombo.removeAllItems();
						
						for( int j = 0; j < customers.customers.size(); j++ )
							if( nameCombo.getSelectedItem().equals( customers.customers.get(j).getName() ) )
								idCombo.addItem( Integer.toString( customers.customers.get(j).getId() ) );
					}
				}
		);
		
		idCombo.addActionListener( new 
				ActionListener()
				{
					public void actionPerformed( ActionEvent e )
					{
						id = new JTextField( ( String )idCombo.getSelectedItem() );
					}
				}
		);
		JPanel panel2 = new JPanel();
		panel2.setLayout( new GridLayout( 5, 2, 10, 10 ) );
		panel2.setBorder( BorderFactory.createTitledBorder( "" ) );
		panel2.add( new JLabel( "   Name" ) );
		panel2.add( nameCombo );
		panel2.add( new JLabel( "   Identifier" ) );
		panel2.add( idCombo );
		panel2.add( new JLabel( "   Account number" ) );
		panel2.add( account = new JTextField("") );
		panel2.add( new JLabel( "   Password") );
		panel2.add( pass = new JPasswordField("") );
		panel2.add( new JLabel( "   Balance" ) );
		panel2.add( balance = new JTextField("") );

		
		add( panel2, BorderLayout.CENTER );
		
		okButton = new JButton( "Registration" );
		okButton.addActionListener( new
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
						else if (!account.getText().matches("[0-9]{3,3}[-][0-9]{3,3}[-][0-9]{3,3}"))
							JOptionPane.showMessageDialog(admin, "Invalid account number");
						else if (!pass.getText().matches("[0-9a-zA-Z]{2,}"))
							JOptionPane.showMessageDialog(admin, "Invalid password");
						else if( !balance.getText().matches("[0-9]{1,}[.]{0,1}[0-9]{0,1}"))
							JOptionPane.showMessageDialog(admin, "Invalid balnace");
						else
						{
							try {
								str[0] = id.getText();
								str[1] = account.getText();
								str[2] = pass.getText();
								str[3] = balance.getText();
								
								out.writeObject( new Request( Request.commands.ADD_ACCOUNT, str ) );
								println( " Add Account " + str[1] + " for " + str[0] );
								Reply reply = (Reply)in.readObject();
								
								if( reply.getReturnType() == Reply.return_value.ADD_ACCOUNT_VALID )
								{
									println( " Account " + str[1]+ " of " + str[0] + " has been added. ");
								}
								else
								{
									println( reply.getParameter()[0] );
									println( " Account " + str[1]+ " of " + str[0] + " has not been added. " );
								}
								// customers.info();
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
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add( okButton );
		buttonPanel.add( cancelButton );
		add( buttonPanel, BorderLayout.SOUTH );
		
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
		
		dialog.setSize( 400, 270 );
		dialog.setTitle( title );
		dialog.show();
		
		return ok;
	}
	
}
