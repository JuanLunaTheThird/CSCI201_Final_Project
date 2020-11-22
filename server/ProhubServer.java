package server;

import java.io.IOException;
import java.net.*;

import networking.ClientHandler;

public class ProhubServer {

	public static void main(String[] args){
		int clients_serviced = 0;
		try {
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(8080);
		
			while(true) {
				Socket client = server.accept();
				System.err.println("Currently servicing someone from " + client.getInetAddress());
				ClientHandler handle = new ClientHandler(client);
				handle.start();
				clients_serviced++;
				System.err.println("have currently serviced " + clients_serviced + " clients");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
}
