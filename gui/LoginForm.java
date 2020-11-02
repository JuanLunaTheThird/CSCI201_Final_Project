import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import serialized.LoginAttempt;
import networking.ClientLoginFunctionality;
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

	
	public LoginForm(String start) {
		super(new BorderLayout());
		if(start.equals("start")){
			userField = new JLabel();
			userField.setText("Username: ");
			username = new JTextField();
			
			passwordField = new JLabel();
			passwordField.setText("Password: ");
			password = new JPasswordField();
			
			submit = new JButton("Submit");
			
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
	}
	
	public static void createAndShowGUI() {
		
		JFrame frame = new JFrame("Please enter your credentials");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		LoginForm login = new LoginForm("start");
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
			LoginAttempt userTry = new LoginAttempt(user, pw, false);
			boolean authenticated = ClientLoginFunctionality.tryToLogin(userTry);
			if(authenticated) {
				JOptionPane.showMessageDialog(this, "Verified!");
				((Window) this.getRootPane().getParent()).dispose();
				UserGui gui = new UserGui("");
				gui.startProject();
			}
			else {
				message.setText("Invalid Credentials");
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
