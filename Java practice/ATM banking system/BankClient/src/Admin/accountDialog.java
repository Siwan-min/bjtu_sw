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

public class accountDialog extends JPanel implements dialog //, ListSelectionListener
{

	private JTextField id;
	private JTextField name;
	private JTextField account;
	private JTextField balance;
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
	
	public accountDialog( Admin ad, Socket s, ObjectOutputStream o, ObjectInputStream i )
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
		panel.add( new JLabel( "Account management" ) );
		add( panel, BorderLayout.NORTH );
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout( new GridLayout( 1, 3, 10, 10 ) );
		centerPanel.setBorder( BorderFactory.createTitledBorder( "" ) );
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout( new BorderLayout() );
		leftPanel.setBorder( BorderFactory.createTitledBorder( "Account Number" ) );
		centerPanel.add( leftPanel );
		
		listModel = new DefaultListModel();
		listing();
		leftPanel.add( listScrollPane );
	
		JPanel panel2 = new JPanel();
		panel2.setLayout( new GridLayout( 4, 1, 10, 10 ) );
		panel2.add( new JLabel( "   Account" ) );
		panel2.add( new JLabel( "   Identifier" ) );
		panel2.add( new JLabel( "   Name" ) );
		panel2.add( new JLabel( "   Balance" ) );
		centerPanel.add( panel2 );
		
		JPanel panel3 = new JPanel();
		panel3.setLayout( new GridLayout( 4, 1, 10, 10 ) );
		panel3.add( account = new JTextField("") );
		panel3.add( id = new JTextField("") );
		panel3.add( name = new JTextField("") );
		panel3.add( balance = new JTextField( "" ) );
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
							
							ListModel dm = list.getModel();
							Object item = dm.getElementAt( index );
							account.setText( ( String )item );
							
							for( int j = 0; j < customers.getCnt(); j++ )
								for( int k = 0; k < customers.customers.get( j ).getCntAcct(); k++ )
									if( account.getText().equals( customers.customers.get( j ).getAcct( k ) ) )
									{
										id.setText( Integer.toString( customers.customers.get( j ).getId() ) );
										name.setText( customers.customers.get( j ).getName() );
										balance.setText( customers.customers.get( j ).getBalance( k ) );
										break;
									}
						}
					}
				}
		);

		okButton = new JButton( "delete" );
		okButton.addActionListener( new
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						ok = true;
						dialog.setVisible( false );
						
						if ( account.getText().equals( "" ) )
							JOptionPane.showMessageDialog(admin, "Please fill all fields");
						else if( account.getText().matches( "[0-9]{3,3}[-][0-9]{3,3}[-][0-9]{3,3}") )
							JOptionPane.showMessageDialog(admin, "Invalid account number");
						else
						{
							try {
								str[0] = account.getText();
								str[1] = null;
								str[2] = null;
								str[3] = null;
								
								out.writeObject( new Request( Request.commands.DEL_ACCOUNT, str ) );
								println( str[0] + " Delete your account." );
								Reply reply = (Reply)in.readObject();
								
								if( reply.getReturnType() == Reply.return_value.DEL_ACCOUNT_VALID )
									println( str[0] + " Account deleted." );
								else
									println( str[0] + " Failed to delete account." );
								
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
		
		okButton.setEnabled( button );
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add( okButton );
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
		{
			for( int k = 0; k < customers.customers.get( j ).getCntAcct(); k++ )
				listModel.addElement( customers.customers.get( j ).getAcct( k ) );
		}
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
