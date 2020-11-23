package serialized;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FileBytes implements Serializable {
	private String request;
	private final byte[] FileData;
	private final String fname;
	private String project;
	private String username;
	private String config_file;
	
	public FileBytes(String t, byte[] data, String fname, String project) {
		this.request = t;
		this.FileData = data;
		this.fname = fname;
		this.project = project;
	}
	
	public FileBytes(String fname, byte[] file, String jsonString) {
		this.fname = fname;
		this.FileData = file;
		this.config_file = jsonString;
	}
	
	public FileBytes(String username, String fname, String project,  String request, String jsonString, byte[] file) {
		this.username = username;
		this.fname = fname;
		this.FileData = file;
		this.request = request;
		this.project = project;
		this.config_file = jsonString;
	}
	
	public String getJsonString() {
		return this.config_file;
	}
	
	public String getOwnerName() {
		return this.username;
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

