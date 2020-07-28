package atm;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Protocol.Reply;
import Protocol.Request;

class Ma extends JFrame {

	Container container = getContentPane();
	JLabel save, area;
	static JLabel save2;
	JButton depositB, withdrawB, referB, transferB, quitB;
	JPanel p1, p2;
	static Integer saveint = 1000;
	String savestr;
	String titlet = "";
	GUI gui;
	public String account;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public Ma(String str, GUI g) {
		setTitle("Main");
		gui = g;
		savestr = saveint.toString();
		save = new JLabel("    Current withdrawal limit amount ");
		save2 = new JLabel("");
		save2.setText("    " + savestr + "  RMB");
		area = new JLabel(titlet);
		area.setText("    Press the Service button.");
		account = new String(str);

		depositB = new JButton("Deposit");
		withdrawB = new JButton("Withdraw");
		referB = new JButton("Balance inquiry ");
		transferB = new JButton("Bank Transfer");
		quitB = new JButton("End");
		
		p1 = new JPanel();
		p2 = new JPanel();



		p1.add(referB);
		p1.add(depositB);
		p1.add(withdrawB);
		p1.add(transferB);
		p1.add(quitB);
		p1.setLayout(new GridLayout(5, 1));	
		
		p2.add(area);
		p2.add(save);
		p2.add(save2);
		p2.setLayout(new GridLayout(3, 1));	

		container.add(p1);
		container.add(p2);
		container.setLayout(new GridLayout(1, 2));

		withdrawB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String[] withDraw = new String[1];
				withDraw[0] = account;

				WithdrawFrame withdrawFrame = new WithdrawFrame(withDraw, gui);
				withdrawFrame.setLocation(300, 300);
				withdrawFrame.setVisible(true);
			}
		});

		depositB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String[] deposit = new String[1];
				deposit[0] = account;
				DepositFrame depositFrame = new DepositFrame(deposit, gui);
				depositFrame.setLocation(300, 300);
				depositFrame.setVisible(true);
			}
		});
		referB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {

					out = gui.out;
					in = gui.in;

					String[] balance = new String[1];
					String[] inqu = new String[1];
					inqu[0] = account;

					out
							.writeObject(new Request(Request.commands.BALANCE,
									inqu));
					Reply reply = (Reply) in.readObject();
					balance = reply.getParameter();
					if (reply.getReturnType() == Reply.return_value.BALANCE_VALID) {
						System.out.println("Balance inquiry succeeded.");
						Inquiry inquiry = new Inquiry(balance);
						inquiry.setLocation(700, 300);
						inquiry.setVisible(true);
						setVisible(true);
					} else {
						System.out.println("Balance inquiry failed.");
					}

				} catch (IOException e) {
					
				} catch (ClassNotFoundException e) {
					
				}
			}
		});
		transferB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String[] ttrans = new String[1];
				ttrans[0] = account;

				Transfer transfer = new Transfer(ttrans, gui);
				transfer.setLocation(300, 300);
				transfer.setVisible(true);
			}
		});
		quitB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
				gui.setVisible(true);
			}
		});

		setLocation(300, 300);
		setSize(400, 300);
		setVisible(true);
	}

	public void setVi() {
		setVisible(true);
	}
}