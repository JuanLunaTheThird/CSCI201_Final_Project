package networking;

import java.io.*;
import serialized.*;

public class ServerLoginAuthentication {
	/* Need to send a packet so that the server can receive all types of messages
	 * Otherwise it won't know if it's trying to verify a user or
	 * send a project
	 */
	
	
	
	public static Packet receiveClientLogin(ObjectInputStream ServerObjectInStream) {
		
		LoginAttempt login = null;
		
		try {
			login = (LoginAttempt) ServerObjectInStream.readObject();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
		}
		
		return new Packet(login);
	}
	
	public static void sendLoginAttemptToClient(ObjectOutputStream ServerObjectOutStream, LoginAttempt authenticatedUser) {
		
		try {
			ServerObjectOutStream.writeObject(authenticatedUser);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static LoginAttempt receiveLoginAttemptFromServer(ObjectInputStream ClientObjectInStream) {
		
		LoginAttempt authenticatedUser = null;
		try {
			authenticatedUser = (LoginAttempt) ClientObjectInStream.readObject();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
		return authenticatedUser;
	}
	
	public static void sendLoginAttemptToServer(LoginAttempt attempt, ObjectOutputStream ClientObjectOutStream) {
		
		Packet packet = new Packet(attempt);
	
		try {
			ClientObjectOutStream.writeObject(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
