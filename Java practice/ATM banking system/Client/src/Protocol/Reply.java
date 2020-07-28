package Protocol;

import java.io.Serializable;

public class Reply implements Serializable {

	// Generate Response
	public Reply(return_value ret) {
		ReturnType = ret;
	}

	public Reply(return_value ret, String[] par) {
		ReturnType = ret;
		Parameter = par;
	}

	// Returns the incoming parameter.
	public String[] getParameter() {
		return Parameter;
	}

	// Tells the response type.
	public return_value getReturnType() {
		return ReturnType;
	}

	private return_value ReturnType;
	private String[] Parameter;

	// Enum constant
	public enum return_value { 						// Result of command execution
		LOGIN_VALID, LOGIN_FAILED, 					// Login success OR failure
		LOGOUT_VALID, LOGOUT_FAILED, 				// Logout Success OR Failure 
		DEPOSIT_VALID, DEPOSIT_FAILED, 				// Deposit successful or failed
		WITHDRAW_VALID, WITHDRAW_FAILED, 			// Withdrawal success OR failure
		TRANSFER_VALID, TRANSFER_FAILED,		 	// Account transfer success OR failure
		BALANCE_VALID, BALANCE_FAILED, 				// Balance inquiry success OR failure
		TOTAL_CAPITAL_VALID, TOTAL_CAPITAL_FAILED, 	// Assets request success or failure
		
		
		CNT_CUSTOMER_VALID, CNT_CUSTOMER_FAILED,					
		CUSTOMER_VALID, CUSTOMER_FAILED,						
		MOD_CUSTOMER_VALID, MOD_CUSTOMER_FAILED,					
		ADD_CUSTOMER_VALID, ADD_CUSTOMER_FAILED,					
		DEL_CUSTOMER_VALID, DEL_CUSTOMER_FAILED,				
		ADD_ACCOUNT_VALID, ADD_ACCOUNT_FAILED,				
		DEL_ACCOUNT_VALID, DEL_ACCOUNT_FAILED,					
		CNT_ACCOUNT_VALID, CNT_ACCOUNT_FAILED,
		ACCOUNT_VALID,ACCOUNT_FAILED
		// You can add a response to the service you want
	};

}
