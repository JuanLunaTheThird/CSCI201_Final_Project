package serialized;

import java.io.Serializable;

public class Message implements Serializable {
	private final String text;
	private final byte[] FileData;
	private final String fname;
	
	
	public Message(String t, byte[] data, String fname) {
		this.text = t;
		this.FileData = data;
		this.fname = fname;
	}
	
	
	public String getMsg() {
		return text;
	}
	
	public byte[] getFile() {
		return FileData;
	}
	
	public String getFileName() {
		return fname;
	}
	
}

