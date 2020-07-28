package atm;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Inquiry extends JFrame {
	JLabel lamount, llamount, tamount;
//	JTextField tamount;
	JButton submit;
	JPanel p1, p2, p3;
	Container container = getContentPane();

	public Inquiry(String[] str) {
		setTitle("Balance inquiry");
		lamount = new JLabel("Your current balance is");
		llamount = new JLabel("RMB");
		tamount = new JLabel("");
		tamount.setText(str[0]);
		submit = new JButton("Confirm");

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
			}
		});

		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();

		p1.add(lamount);
		p2.add(tamount);
		p2.add(llamount);
		p3.add(submit);

		container.add(p1);
		container.add(p2);
		container.add(p3);

		container.setLayout(new GridLayout(3, 1));
		setSize(300, 200);

	}
}