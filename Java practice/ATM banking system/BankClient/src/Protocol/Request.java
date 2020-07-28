package Protocol;

import java.io.Serializable;

public class Request implements Serializable {

	/**
	 * Create request
	 * @param comm Request type
	 */
	public Request(commands comm) {
		CommandType = comm;
	}

	/**
	 * 요청을 생성
	 * @param comm Request type
	 * @param arg Request parameter
	 */
	public Request(commands comm, String[] arg) {
		CommandType = comm;
		Parameter = arg;
	}

	/**
	 * Returns the request parameter
	 * @return Request parameter
	 */
	public String[] getParameter() {
		return Parameter;
	}

	/**
	 * Get the request type
	 * @return Request type
	 */
	public commands getCommandType() {
		return CommandType;
	}

	/**
	 * Save request type
	 */
	private commands CommandType;

	/**
	 * Save request parameters
	 */
	private String[] Parameter;

	/**
	 * 응답 타입
	 *  LOGIN,  				// Login request 
	 * 	LOGOUT, 				// Logout request
	 *	DEPOSIT, 				// Deposit request
	 *	WITHDRAW, 				// Withdraw request
	 *	TRANSFER,  				// Request a transfer
	 *	BALANCE,  				// Balance inquiry Request
	 *	TOTAL_CAPITAL, 			// Request total balance of bank (for testing)
	 *	ATM_EXIT,				// ATM termination
	 *	CNT_CUSTOMER, 			// Returns the number of customers and their IDs
	 *	CUSTOMER, 				// View customer information through customer number
	 *	MOD_CUSTOMER,			// Edit customer information
	 *	ADD_CUSTOMER, 			// Add customer
	 *	DEL_CUSTOMER, 			// Delete customer information by customer number
	 *	ADD_ACCOUNT,			// Add an account
	 *	DEL_ACCOUNT,			// Delete account
	 *	CNT_ACCOUNT,			// Request all account information
	 *	ACCOUNT					// Request all account information
	 */
	public enum commands {

		LOGIN, // Login request
		LOGOUT, // Logout request
		ADMIN_LOGIN,
		ADMIN_LOGOUT,
		DEPOSIT, // Deposit request
		WITHDRAW, // Withdraw request 
		TRANSFER, // Request a transfer
		BALANCE, // Balance inquiry Request
		TOTAL_CAPITAL, // Request total balance of bank (for testing)
		ATM_EXIT, // ATM termination
		CNT_CUSTOMER, // Returns the number of customers and their IDs
		CUSTOMER, // View customer information through customer number
		MOD_CUSTOMER, // Edit customer information
		ADD_CUSTOMER, // Add customers.
		DEL_CUSTOMER, // Delete customer information by customer number
		ADD_ACCOUNT, // Add an account
		DEL_ACCOUNT, // Delete account
		CNT_ACCOUNT, // Request all account information
		ACCOUNT
		//Request all account information
	};
}