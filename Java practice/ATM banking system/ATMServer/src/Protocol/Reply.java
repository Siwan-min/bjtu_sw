package Protocol;

import java.io.Serializable;

public class Reply implements Serializable {

	/**
	 * Generate Response
	 * @param ret Response type
	 */
	public Reply(return_value ret) {
		ReturnType = ret;
	}

	/**
	 * Generate Response
	 * @param ret Response type
	 * @param par Response parameter
	 */
	public Reply(return_value ret, String[] par) {
		ReturnType = ret;
		Parameter = par;
	}

	/**
	 * Accept response parameter.
	 * @return Response parameter
	 */
	public String[] getParameter() {
		return Parameter;
	}

	/**
	 * Accept response type
	 * @return Response type
	 */
	public return_value getReturnType() {
		return ReturnType;
	}

	/***
	 * Save response type
	 */
	private return_value ReturnType;
	
	/**
	 * Save response parameters
	 */
	private String[] Parameter;

	/**
	 * Response type
	 * 
	 * 	LOGIN_VALID, LOGIN_FAILED, 					// Login succeeded OR failed
	 *	LOGOUT_VALID, LOGOUT_FAILED, 				// Logout success OR failure
	 *	DEPOSIT_VALID, DEPOSIT_FAILED, 				// deposit successful OR failure
	 *	WITHDRAW_VALID, WITHDRAW_FAILED, 			// Withdrawal success OR failure
	 *	TRANSFER_VALID, TRANSFER_FAILED,		 	// Account transfer success OR failure
	 *	BALANCE_VALID, BALANCE_FAILED, 				// Balance inquiry success OR failure
	 *	TOTAL_CAPITAL_VALID, TOTAL_CAPITAL_FAILED, 	// Reservation request successful OR failure
	 *	CNT_CUSTOMER_VALID, CNT_CUSTOMER_FAILED,	// customer number, customer number request success OR failure				
	 *	CUSTOMER_VALID, CUSTOMER_FAILED,			// customer information request success OR failure		
	 *	MOD_CUSTOMER_VALID, MOD_CUSTOMER_FAILED,	// customer info modification successful OR failure			
	 *	ADD_CUSTOMER_VALID, ADD_CUSTOMER_FAILED,	// Customer addition successful OR failure		
	 *	DEL_CUSTOMER_VALID, DEL_CUSTOMER_FAILED,	// customer deletion success OR failure	
	 *	ADD_ACCOUNT_VALID, ADD_ACCOUNT_FAILED,		// Account addition successful OR failure	
	 *	DEL_ACCOUNT_VALID, DEL_ACCOUNT_FAILED,		// Account deletion success OR failure	
	 *	CNT_ACCOUNT_VALID, CNT_ACCOUNT_FAILED,		// Total number of accounts, account number request success OR failure
	 *		ACCOUNT_VALID,ACCOUNT_FAILED			// Account information request success OR failure
	 */
	public enum return_value { 						
		LOGIN_VALID, LOGIN_FAILED, 					// Login succeeded OR failed
		LOGOUT_VALID, LOGOUT_FAILED, 				// Logout success OR failure
		ADMIN_LOGIN_VALID,ADMIN_LOGIN_FAILED,
		ADMIN_LOGOUT_VALID,ADMIN_LOGOUT_FAILED,
		DEPOSIT_VALID, DEPOSIT_FAILED, 				// Deposit successful or failed
		WITHDRAW_VALID, WITHDRAW_FAILED, 			// Withdrawal success OR failure
		TRANSFER_VALID, TRANSFER_FAILED,		 	// Account transfer success OR failure 
		BALANCE_VALID, BALANCE_FAILED, 				// Balance inquiry success OR failure
		TOTAL_CAPITAL_VALID, TOTAL_CAPITAL_FAILED, 	// Retention amount request success or failure
		CNT_CUSTOMER_VALID, CNT_CUSTOMER_FAILED,	// Customer number and customer number request success OR failure				
		CUSTOMER_VALID, CUSTOMER_FAILED,			// Customer information request success OR failure		
		MOD_CUSTOMER_VALID, MOD_CUSTOMER_FAILED,	// Customer information modification successful OR failure		
		ADD_CUSTOMER_VALID, ADD_CUSTOMER_FAILED,	// Customer Successful OR Failed		
		DEL_CUSTOMER_VALID, DEL_CUSTOMER_FAILED,	// Customer deletion success OR failure	
		ADD_ACCOUNT_VALID, ADD_ACCOUNT_FAILED,		// Account addition success or failure	
		DEL_ACCOUNT_VALID, DEL_ACCOUNT_FAILED,		// Account deletion success OR failure	
		CNT_ACCOUNT_VALID, CNT_ACCOUNT_FAILED,		// Total number of accounts, account number request success OR failure
		ACCOUNT_VALID,ACCOUNT_FAILED				// Account information request success OR failure
	
	};

}
