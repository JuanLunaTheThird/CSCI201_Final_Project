package serverTests;
import java.io.*;
import java.net.Socket;
import java.util.*;
import serialized.*;

public class Client {

	
	
	public static void main(String[] args) {
		Socket s;
		OutputStream os;
		ObjectOutputStream ObjOutput;
		InputStream is;
		ObjectInputStream ObjInput;
		Scanner scan;
		FileBytes file;
		String fname;
	
		try {
			
			String juanServer = "104.12.140.24";
			
			s = new Socket(juanServer, 8080);
			System.err.println("Connected to localhost: 8080");
			
			
			
			os = s.getOutputStream();
			ObjOutput = new ObjectOutputStream(os);
			scan = new Scanner(System.in);
			
			is = s.getInputStream();
			ObjInput = new ObjectInputStream(is);
			fname = null;
			
			scan = new Scanner(System.in);
			
			while(true) {
				byte[] bytes = null;
				String byteString = null;
				
				
				FileBytes serverFile = (FileBytes) ObjInput.readObject();
				System.out.println("Server sent: " + serverFile.getRequestType());
				System.out.println("Client time to talk");
				String ClientMsg = scan.nextLine();
				
				if(serverFile.getFile() != null) {
					System.err.println("You received a file! The file is: " + serverFile.getFileName());
					
				}
				
			
				FileBytes toServer = new FileBytes(ClientMsg, bytes, fname, "");
				ObjOutput.writeObject(toServer);
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
			
