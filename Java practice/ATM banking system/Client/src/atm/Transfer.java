package atm;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Transfer extends JFrame implements ActionListener {
	JLabel lTitle, laccount;
	JTextField taccount, account;
	JButton submit, cancel;
	HashMap<Integer, JButton> btns = new HashMap<Integer, JButton>();
	String accounNum = "";
	JPanel p1, p2, p3, pL, pR;
	String[] str = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-",
			"correction" };
	JButton[] bstr = new JButton[12];
	GUI gui;
	Container container = getContentPane();

	public Transfer(String[] string,GUI g) {
		setTitle("Bank Transfer");
		gui = g;
		lTitle = new JLabel("Please enter account number.");
		laccount = new JLabel("Account Number");
		taccount = new JTextField(11);
		account = new JTextField(11);
		account.setText(string[0]);
		submit = new JButton("Confirm");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(Validate.validAccount(taccount))	{
					NTransfer nTransfer = new NTransfer(account.getText(), taccount
							.getText(),gui);
					setVisible(false);
					nTransfer.setLocation(300, 300);
					nTransfer.setVisible(true);
				}
				
				else{
					JOptionPane.showMessageDialog(null, "Account number entered incorrectly.");
					taccount.setText("");
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
		p2.add(taccount);
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

		if (strtmp == "-") {
			accounNum += strtmp;
			taccount.setText(accounNum);
			/*
			accounNum = taccount.getText().substring(0,
					taccount.getText().length() - 1);
			taccount.setText(accounNum);
			*/
		}

		else if (strtmp == "correction") {
			accounNum = "";
			taccount.setText(accounNum);
		}

		else {
			accounNum += strtmp;
			taccount.setText(accounNum);
		}

	}
}