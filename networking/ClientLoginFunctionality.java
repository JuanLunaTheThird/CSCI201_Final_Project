package networking;

import java.net.*;

import serialized.LoginAttempt;
import java.io.*;

public class ClientLoginFunctionality {
	
	public static boolean tryToLogin(LoginAttempt login) {
	
		InputStream is = null;
		ObjectInputStream clientInput = null;
		LoginAttempt authStatus = null;
		Socket client;
		boolean success = false;
	
		try {
			String juanServer = "104.12.140.24";
			client = new Socket(juanServer, 8080);
			System.err.println("Successfully connected to the server");
			OutputStream os = client.getOutputStream();
			ObjectOutputStream clientOutput = new ObjectOutputStream(os);
				
			is = client.getInputStream();
			clientInput = new ObjectInputStream(is);
			
			ServerLoginAuthentication.sendLoginAttemptToServer(login, clientOutput);
			System.err.println("Server sending auth status back");
			authStatus = ServerLoginAuthentication.receiveLoginAttemptFromServer(clientInput);
			
			if(authStatus!= null) {
				if(authStatus.wasSuccessful()) {
					success = true;
				}
			}
			
		client.close();
			
		}catch(IOException e) {
				e.printStackTrace();
			}
		return success;
	}
	
	
	public static boolean registerUser(LoginAttempt register) {
		
		InputStream is = null;
		ObjectInputStream clientInput = null;
		LoginAttempt authStatus = null;
		Socket client;
		boolean success = false;
	
		try {
			String juanServer = "104.12.140.24";
			client = new Socket(juanServer, 8080);
			System.err.println("Successfully connected to the server");
			OutputStream os = client.getOutputStream();
			ObjectOutputStream clientOutput = new ObjectOutputStream(os);
				
			is = client.getInputStream();
			clientInput = new ObjectInputStream(is);
			
			ServerLoginAuthentication.sendLoginAttemptToServer(register, clientOutput);
			System.err.println("Server sending auth status back");
			authStatus = ServerLoginAuthentication.receiveLoginAttemptFromServer(clientInput);
			
			if(authStatus!= null) {
				if(authStatus.wasSuccessful()) {
					success = true;
				}
			}
			
		client.close();
			
		}catch(IOException e) {
				e.printStackTrace();
			}
		return success;
	}
	
}
