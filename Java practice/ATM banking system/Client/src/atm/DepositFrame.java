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

class DepositFrame extends JFrame implements ActionListener {
	JLabel lTitle, lamount;
	JTextField tamount, account;
	JButton submit, cancel;
	HashMap<Integer, JButton> btns = new HashMap<Integer, JButton>();
	String accounNum = "";
	JPanel p1, p2, p3, pL, pR;
	String[] str = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "¢¸",
			"correction" };
	JButton[] bstr = new JButton[12];
	GUI gui;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	Container container = getContentPane();

	public DepositFrame(String[] string, GUI g) {
		setTitle("deposit");
		gui = g;
		lTitle = new JLabel("Please enter a deposit amount");
		lamount = new JLabel("Price");
		tamount = new JTextField(20);
		submit = new JButton("Confirm");
		account = new JTextField(11);
		account.setText(string[0]);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					Reply reply;

					out = gui.out;
					in = gui.in;

					// String temp = "";
					String[] deposit = new String[2];
					deposit[0] = account.getText();
					deposit[1] = tamount.getText();
					
					if (deposit[1].equals("")) {
						deposit[1] = "0";
					}
					
					if(Validate.valid(tamount)){
						deposit[1] = tamount.getText();
					}
					else
						return;

					out.writeObject(new Request(Request.commands.DEPOSIT,
							deposit));

					reply = (Reply) in.readObject();

					String[] balance = reply.getParameter();
					double bal = Double.parseDouble(balance[0]);

					if (reply.getReturnType() == Reply.return_value.DEPOSIT_FAILED) {
						lTitle.setText(tamount.getText() + "can not make a deposit.");
						tamount.setText("");

					} else {
						JOptionPane.showMessageDialog(null, tamount.getText() + "RMB has been sent. The balance is "
								+ bal + "RMB");
//						lTitle.setText(tamount.getText() + "RMB has been sent. The balance is "
//								+ bal + "RMB");
						tamount.setText("");
						int temp = Integer.parseInt(deposit[1]);
						Ma.saveint += temp;
						Ma.save2.setText(Ma.saveint.toString() + "RMB");
						setVisible(false);
					}

				}

				catch (IOException e) {
					
				} catch (ClassNotFoundException e) {
					
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
		p2.add(lamount);
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