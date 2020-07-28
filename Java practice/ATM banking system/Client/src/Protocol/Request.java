package Protocol;
import java.io.Serializable;

public class Request implements Serializable {

 public Request(commands comm){
  CommandType = comm;
 }
 public Request(commands comm, String[] arg){
  CommandType = comm;
  Parameter = arg;
 }
 
 // Returns the incoming parameter.
 public String[] getParameter(){
  return Parameter;
 }
 // Tells the request type.
 public commands getCommandType(){
  return CommandType;
 }
 
 private commands CommandType;
 private String[] Parameter;
 // Enum constant
 public enum commands {// Instruction Enumeration
  // User protocol
  LOGIN,  // Login request
  LOGOUT,  // Logout request
  DEPOSIT, // Deposit request
  WITHDRAW, // Withdrawal request
  TRANSFER,  // Request a transfer
  BALANCE,  // Balance inquiry Request
  TOTAL_CAPITAL, // Request total balance of bank (for testing)
  ATM_EXIT,				// ATM termination
  
  
  // Manager protocol
  CNT_CUSTOMER, // Returns the number of customers and their IDs
  CUSTOMER, 	// View customer information through customer number
  MOD_CUSTOMER,	// Edit customer information
  ADD_CUSTOMER, // Add customers.
  DEL_CUSTOMER, // Delete customer information by customer number
  ADD_ACCOUNT,	// Add an account
  DEL_ACCOUNT,	// Delete account
  CNT_ACCOUNT,	// Request all account information
  ACCOUNT	// Request all account information
  
 };
}