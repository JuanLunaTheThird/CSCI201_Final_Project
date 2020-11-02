package serverTests;

import java.net.*;


import serialized.LoginAttempt;
import serialized.Packet;
import networking.ServerLoginAuthentication;

import java.io.*;

public class TestingLoginServer {

	public static void main(String[] args) {
		
		ServerSocket server;
		OutputStream os;
		ObjectOutputStream serverOutput;
		InputStream is;
		ObjectInputStream serverInput;
		LoginAttempt login, authStatus;
		Socket client;
		Packet packet;
		
		try {
			server = new ServerSocket(8080);
			
			
			while(true) {
				authStatus = null;
				login = null;
				client = server.accept();
				System.err.println(client.getInetAddress());
				os = client.getOutputStream();
				serverOutput = new ObjectOutputStream(os);
				is = client.getInputStream();
				serverInput = new ObjectInputStream(is);
				
				System.err.println("trying to read login");
				packet = ServerLoginAuthentication.receiveClientLogin(serverInput);
				System.err.println("Got packet");
				
				if(packet != null) {
					System.out.println("username: " + packet.getUsername() + " password:" + packet.getPassword() );
					
					if(packet.getUsername().equals("pog") && packet.getPassword().equals("pog")) {
						System.err.println("User authenticated");
						authStatus = new LoginAttempt(packet.getUsername(), packet.getPassword(), true);
						ServerLoginAuthentication.sendLoginAttemptToClient(serverOutput, authStatus);
					}else {
						authStatus = new LoginAttempt(packet.getUsername(), packet.getPassword(), false);
						ServerLoginAuthentication.sendLoginAttemptToClient(serverOutput, authStatus);
					}
				}
				
				
			}
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		

	}

}
