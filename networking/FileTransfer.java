package networking;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import serialized.FileBytes;
import serialized.Packet;


public class FileTransfer {
	private FileOutputStream fos;
	private ObjectOutputStream ObjOutput;
	private ObjectOutputStream serverOutput;
	private ObjectInputStream ObjInput;

	
	public FileTransfer(ObjectOutputStream oos, ObjectInputStream iis) {
		this.ObjOutput = oos;
		this.ObjInput = iis;
	}
	
	public FileTransfer(ObjectOutputStream oos) {
		this.serverOutput = oos;
	}
	
	
	public void sendFileToClient(String fname) {
		Path path = Paths.get(fname);
		try {
			byte[] bytes = Files.readAllBytes(path);
			
			FileBytes toClient = new FileBytes(fname, bytes);
			Packet packet = new Packet(toClient);
			serverOutput.writeObject(packet);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
	
	public String receiveFile(String projectdir) {
		fos = null;
		String targetdir = null;
		try {
			Packet packet = (Packet) ObjInput.readObject();
			FileBytes file = packet.getFile();
			byte[] bytes = file.getFile();
			targetdir = projectdir + File.separator + ".zip";
			int lastpost = projectdir.lastIndexOf(File.separator);
			
			if(lastpost == -1) {
				System.err.println("error processing string of project dir");
			}
			
			fos = new FileOutputStream(targetdir);
			fos.write(bytes);
			
			fos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return targetdir;
	}
	
	public void sendFileToServer(String fname, String project, String owner) {
		Path path = Paths.get(fname);
		try {
			byte[] bytes = Files.readAllBytes(path);
			
			
			
			FileBytes toClient = new FileBytes(owner, fname, project, "import", bytes);
			
			Packet packet = new Packet(toClient);
			ObjOutput.writeObject(packet);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}
