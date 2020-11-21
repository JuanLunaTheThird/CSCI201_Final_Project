package networking;

import java.net.*;

import serialized.User;
import java.io.*;

public class ClientLoginFunctionality {
	
	
	
	
	public static boolean tryToLogin(User login) {
	
		InputStream is = null;
		ObjectInputStream clientInput = null;
		User authStatus = null;
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
			
			ServerLoginAuthentication.sendUserToServer(login, clientOutput);
			System.err.println("Server sending auth status back");
			authStatus = ServerLoginAuthentication.receiveUserFromServer(clientInput);
			
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
	
	public static boolean sendUsertoAdd(User toAdd) {
		InputStream is = null;
		ObjectInputStream clientInput = null;
		User authStatus = null;
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
			
			ServerLoginAuthentication.sendUserToServer(toAdd, clientOutput);
			System.err.println("Server sending if user was successfully added");
			authStatus = ServerLoginAuthentication.receiveUserFromServer(clientInput);
			
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
	
	
	
	
	public static boolean registerUser(User register) {
		
		InputStream is = null;
		ObjectInputStream clientInput = null;
		User authStatus = null;
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
			
			ServerLoginAuthentication.sendUserToServer(register, clientOutput);
			System.err.println("Server sending auth status back");
			authStatus = ServerLoginAuthentication.receiveUserFromServer(clientInput);
			
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
