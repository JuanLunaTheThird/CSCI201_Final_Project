package serialized;
import java.io.Serializable;

public class Packet implements Serializable {

	/**
	 * 
	 */
	
	private LoginAttempt login;
	private Message message;
	private String packetType;
	private static String PACKET_TYPE_LOGIN = "login";
	private static String PACKET_TYPE_MESSAGE = "message";
	private static String PACKET_TYPE_ERROR = "error";
	
	
	public Packet(LoginAttempt loginAttempt) {
		message = null;
		login = loginAttempt;
		packetType = PACKET_TYPE_LOGIN;
	}
	
	public Packet(Message msg) {
		login = null;
		message = msg;
		packetType = PACKET_TYPE_MESSAGE;
	}
	
	public String getUsername(){
	
		if(packetType.equals(PACKET_TYPE_MESSAGE)) {
			System.err.println("You're getting the username on a packet with no LoginAttempt");
			return null;
		}
		return login.getUsername();
	}
	
	public String getPassword() {
		if(packetType.equals(PACKET_TYPE_MESSAGE)) {
			System.err.println("You're getting the password on a packet with no LoginAttempt");
			return null;
		}
		
		return login.getPassword();
	}
	
	public String getMsg() {
		if(packetType.equals(PACKET_TYPE_LOGIN)) {
			System.err.println("You're getting the message on a packet with no Message");
			return null;
		}
		return message.getMsg();
	}
	
	public byte[] getFile() {
		
		if(packetType.equals(PACKET_TYPE_LOGIN)) {
			System.err.println("You're getting the message on a packet with no Message");
			return null;
		}
		
		return message.getFile();
	}
	
	public String getFileName() {
		if(packetType.equals(PACKET_TYPE_LOGIN)) {
			System.err.println("You're getting the message on a packet with no Message");
			return null;
		}
		return message.getFileName();
	}
	
	public String returnPacketType() {
		
		if(packetType.equals(PACKET_TYPE_LOGIN)) {
			return PACKET_TYPE_LOGIN;
		}else if(packetType.equals(PACKET_TYPE_MESSAGE)) {
			return PACKET_TYPE_MESSAGE;
		}
		
		
		System.err.println("This packet is neither a message nor login");
		return PACKET_TYPE_ERROR;
		
	}
	
	
}
