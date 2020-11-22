package fileIO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalFileIO {
	
	
	
	public static String receiveFile(Object msg, String dir, String fname) {
		FileOutputStream fos = null;
		byte[] bytes = (byte[]) msg;
		String wrote_to = dir + File.separator + fname;
		File mkdir = new File(dir);
		mkdir.mkdirs();
		System.err.println("in receiveFile function dir is " + dir);
		System.err.println(" and the filename is " + fname);
		
		
		
		
		try {
			fos = new FileOutputStream(wrote_to);
		} catch (FileNotFoundException e) {
			System.err.println(wrote_to);
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
		return wrote_to;
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
