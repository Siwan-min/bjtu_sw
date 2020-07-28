package Server;
/**
 * @author MIN SI WAN
 */

import java.io.*;
import java.net.*;

import Banking.*;
import Protocol.Reply;
import Protocol.Request;

/*
 * Handle requests after each client connects
 */
class ThreadHandler implements Runnable {

	private Socket s;
	private Bank bank;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	static final String admin_id = "admin";
	static final String admin_pass = "1234";

	/**
	 * The constructor for ThreadHandler.
	 * 
	 * @param socket
	 *            Client connection socket
	 * @param _bank
	 *            Bank object
	 *            
	 */
	ThreadHandler(Socket socket, Bank _bank) {
		s = socket;
		bank = _bank;
	}

	public void run() {

		Account account;

		try {

			// Create object input and output on the socket.
			in = new ObjectInputStream(s.getInputStream());
			out = new ObjectOutputStream(s.getOutputStream());

			// Process the request repeatedly for each client connection until termination.
			while (true) {
	
				Request request = (Request) in.readObject();
				
				// Receive the request from the client.

				switch (request.getCommandType()) {
				// Determine the type of request.

				case ATM_EXIT:
					// Terminate the connection with ATM.
					in.close();
					out.close();
					s.close();
					return;

				case ADMIN_LOGIN:
					// 	
					if (request.getParameter()[0].equals(admin_id)) {
						if (request.getParameter()[1].equals(admin_pass)) {
							Reply log = new Reply(
									Reply.return_value.ADMIN_LOGIN_VALID);
							out.writeObject(log);
						} else {
							String[] args = new String[1];
							args[0] = "Passwords do not match.";
							Reply log = new Reply(
									Reply.return_value.ADMIN_LOGIN_FAILED, args);
							out.writeObject(log);
						}
					} else {
						String[] args = new String[1];
						args[0] = "ID" + request.getParameter()[0]
								+ " does not exist.";
						Reply log = new Reply(
								Reply.return_value.ADMIN_LOGIN_FAILED, args);
						out.writeObject(log);
					}
					break;

				case ADMIN_LOGOUT:

					if (request.getParameter()[0].equals(admin_id)) {
						Reply log = new Reply(Reply.return_value.ADMIN_LOGOUT_VALID);
						out.writeObject(log);
					}
					
				case LOGIN:
					// 	
					account = bank.getAccount(request.getParameter()[0]);

					if (account == null) {
						String[] args = new String[1];
						args[0] = "Account does not exist.";
						Reply log = new Reply(Reply.return_value.LOGIN_FAILED,
								args);
						out.writeObject(log);
					} else {
						if (account.getPassword().equals(
								request.getParameter()[1])) {
							Reply log = new Reply(
									Reply.return_value.LOGIN_VALID);
							out.writeObject(log);
						} else {
							String[] args = new String[1];
							args[0] = "Passwords do not match.";
							Reply log = new Reply(
									Reply.return_value.LOGIN_FAILED, args);
							out.writeObject(log);
						}
					}
					break;

				case LOGOUT:

					account = bank.getAccount(request.getParameter()[0]);

					if (account != null) {
						Reply log = new Reply(Reply.return_value.LOGOUT_VALID);
						out.writeObject(log);
					} else {
						String[] args = new String[1];
						args[0] = "Account does not exist.";
						Reply log = new Reply(Reply.return_value.LOGIN_FAILED,
								args);
						out.writeObject(log);
					}

					break;

				case DEPOSIT: //  
					account = bank.getAccount(request.getParameter()[0]);
					if (account != null) {
						if (account.deposit(Double.parseDouble(request
								.getParameter()[1]))) {
							String[] args = new String[1];
							args[0] = "" + account.getBalance();
							Reply dep = new Reply(
									Reply.return_value.DEPOSIT_VALID, args);
							out.writeObject(dep);
						} else {
							String[] args = new String[1];
							args[0] = "You have entered an incorrect amount.";
							Reply dep = new Reply(
									Reply.return_value.DEPOSIT_FAILED, args);
							out.writeObject(dep);
						}
					} else {
						String[] args = new String[1];
						args[0] = "Account does not exist.";
						Reply dep = new Reply(
								Reply.return_value.WITHDRAW_FAILED, args);
						out.writeObject(dep);
					}
					break;

				case WITHDRAW:
					account = bank.getAccount(request.getParameter()[0]);

					if (account != null) {
						if (account.withdraw(Double.parseDouble(request
								.getParameter()[1]))) {
							String[] args = new String[1];
							args[0] = "" + account.getBalance();
							Reply wit = new Reply(
									Reply.return_value.WITHDRAW_VALID, args);
							out.writeObject(wit);
						} else {
							String[] args = new String[1];
							args[0] = "There is not enough balance.";
							Reply dep = new Reply(
									Reply.return_value.WITHDRAW_FAILED, args);
							out.writeObject(dep);
						}
					} else {
						String[] args = new String[1];
						args[0] = "Account does not exist.";
						Reply dep = new Reply(
								Reply.return_value.WITHDRAW_FAILED, args);
						out.writeObject(dep);
					}
					break;

				case TRANSFER:

					account = bank.getAccount(request.getParameter()[0]);
					Account toAccount = bank
							.getAccount(request.getParameter()[1]);

					if (account == null) {
						String[] args = new String[1];
						args[0] = "No withdrawal account exists.";
						Reply tra = new Reply(
								Reply.return_value.TRANSFER_FAILED, args);
						out.writeObject(tra);
					} else if (toAccount == null) {
						String[] args = new String[1];
						args[0] = "No deposit account exists.";
						Reply tra = new Reply(
								Reply.return_value.TRANSFER_FAILED, args);
						out.writeObject(tra);
					} else {
						if (account.withdraw(Double.parseDouble(request
								.getParameter()[2]))) {
							toAccount.deposit(Double.parseDouble(request
									.getParameter()[2]));
							String[] args = new String[1];
							args[0] = "" + account.getBalance();
							Reply tra = new Reply(
									Reply.return_value.TRANSFER_VALID, args);
							out.writeObject(tra);

						} else {
							String[] args = new String[1];
							args[0] = "There is not enough balance.";
							Reply tra = new Reply(
									Reply.return_value.TRANSFER_FAILED, args);
							out.writeObject(tra);
						}
					}
					break;

				case BALANCE:
					account = bank.getAccount(request.getParameter()[0]);

					if (account != null) {

						String[] args = new String[1];
						args[0] = "" + account.getBalance();
						Reply bal = new Reply(Reply.return_value.BALANCE_VALID,
								args);
						out.writeObject(bal);

					} else {
						String[] args = new String[1];
						args[0] = "The deposit account does not exist.";
						Reply bal = new Reply(
								Reply.return_value.BALANCE_FAILED, args);
						out.writeObject(bal);
					}
					break;

				case TOTAL_CAPITAL: {
					String[] args = new String[1];
					args[0] = "" + bank.getTotalBalance();
					Reply tot = new Reply(
							Reply.return_value.TOTAL_CAPITAL_VALID, args);
					out.writeObject(tot);

				}
					break;

				case CNT_CUSTOMER: {
					String[] args = new String[bank.getNumOfCustomers() + 1];

					args[0] = "" + bank.getNumOfCustomers();

					for (int i = 0; i < bank.getNumOfCustomers(); i++) {
						args[i + 1] = "" + bank.getCustomer(i).getId();
					}
					Reply cnt = new Reply(
							Reply.return_value.CNT_CUSTOMER_VALID, args);
					
					out.writeObject(cnt);
				}
					break;

				case CUSTOMER: {
					Customer customer = bank.getCustomerbyId(Integer
							.parseInt(request.getParameter()[0]));

					if (customer != null) {
						String[] args = new String[customer.getNumOfAccounts() + 4];

						args[0] = customer.getName();
						args[1] = customer.getTel();
						args[2] = customer.getAddress();
						args[3] = "" + customer.getNumOfAccounts();

						for (int i = 0; i < customer.getNumOfAccounts(); i++) {
							args[i + 4] = customer.getAccount(i).getAcctNum();
						}
						Reply cus = new Reply(
								Reply.return_value.CUSTOMER_VALID, args);
						out.writeObject(cus);
					}

					else {
						String[] args = new String[1];
						args[0] = "Customer information does not exist.";
						Reply cus = new Reply(
								Reply.return_value.CUSTOMER_FAILED, args);
						out.writeObject(cus);
					}
				}
					break;

				case MOD_CUSTOMER: {
					Customer customer = bank.getCustomerbyId(Integer
							.parseInt(request.getParameter()[0]));

					if (customer != null) {
						customer.setTel(request.getParameter()[1]);
						customer.setAddress(request.getParameter()[2]);
						Reply cus = new Reply(Reply.return_value.CUSTOMER_VALID);
						out.writeObject(cus);
					}

					else {
						String[] args = new String[1];
						args[0] = "Customer information does not exist.";
						Reply cus = new Reply(
								Reply.return_value.CUSTOMER_FAILED, args);
						out.writeObject(cus);
					}
				}
					break;

				case ADD_CUSTOMER: 
				{
					
					if(bank.addCustomer(Integer
							.parseInt(request.getParameter()[0]), request
							.getParameter()[1], request.getParameter()[2],
							request.getParameter()[3]))
					{
						Reply add = new Reply(Reply.return_value.ADD_CUSTOMER_VALID);
						out.writeObject(add);
					}
					else
					{
						String[] args = new String[1];
						args[0] = "Customer ID is a duplicate.";
						Reply add = new Reply(
								Reply.return_value.ADD_CUSTOMER_FAILED, args);
						out.writeObject(add);
					}
				}
					break;

				case DEL_CUSTOMER: {
					Customer customer = bank.getCustomerbyId(Integer
							.parseInt(request.getParameter()[0]));

					if (customer != null) {
						bank.delCustomer(customer);

						Reply del = new Reply(
								Reply.return_value.DEL_CUSTOMER_VALID);
						out.writeObject(del);
					}

					else {
						String[] args = new String[1];
						args[0] = "Customer information does not exist.";
						Reply del = new Reply(
								Reply.return_value.DEL_CUSTOMER_FAILED, args);
						out.writeObject(del);
					}
				}
					break;

				case ADD_ACCOUNT: {
					Customer customer = bank.getCustomerbyId(Integer
							.parseInt(request.getParameter()[0]));

					if (customer != null) {
						boolean result = bank.Account_exist(request
								.getParameter()[1]);

						if (!result) {
							customer
									.addAccount(new Account(request
											.getParameter()[1], request
											.getParameter()[2],
											Double.parseDouble(request
													.getParameter()[3])));

							Reply add = new Reply(
									Reply.return_value.ADD_ACCOUNT_VALID);
							out.writeObject(add);
						} else {
							String[] args = new String[1];
							args[0] = "Account number is duplicated.";
							Reply add = new Reply(
									Reply.return_value.ADD_ACCOUNT_FAILED, args);
							out.writeObject(add);
						}
					}

					else {
						String[] args = new String[1];
						args[0] = "Customer information does not exist.";
						Reply add = new Reply(
								Reply.return_value.ADD_ACCOUNT_FAILED, args);
						out.writeObject(add);
					}
				}
					break;

				case DEL_ACCOUNT: {
					Customer customer = bank
							.getCustomer(request.getParameter()[0]);
					if (customer != null) {
						Account acc = customer.getAccount(request
								.getParameter()[0]);

						if (acc != null) {
							customer.delAccount(acc);
							Reply del = new Reply(
									Reply.return_value.DEL_ACCOUNT_VALID);
							out.writeObject(del);
						} else {
							String[] args = new String[1];
							args[0] = "Account information does not exist.";
							Reply del = new Reply(
									Reply.return_value.DEL_ACCOUNT_FAILED, args);
							out.writeObject(del);
						}

					} else {
						String[] args = new String[1];
						args[0] = "Customer information does not exist.";
						Reply del = new Reply(
								Reply.return_value.DEL_ACCOUNT_FAILED, args);
						out.writeObject(del);
					}
				}
					break;

				case CNT_ACCOUNT: {
					String[] args = new String[bank.getNumOfAccounts() + 1];

					args[0] = "" + bank.getNumOfAccounts();
					int count = 1;

					for (int i = 0; i < bank.getNumOfCustomers(); i++) {
						for (int j = 0; j < bank.getCustomer(i)
								.getNumOfAccounts(); j++) {
							args[count++] = bank.getCustomer(i).getAccount(j)
									.getAcctNum();
						}
					}
					Reply cnt = new Reply(Reply.return_value.CNT_ACCOUNT_VALID,
							args);
					out.writeObject(cnt);
				}
					break;
				case ACCOUNT: {
					Customer customer = bank
							.getCustomer(request.getParameter()[0]);

					if (customer != null) {
						Account acc = customer.getAccount(request
								.getParameter()[0]);
						String[] args = new String[2];
						args[0] = "" + customer.getId();
						args[1] = "" + acc.getBalance();
						Reply ac = new Reply(Reply.return_value.ACCOUNT_VALID,
								args);
						out.writeObject(ac);
					} else {
						String[] args = new String[1];
						args[0] = "Account information does not exist.";
						Reply ac = new Reply(Reply.return_value.ACCOUNT_FAILED,
								args);
						out.writeObject(ac);
					}
				}
					break;
				}
				out.flush();
				out.reset();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			try {
				in.close();
				out.close();
				s.close();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
			return;
		}
	}
}
