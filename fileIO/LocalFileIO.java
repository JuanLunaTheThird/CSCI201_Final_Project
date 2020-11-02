package fileIO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalFileIO {
	
	
	
	public static void receiveFile(Object msg, String dir, String fname) {
		FileOutputStream fos = null;
		byte[] bytes = (byte[]) msg;
		try {
			fos = new FileOutputStream(dir + "\\" + fname);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fos.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static byte[] sendFile(String fname) {
		Path path = Paths.get(fname);
		System.err.println(path);
		try {
			byte[] bytes = Files.readAllBytes(path);

			return bytes;
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
}
