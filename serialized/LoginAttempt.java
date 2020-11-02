package serialized;
import java.io.Serializable;

public class LoginAttempt implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private boolean success;
	
	public LoginAttempt(String user, String pw, boolean s){
		this.username = user;
		this.password = pw;
		success = s;
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
