package Admin;

import java.awt.*;
import java.net.*;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.io.*;
/**
 * 
 * @author umeyon
 *
 * Interfaces defining dialogs
 */
public interface dialog{

	public JButton okButton = null;
	public boolean ok = false;
	public JDialog dialog = null;
	public static Socket socket = null;
	public static Admin admin = null;
	
	public void dialog( Admin ad );
	public void println(String s);
	public boolean showDialog( Component parent, String title );
	
}
