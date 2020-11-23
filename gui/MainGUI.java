package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class MainGUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private static String LOGIN = "Login";
    private static String REGISTER = "Register";
    private static String GUEST = "Guest Login";
    
    
    
	public MainGUI() {
		super(new BorderLayout());
		
		
		
		
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource("/resources/login.png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
		JButton login = new JButton(LOGIN);
		login.setActionCommand(LOGIN);
		login.addActionListener(this);
		login.setIcon(new ImageIcon(img));
		
		
		Image registerimg = null;
		try {
			registerimg = ImageIO.read(getClass().getResource("/resources/login.png"));;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		JButton register = new JButton(REGISTER);
		register.setActionCommand(REGISTER);
		register.addActionListener(this);
		register.setIcon(new ImageIcon(registerimg));
		
		
		
		JButton guest = new JButton(GUEST);
		guest.setActionCommand(GUEST);
		guest.addActionListener(this);
		guest.setIcon(new ImageIcon(registerimg));
		
		panel = new JPanel(new GridLayout(1,0));
		panel.add(login);
		panel.add(register);
		panel.add(guest);
		
		
		add(panel, BorderLayout.CENTER);
	}
	
	public static void createAndShowGUI() {
		
		JFrame frame = new JFrame("Please login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MainGUI credentials = new MainGUI();
		credentials.setOpaque(true);
		frame.setContentPane(credentials);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String juanServer = "104.12.140.24";
        try {
        	System.err.println("here");
			@SuppressWarnings("resource")
			Socket client = new Socket(juanServer, 8080);
			OutputStream os = client.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			InputStream is = client.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			
			
			if (LOGIN.equals(command)) {
				((Window) this.getRootPane().getParent()).dispose();
				LoginForm loginForm = new LoginForm("login", oos, ois);
				loginForm.addCredentials();
	        } else if (REGISTER.equals(command)) {
	        	((Window) this.getRootPane().getParent()).dispose();
	            LoginForm registerForm = new LoginForm("register", oos, ois);
	            registerForm.addCredentials();
	        }else if(GUEST.equals(GUEST)) {
	        	((Window) this.getRootPane().getParent()).dispose();
	        	GuestUserSelect guest = new GuestUserSelect(oos, ois);
	        	guest.addCredentials();
	        }
			
			
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
  
    }
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

}
	
	

