package serialized;
import java.io.Serializable;

public class User implements Serializable {
	

	
	private String username;
	private String password;
	private String[] userProjects;
	private boolean success;
	
	public User(String user, String pw, boolean s){
		this.username = user;
		this.password = pw;
		success = s;
	}
	
	public User(String user, String pw, boolean s, String[] projects){
		this.username = user;
		this.password = pw;
		success = s;
		userProjects = projects;
	}
	
	public String[] getProjects() {
		return userProjects;
	}
	
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean wasSuccessful() {
		return success;
	}
	
	
}
