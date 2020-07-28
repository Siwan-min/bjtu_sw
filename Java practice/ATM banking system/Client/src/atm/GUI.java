package atm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Protocol.Reply;
import Protocol.Request;

class GUI extends JFrame {
	/**
	 * 
	 */
	JLabel accountL, account1, account2, passwdL;
	JTextField accountF1, accountF2, accountF3;
	JPasswordField passwdF;
	JButton loginB, quitB;
	JPanel p1, p1set, p2, p2set, p3;

	Container container = getContentPane();

	private Socket socket;
	public static ObjectOutputStream out;
	public static ObjectInputStream in;

	static GUI application;

	/*
	 * public enum commands {// Instruction Enumeration // User Protocol LOGIN, // Login request LOGOUT, //
	 * Sign out request DEPOSIT, // Deposit request WITHDRAW, // Withdrawal request TRANSFER, // Request a transfer
	 * BALANCE, // Balance View Request TOTAL_CAPITAL, // Request total balance of bank (for testing) // Manager protocol
	 * CNT_CUSTOMER, // Returns the number of customers and their IDs CUSTOMER, // View customer information through customer number
	 * MOD_CUSTOMER, // Edit customer information ADD_CUSTOMER, // Add customers. DEL_CUSTOMER, // 고객
	 * 번호로 고객 정보를 삭제 ADD_ACCOUNT, // Add an account DEL_ACCOUNT, // Delete account ALL_ACCOUNT //
	 * Request all account information };
	 */
	/**
	 * 
	 */
	public GUI() {
		super("Login");

		try {

			socket = new Socket("127.0.0.1", 9999);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {

		}

		accountL = new JLabel("Account Number");
		passwdL = new JLabel("Password");
		loginB = new JButton("Login");
		quitB = new JButton("End");
		accountF1 = new JTextField(3);
		accountF2 = new JTextField(3);
		accountF3 = new JTextField(3);
		account1 = new JLabel("-");
		account2 = new JLabel("-");

		passwdF = new JPasswordField(4);

		quitB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				try {
					out.writeObject(new Request(Request.commands.ATM_EXIT));
					out.close();
					in.close();
					socket.close();
				} catch (IOException e) {

				}
				System.exit(0);
			}
		});

		loginB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {

					Reply reply;

					String acct = "";

					if (Validate.valid(accountF1)) {
						acct += accountF1.getText();
					}

					acct += account1.getText();

					if (Validate.valid(accountF2)) {
						acct += accountF2.getText();
					}

					acct += account2.getText();

					if (Validate.valid(accountF3)) {
						acct += accountF3.getText();
					}

					String[] login = new String[2];
					login[0] = acct; // Account Number

					login[1] = passwdF.getText(); // Account password

					out.writeObject(new Request(Request.commands.LOGIN, login));

					reply = (Reply) in.readObject();

					if (reply.getReturnType() == Reply.return_value.LOGIN_FAILED) {
						JOptionPane.showMessageDialog(null,
								"Invalid account number or password.");
						accountF1.setText("");
						accountF2.setText("");
						accountF3.setText("");
						passwdF.setText("");

					}

					else {
						accountF1.setText("");
						accountF2.setText("");
						accountF3.setText("");
						passwdF.setText("");

						JOptionPane.showMessageDialog(null, "Log-in succeeded.");
						Ma ma = new Ma(acct, application);
						setVisible(false);
						ma.setVisible(true);
					}

				}

				catch (IOException e) {

				} catch (ClassNotFoundException e) {

				}

			}
		});

		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();

		p1.add(accountL);
		p1.add(accountF1);
		p1.add(account1);
		p1.add(accountF2);
		p1.add(account2);
		p1.add(accountF3);

		p2.add(passwdL);
		p2.add(passwdF);

		p3.add(loginB);
		p3.add(quitB);

		container.add(p1);
		container.add(p2);
		container.add(p3);

		container.setLayout(new GridLayout(3, 1));

		setLocation(300, 300);
		setSize(300, 200);
		setVisible(true);

	}

	/**
	 * 
	 * @param args
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void main(String[] args) throws UnknownHostException,
			IOException {

		application = new GUI();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}