package networking;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import serialized.FileBytes;
import serialized.Packet;


public class FileTransfer {
	private Socket socket;
	private FileOutputStream fos;
	private String projectdir;
	
	public FileTransfer(Socket client){
		socket = client;
		fos = null;
	}
	
	public FileTransfer(String projectdir){
		try {
			String juanServer = "104.12.140.24";
			socket = new Socket(juanServer, 8080);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.projectdir = projectdir;
		fos = null;
	}
	
	public void receiveFile() {
		fos = null;
		InputStream is;
		try {
			is = socket.getInputStream();
			ObjectInputStream ObjInput = new ObjectInputStream(is);
			Packet packet = (Packet) ObjInput.readObject();
			FileBytes file = packet.getFile();
			byte[] bytes = file.getFile();
			
			int lastpost = projectdir.lastIndexOf(File.separator);
			
			if(lastpost == -1) {
				System.err.println("error processing string of project dir");
			}
			
			fos = new FileOutputStream(projectdir + File.separator + ".zip");
			fos.write(bytes);
			
			fos.close();
			ObjInput.close();
			socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendFile(String fname) {
		Path path = Paths.get(fname);
		try {
			byte[] bytes = Files.readAllBytes(path);
			
			OutputStream is = socket.getOutputStream();
			ObjectOutputStream ObjOutput = new ObjectOutputStream(is);
			
			FileBytes toClient = new FileBytes("export", bytes,"","");
			
			Packet packet = new Packet(toClient);
			ObjOutput.writeObject(packet);
			ObjOutput.close();
			is.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}
