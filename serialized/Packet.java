package serialized;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Packet implements Serializable {

	/**
	 * 
	 */
	
	private User login;
	private FileBytes file;
	private String packetType;
	private static String PACKET_TYPE_USER = "user";
	private static String PACKET_TYPE_FILE = "file";
	private static String PACKET_TYPE_ERROR = "error";
	
	
	public Packet(User User) {
		file = null;
		login = User;
		packetType = PACKET_TYPE_USER;
	}
	
	public Packet(FileBytes msg) {
		login = null;
		file = msg;
		packetType = PACKET_TYPE_FILE;
	}
	
	
	public Packet(User user, FileBytes file) {
		this.login = user;
		this.file = file;
		packetType = PACKET_TYPE_FILE;
	}
	
	public String getUsername(){
	
		if(packetType.equals(PACKET_TYPE_FILE)) {
			return file.getOwnerName();
		}
		return login.getUsername();
	}
	
	public String getPassword() {
		if(packetType.equals(PACKET_TYPE_FILE)) {
			System.err.println("You're getting the password on a packet with no User");
			return null;
		}
		
		return login.getPassword();
	}
	
	public String getMsg() {
		if(packetType.equals(PACKET_TYPE_USER)) {
			System.err.println("You're getting the message on a packet with no Message");
			return null;
		}
		return file.getRequestType();
	}
	
	
	public String getFileName() {
		if(packetType.equals(PACKET_TYPE_USER)) {
			System.err.println("You're getting the message on a packet with no Message");
			return null;
		}
		return file.getFileName();
	}
	
	public String getPacketType() {
		
		if(packetType.equals(PACKET_TYPE_USER)) {
			return PACKET_TYPE_USER;
		}else if(packetType.equals(PACKET_TYPE_FILE)) {
			return PACKET_TYPE_FILE;
		}
		
		
		System.err.println("This packet is neither a message nor login");
		return PACKET_TYPE_ERROR;
	}
	
	public User getUser() {
		return this.login;
	}
	
	public FileBytes getFile() {
		return this.file;
	}
	
	
}
