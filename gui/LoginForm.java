package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import serialized.User;
import networking.NetworkFunctions;
public class LoginForm extends JPanel implements ActionListener {

	   /**
	 * 
	 */
	   private static final long serialVersionUID = 1L;
	   private JPanel panel;
	   private JLabel userField, passwordField, message;
	   private JTextField username;
	   private JPasswordField password;
	   private JButton submit;
	   private final String login_or_register;
	   private ObjectInputStream ois;
	   private ObjectOutputStream oos;
	   private ImageIcon loginImage;
	  
	public LoginForm(String login_or_register, ObjectOutputStream oos, ObjectInputStream ois) {
			super(new BorderLayout());
			
			Image img = null;
			try {
				img = ImageIO.read(getClass().getResource("/resources/login.png"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			
			this.login_or_register = login_or_register;
			this.oos = oos;
			this.ois = ois;
			
			userField = new JLabel();
			userField.setText("Username: ");
			username = new JTextField();
			
			passwordField = new JLabel();
			passwordField.setText("Password: ");
			password = new JPasswordField();
			
			submit = new JButton("Submit");
			submit.setIcon(new ImageIcon(img));
			message = new JLabel();
			
			panel = new JPanel(new GridLayout(3,1));
			
			panel.add(userField);
			panel.add(username);
			panel.add(passwordField);
			panel.add(password);
			panel.add(submit);
			panel.add(message);
			
			submit.addActionListener(this);
			add(panel, BorderLayout.CENTER);
		
	}
	
	public void createAndShowGUI() {
		
		JFrame frame = new JFrame("Please enter your credentials");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		LoginForm login = new LoginForm(login_or_register, oos, ois);
		login.setOpaque(true);
		frame.setContentPane(login);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String user = username.getText();
		char[] charPw = password.getPassword();
		String pw = new String(charPw);
		
		if(user == null) {
			
		}
		
		if(user != "" && pw != "") {
			
			
			if(login_or_register.equals("login")) {
				User userTry = new User(user, pw, true);
				boolean authenticated = NetworkFunctions.tryToLogin(userTry, oos, ois);
				if(authenticated) {
					JOptionPane.showMessageDialog(this, "Verified!");
					((Window) this.getRootPane().getParent()).dispose();
					DisplayUserProjects displayProjects = new DisplayUserProjects(user, oos, ois);
					displayProjects.loadProject();
				}
				else {
					message.setText("Invalid Credentials");
				}
					
			}else if(login_or_register.equals("register")) {
				User userTry = new User(user, pw, false);
				boolean authenticated = NetworkFunctions.registerUser(userTry, oos, ois);
				if(authenticated) {
					JOptionPane.showMessageDialog(this, "Successfully added!");
					((Window) this.getRootPane().getParent()).dispose();
					DisplayUserProjects displayProjects = new DisplayUserProjects(user, oos, ois);
					displayProjects.loadProject();
				}
			}
		
			else {
				message.setText("Username already exists");
			}
		}else {
			message.setText("Please enter credentials");
		}
		
		
	}
	
	public void addCredentials() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

}
