package serverTests;



import java.net.*;


import serialized.User;
import serialized.Packet;
import networking.ServerLoginAuthentication;

import java.io.*;

public class Server {

	public static void main(String[] args) {
		
		ServerSocket server;
		OutputStream os;
		ObjectOutputStream serverOutput;
		InputStream is;
		ObjectInputStream serverInput;
		User login, authStatus;
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
						authStatus = new User(packet.getUsername(), packet.getPassword(), true);
						ServerLoginAuthentication.sendUserToClient(serverOutput, authStatus);
					}else {
						authStatus = new User(packet.getUsername(), packet.getPassword(), false);
						ServerLoginAuthentication.sendUserToClient(serverOutput, authStatus);
					}
				}
				
				
			}
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		

	}

}

