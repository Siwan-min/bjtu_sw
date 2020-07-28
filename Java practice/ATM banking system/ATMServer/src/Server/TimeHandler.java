package Server;

import java.io.*;

import Banking.*;

/**
 * To create a thread, inherit the Runnable interface
 * every hour, Store the server's DATA
 * The BANK class is serializable.
 * Store data in the file data.txt via ObjextOutputStream.
 */
class TimeHandler implements Runnable {

	static Bank bank;

	/**
	 * Constructor for TimeHandler
	 * @param _bank Bank object
	 */
	TimeHandler(Bank _bank) {

		bank = _bank;
	}

	/**
	 * Thread processing routine
	 */
	public void run() {
		
		while(true)
		{// Repeated during Thread operation.
			try	{
				/* Store data in data.txt file every 10 seconds.*/
				ObjectOutputStream output = new ObjectOutputStream( new FileOutputStream( "data.txt", false ) );
				output.writeObject( bank );
				output.flush();
				output.reset();
				output.close();
				Thread.sleep(1000*10);
			}
			catch( Exception e )
			{/* Terminate thread processing routine in case of exception*/
				e.printStackTrace();
				return;
			}	
		}
	}
}
