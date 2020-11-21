package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fileIO.LocalFileIO;
import serialized.Packet;
import serialized.User;
import serialized.FileBytes;
public class ClientHandler extends Thread {
	
	final private ObjectInputStream ObjInput;
	final private ObjectOutputStream ObjOutput;
	final Socket socket;
	private Packet userPacket;
	final static String PACKET_TYPE_USER = "user";
	final static String PACKET_TYPE_FILE = "file";
	final static String PACKET_TYPE_ERROR = "error";
	final static String FILE_TYPE_EXPORT = "export";
	final static String FILE_TYPE_IMPORT = "import";
	final static String USER_TYPE_LOGIN = "login";
	final static String USER_TYPE_REGISTER = "register";
	final static String USER_TYPE_ADD_TO_PROJECT = "add_user";
	final static String USER_TYPE_REGISTER_PROJECT = "register_project";
	
	
	public ClientHandler(Socket s, ObjectInputStream ois, ObjectOutputStream oos)
	{
		this.socket = s;
		this.ObjInput = ois;
		this.ObjOutput = oos;
	}
	
	@Override
	public void run() {
		
		
		while(true) {
			
			try {
				userPacket = (Packet) ObjInput.readObject();
				
				String packet_type = userPacket.getPacketType();
				
				switch(packet_type) {
					case PACKET_TYPE_USER:
						//function for connecting to sql database and checking user credentials
						User user = userPacket.getUser();
						String userType = user.getUserType();
						switch(userType) {
						
						case USER_TYPE_LOGIN:
							//login sql code
							break;
						case USER_TYPE_REGISTER:
							//register sql code
							break;
						case USER_TYPE_ADD_TO_PROJECT:
							// sql code to add a user to a project
							break;
						case USER_TYPE_REGISTER_PROJECT:
							// sql function to register a project
							break;
						}
							break;
						
					case PACKET_TYPE_FILE:
						
						FileBytes file = userPacket.getFile();
						
						String requestType = file.getRequestType();
						
						switch(requestType) {
							case FILE_TYPE_IMPORT: //case for sending a project to user
								String Project = file.getProjectName();
								
								//sql function for finding directory of project name
								
								
							case FILE_TYPE_EXPORT://case for exporting a project to user
								String project = file.getProjectName();
								//SQL function for finding directory of current project
								String dir = "C:\\Users\\juanl\\Desktop\\Prohub";
								LocalFileIO.receiveFile(file, dir, file.getFileName());
						}
							
					case PACKET_TYPE_ERROR:
						break;
				}
				
				
				
				
				
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			
			
			
		}
	
	}
	
}
