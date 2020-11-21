package serialized;

import java.io.Serializable;

public class FileBytes implements Serializable {
	private final String request;
	private final byte[] FileData;
	private final String fname;
	private final String project;
	
	
	public FileBytes(String t, byte[] data, String fname, String project) {
		this.request = t;
		this.FileData = data;
		this.fname = fname;
		this.project = project;
	}
	
	
	public String getRequestType() {
		return request;
	}
	
	public byte[] getFile() {
		return FileData;
	}
	
	public String getFileName() {
		return fname;
	}
	
	public String getProjectName() {
		return this.project;
	}
	
}

