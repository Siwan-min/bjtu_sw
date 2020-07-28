package atm;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Protocol.Reply;
import Protocol.Request;

class NTransfer extends JFrame implements ActionListener {
	JLabel lTitle, laccount;
	String accounNum = "";
	JTextField tamount, account, taccount;
	JButton submit, cancel;
	HashMap<Integer, JButton> btns = new HashMap<Integer, JButton>();

	JPanel p1, p2, p3, pL, pR;
	String[] str = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "¢¸",
			"correction" };
	JButton[] bstr = new JButton[12];
	GUI gui;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	Container container = getContentPane();

	public NTransfer(String string, String string1, GUI g) {
		setTitle("Bank Transfer");
		gui = g;
		lTitle = new JLabel("Please enter the amount you want to send.");
		laccount = new JLabel("Amount");
		tamount = new JTextField(20);
		account = new JTextField(11);
		taccount = new JTextField(11);
		
		account.setText(string);
		taccount.setText(string1);

		submit = new JButton("Confirm");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					Reply reply;

					out = gui.out;
					in = gui.in;

					String[] transf = new String[3];
					transf[0] = account.getText();
					transf[1] = taccount.getText();
					
					if(Validate.valid(tamount)){
						transf[2] = tamount.getText();
						out.writeObject(new Request(Request.commands.TRANSFER,
								transf));

						reply = (Reply) in.readObject();

						String[] balance = reply.getParameter();
						
						if (reply.getReturnType() == Reply.return_value.TRANSFER_FAILED) {
							JOptionPane.showMessageDialog(null, reply.getParameter()[0]);
							
						} else {
							double bal = Double.parseDouble(balance[0]);
							JOptionPane.showMessageDialog(null, tamount.getText() + "RMB has been sent. The balance is  "
									+ bal + "RMB");
							
						}
						setVisible(false);
					}
					
					else{
						JOptionPane.showMessageDialog(null, "Amount entered incorrectly.");
						tamount.setText("");
					}
/*
					out.writeObject(new Request(Request.commands.TRANSFER,
							transf));

					reply = (Reply) in.readObject();

					String[] balance = reply.getParameter();
					double bal = Double.parseDouble(balance[0]);

					if (reply.getReturnType() == Reply.return_value.WITHDRAW_FAILED) {
						
					} else {
						lTitle.setText(tamount.getText() + "RMB has been sent. The balance is "
								+ bal + "RMB");
					}
*/
				}

				catch (IOException e) {
//					e.printStackTrace();
				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
				}

			}
		});
		cancel = new JButton("cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
			}
		});

		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		pL = new JPanel();
		pL.setLayout(new GridLayout(3, 1));
		pR = new JPanel();
		pR.setLayout(new GridLayout(4, 3));

		for (int i = 0; i < 12; i++) {
			bstr[i] = new JButton();
			bstr[i].setText(str[i]);
			bstr[i].addActionListener(this);
			pR.add(bstr[i]);
			btns.put(i + 1, bstr[i]);
		}

		p1.add(lTitle);
		p2.add(laccount);
		p2.add(tamount);
		p3.add(submit);
		p3.add(cancel);

		pL.add(p1);
		pL.add(p2);
		pL.add(p3);

		container.add(pL);
		container.add(pR);

		container.setLayout(new GridLayout(1, 2));
		setSize(600, 300);

	}

	public void actionPerformed(ActionEvent ae) {
		JButton tmp = (JButton) ae.getSource();

		String strtmp = tmp.getText();

		if (strtmp == "¢¸") {
			accounNum = tamount.getText().substring(0,
					tamount.getText().length() - 1);
			tamount.setText(accounNum);
		}

		else if (strtmp == "correction") {
			accounNum = "";
			tamount.setText(accounNum);
		}

		else {
			accounNum += strtmp;
			tamount.setText(accounNum);
		}

	}

}