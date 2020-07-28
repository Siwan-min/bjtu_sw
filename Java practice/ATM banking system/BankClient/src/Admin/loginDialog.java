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
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Protocol.Reply;
import Protocol.Request;

public class loginDialog extends JPanel implements dialog{

	
	private JTextField id;
	private JPasswordField pass;
	private String info[] = new String[2];
	private boolean valid;
	
	private JButton okButton;			// Login button
	private boolean ok;
	private JDialog dialog;
	private static Socket socket;
	private static Admin admin;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	
	/**
	 * 
	 * @param ad frame Information
	 * @param s  socket
	 * @param o  OuutputStream 
	 * @param i  inputStream
	 */
	public loginDialog( Admin ad, Socket s, ObjectOutputStream o, ObjectInputStream i )
	{
		socket = s;
		out = o;
		in = i;
		valid = false;
		dialog( ad );
	}
	
	/**
	 * Login DataLog
	 */
	public void dialog( Admin ad )
	{
		admin = ad;
		
		// Make a screen
		setLayout( new BorderLayout() );
		JPanel panel = new JPanel();
		panel.setLayout( new GridLayout( 2, 2, 10, 10 ) );
		panel.setBorder( BorderFactory.createTitledBorder( "" ) );
		
		panel.setBorder( BorderFactory.createTitledBorder( "" ) );
		panel.add( new JLabel( "   ID  " ) );
		panel.add( id = new JTextField("") );
		panel.add( new JLabel( "   Pass" ) );
		panel.add( pass = new JPasswordField("") );
		add( panel, BorderLayout.CENTER );

		
		okButton = new JButton("Login" );
		
		// When you click the login button
		okButton.addActionListener( new
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						ok = true;
						dialog.setVisible( false );
						
						try {
							valid = false;
							if ( id.getText().equals( "" ) )
								JOptionPane.showMessageDialog(admin, "Please fill all fields");
							else if (!id.getText().matches("[a-zA-Z]{2,}") )
								JOptionPane.showMessageDialog(admin, "Invalid id");
							else if (!pass.getText().matches("[0-9a-zA-Z]{2,}"))
								JOptionPane.showMessageDialog(admin, "Invalid password");
							else
							{
								info[0] = id.getText();
								info[1] = pass.getText();

								// Send id and password to server
								out.writeObject(new Request(Request.commands.ADMIN_LOGIN, info));
								out.flush();
								out.reset();
								println( socket + " s" );
								println( "Log in as " + info[0] );
								
								// Receive response from server
								Reply reply = (Reply)in.readObject();
								
								// When login failed
								if(reply.getReturnType() == Reply.return_value.ADMIN_LOGIN_FAILED )
								{
									// valid = false;
									println(reply.getParameter()[0]);
									println("Can not sign in with " + info[0] );
								}
								// Upon success
								else
								{
									valid = true;
									println("You are logged in as " + info[0] );
								}
							}
							
						} catch ( IOException e ) {
							e.printStackTrace();
						}catch (ClassNotFoundException e) {
							e.printStackTrace();
						} 
					}
				}
		);
		
		JButton  cancelButton = new JButton( "cancel" );
		// When you click cancel button
		cancelButton.addActionListener( new 
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						dialog.setVisible( false );
					}
				}
		);
		
		
		// Make a screen
		JPanel buttonPanel = new JPanel();
		buttonPanel.add( okButton );
		buttonPanel.add( cancelButton );
		add( buttonPanel, BorderLayout.SOUTH );
		
	}
	
	/**
	 * valid is the login success or failure.
	 * @return valid
	 */
	public boolean getLogin()
	{
		return valid;
	}
	
	/**
	 * Returns the id you entered
	 * @return id
	 */
	public String getId()
	{
		return id.getText();
	}
	
	/**
	 * admin panel¿¡ ¾¸..
	 */
	public void println(String s)
	{
		admin.appendTextArea(s + "\n");
	}
	
	/**
	 * Socket, IOStream settings
	 * @param s socket
	 * @param o OutputStream
	 * @param i InputStream
	 */
	public void setSocket( Socket s, ObjectOutputStream o, ObjectInputStream i )
	{
		socket = s;
		out = o;
		in = i;
	}
	
	/**
	 * Show dialog window
	 */
	@SuppressWarnings("deprecation")
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
		
		id.setText("");
		pass.setText("");
		
		dialog.setSize( 300, 130 );
		dialog.setTitle( title );
		dialog.show();
		
		return ok;
	}
}
