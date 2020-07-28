package atm;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Validate {
	static JTextField text = new JTextField(11);

	public static boolean valid(JTextField t) {
		String str = "";
		str += t.getText();

		if (str.matches("[0-9]{1,}")) {
			str = null;
			return true;
		} else {
			str = null;
			JOptionPane.showMessageDialog(null, "Please enter the correct value.");
			return false;
		}
	}
	
	public static boolean validAccount(JTextField t){
		String str = "";
		str += t.getText();

		if (str.matches("[0-9]{3,3}[-][0-9]{3,3}[-][0-9]{3,3}")) {
			str = null;
			return true;
		} else {
			str = null;
			JOptionPane.showMessageDialog(null, "Please fill in the account number format");
			return false;
		}
	}
}
//[0-9]-[0-9]-[0-9]