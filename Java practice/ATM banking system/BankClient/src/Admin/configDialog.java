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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class configDialog extends JPanel implements dialog{

	private JTextField ip;
	private JTextField port;
	
	private JButton okButton;
	private boolean ok;
	private JDialog dialog;
	private static Socket socket;
	private static Admin admin;
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private boolean valid = false;
	
	public configDialog( Admin ad )
	{
		dialog( ad );
	}
	
	public void dialog( Admin ad )
	{
		setLayout( new BorderLayout() );
		admin = ad;
		
		JPanel panel = new JPanel();
		panel.setLayout( new GridLayout( 2, 2, 10, 10 ) );
		panel.setBorder( BorderFactory.createTitledBorder( "" ) );
		
		panel.add( new JLabel( "   IP   " ) );
		panel.add( ip = new JTextField("") );
		panel.add( new JLabel( "   Port " ) );
		panel.add( port = new JTextField("") );
		add( panel, BorderLayout.CENTER );
		
		okButton = new JButton( "OK" );
		okButton.addActionListener( new
				ActionListener()
				{
					public void actionPerformed( ActionEvent event )
					{
						ok = true;
						dialog.setVisible( false );
						
						try {
							valid = false;
							if ( ip.getText().equals( "" ) )
								JOptionPane.showMessageDialog(admin, "Please fill all fields");
							else if( !ip.getText().matches( "[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}" ))
								JOptionPane.showMessageDialog(admin, "Invalid ip number");
							else if (!port.getText().matches("[0-9]{1,5}"))
								JOptionPane.showMessageDialog(admin, "Invalid port number");
							else
							{
								println( ip.getText() + "connection." );
								socket = new Socket( ip.getText(), Integer.parseInt( port.getText() ));
								println( socket + "" );
								out = new ObjectOutputStream( socket.getOutputStream() );
								in = new ObjectInputStream( socket.getInputStream() );
								
								valid = true;
							}
						}
						catch ( UnknownHostException e ) {
							e.printStackTrace();
						}
						catch ( IOException e) {
							valid = false;
							println( "Please reconnect" );
							e.printStackTrace();
						}
					}
				}
		);
		
		JButton  cancelButton = new JButton( "Cancel" );
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
	
	public ObjectOutputStream getOutput()
	{
		return out;
	}
	
	public ObjectInputStream getInput()
	{
		return in;
	}
	
	public Socket getSocket()
	{
		return socket;
	}
	
	public void println(String s)
	{
		admin.appendTextArea(s + "\n");
	}
	
	public boolean getConfig()
	{
		return valid;
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
		
		dialog.setSize( 300, 130 );
		dialog.setTitle( title );
		dialog.show();
		
		return ok;
	}
	
}
