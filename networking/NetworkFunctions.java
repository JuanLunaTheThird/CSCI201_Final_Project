package networking;

import serialized.Packet;
import serialized.User;
import java.io.*;

import fileIO.FileZip;

public class NetworkFunctions {
	
	public static User getUserProjects(User user, ObjectOutputStream clientOutput, ObjectInputStream clientInput) {
		
		NetworkFunctions.sendUserToServer(user, clientOutput);
		System.err.println("Server sending list of projects back");
		User ListOfUserProjects = NetworkFunctions.receiveUserFromServer(clientInput);
		
		return ListOfUserProjects;
	}
	
	
	
	
	public static boolean sendGuest(User guest, ObjectOutputStream clientOutput, ObjectInputStream clientInput) {
		User authStatus = null;
		boolean success = false;
	
		sendUserToServer(guest, clientOutput);
		System.err.println("Server sending auth status back");
		authStatus = receiveUserFromServer(clientInput);
		
		if(authStatus!= null) {
			if(authStatus.wasSuccessful()) {
				success = true;
			}
		}
		return success;
	}
	
	public static void importProject(User user, String projectdir, ObjectOutputStream clientOutput, ObjectInputStream clientInput) {
		sendUserToServer(user, clientOutput);
		System.err.println("Receiving file for project " + user.getProject() );
		FileTransfer receive_file = new FileTransfer(clientOutput, clientInput);
		String srcdir = receive_file.receiveFile(projectdir);
		FileZip unzip = new FileZip(srcdir, projectdir);
    	unzip.unzipDir();
    	File toDelete = new File(srcdir);
    	toDelete.delete();
	}
	
	
	
	
	public static boolean tryToLogin(User login, ObjectOutputStream clientOutput, ObjectInputStream clientInput) {
	
	
		User authStatus = null;
		boolean success = false;
		sendUserToServer(login, clientOutput);
		System.err.println("Server sending auth status back");
		authStatus = receiveUserFromServer(clientInput);
		
		if(authStatus!= null) {
			if(authStatus.wasSuccessful()) {
				success = true;
			}
		}
		return success;
	}
	
	public static boolean sendUsertoAdd(User toAdd, ObjectOutputStream clientOutput, ObjectInputStream clientInput) {
		
		User authStatus = null;
	
		boolean success = false;
	
		sendUserToServer(toAdd, clientOutput);
		System.err.println("Server sending if user was successfully added");
		authStatus = receiveUserFromServer(clientInput);
		
		if(authStatus!= null) {
			if(authStatus.wasSuccessful()) {
				success = true;
			}
		}
		return success;
	}
	
	
	
	
	public static boolean registerUser(User register, ObjectOutputStream clientOutput, ObjectInputStream clientInput) {
		
		User authStatus = null;
		boolean success = false;
	
		sendUserToServer(register, clientOutput);
		System.err.println("Server sending auth status back");
		authStatus = receiveUserFromServer(clientInput);
		
		if(authStatus!= null) {
			if(authStatus.wasSuccessful()) {
				success = true;
			}
		}
		return success;
	}
	
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
