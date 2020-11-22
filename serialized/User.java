package serialized;
import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {
	

	
	private String username;
	private String password;
	private String[] userProjects;
	private String[] project_owners;
	private String project;
	private String userType;
	private boolean RegisterOrLogin;
	private String owner;
	
	public User(String user, String pw, boolean s){
		this.username = user;
		this.password = pw;
		RegisterOrLogin = s;
		if(s) {
			this.userType = "login";
		}else {
			this.userType = "register";
		}
	}
	
	public User(String user, String projectReq) {
		this.username = user;
		this.project = projectReq;
	}
	
	public User(String user, String project, String type) {
		this.username = user;
		this.project = project;
		this.userType = type;
	}
	
	public User(String user, String owner, String project, String type) {
		this.username = user;
		this.project = project;
		this.userType = type;
		this.owner = owner;
	}
	
	public User(String user) {
		this.username = user;
	}
	
	
	public User(String user, String[] projects, String[] owners){
		this.username = user;
		this.userProjects = projects;
		this.project_owners =owners;
	}
	
	public String[] getProjects() {
		return userProjects;
	}
	
	public String[] getProjectOwners() {
		return this.project_owners;
	}
	
	public String getProject() {
		return project;
	}
	
	public String getProjectOwner() {
		return owner;
	}
	
	public String getUserType() {
		return this.userType;
	}
	
	
	public String getUsername() {
		return username;
	}
	
	public void setSuccess(boolean s) {
		this.RegisterOrLogin = s;
	}
	
	
	public String getPassword() {
		return password;
	}
	
	//Use this function server-side to see if the user is trying to login or register
	public boolean wasSuccessful() {
		return RegisterOrLogin;
	}
	
	
}
