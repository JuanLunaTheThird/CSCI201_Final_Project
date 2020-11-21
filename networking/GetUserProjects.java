package networking;

import serialized.Packet;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import serialized.User;

public class GetUserProjects {

	public static String[] getUserProjects(User user) {
		InputStream is = null;
		ObjectInputStream clientInput = null;
		User authStatus = null;
		Socket client;
		String[] projects = null;
	
		try {
			String juanServer = "104.12.140.24";
			client = new Socket(juanServer, 8080);
			System.err.println("Successfully connected to the server");
			OutputStream os = client.getOutputStream();
			ObjectOutputStream clientOutput = new ObjectOutputStream(os);
				
			is = client.getInputStream();
			clientInput = new ObjectInputStream(is);
			
			ServerLoginAuthentication.sendUserToServer(user, clientOutput);
			System.err.println("Server sending auth status back");
			User ListOfUserProjects = ServerLoginAuthentication.receiveUserFromServer(clientInput);
			projects = ListOfUserProjects.getProjects();
			
		client.close();
			
		}catch(IOException e) {
				e.printStackTrace();
			}
		return projects;
	}
	
	
}
