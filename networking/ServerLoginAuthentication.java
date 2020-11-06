package networking;

import java.io.*;
import serialized.*;

public class ServerLoginAuthentication {
	/* Need to send a packet so that the server can receive all types of messages
	 * Otherwise it won't know if it's trying to verify a user or
	 * send a project
	 */
	
	
	
	public static Packet receiveClientLogin(ObjectInputStream ServerObjectInStream) {
		
		Packet login = null;
		
		try {
			login = (Packet) ServerObjectInStream.readObject();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
		}
		
		return login;
	}
	
	public static void sendUserToClient(ObjectOutputStream ServerObjectOutStream, User authenticatedUser) {
		
		try {
			ServerObjectOutStream.writeObject(authenticatedUser);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static User receiveUserFromServer(ObjectInputStream ClientObjectInStream) {
		
		User authenticatedUser = null;
		try {
			authenticatedUser = (User) ClientObjectInStream.readObject();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
		return authenticatedUser;
	}
	
	public static void sendUserToServer(User attempt, ObjectOutputStream ClientObjectOutStream) {
		
		Packet packet = new Packet(attempt);
	
		try {
			ClientObjectOutStream.writeObject(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
