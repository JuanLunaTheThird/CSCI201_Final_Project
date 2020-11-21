package gui;
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

import serialized.User;
import networking.ClientLoginFunctionality;
public class Guest_GUI extends JPanel implements ActionListener {

	   /**
	 * 
	 */
	   private static final long serialVersionUID = 1L;
	   private JPanel panel;
	   private JLabel userField, message;
	   private JTextField username;
	   private JButton submit;

	
	public Guest_GUI(String start) {
		super(new BorderLayout());
		if(start.equals("start")){
			userField = new JLabel();
			userField.setText("Username: ");
			username = new JTextField();
			

			
			submit = new JButton("Submit");
			message = new JLabel();
			panel = new JPanel(new GridLayout(2,1));
			
			panel.add(userField);
			panel.add(username);
		
			panel.add(submit);
			panel.add(message);
			
			submit.addActionListener(this);
			add(panel, BorderLayout.CENTER);
		}
	}
	
	public static void createAndShowGUI() {
		
		JFrame frame = new JFrame("Please enter the username of whose projects you would like to see");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Guest_GUI login = new Guest_GUI("start");
		login.setOpaque(true);
		frame.setContentPane(login);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String user = username.getText();
		
		if(user == null) {
			
		}
		
		if(user != "") {
			User userTry = new User(user);
			boolean authenticated = ClientLoginFunctionality.tryToLogin(userTry);
			if(authenticated) {
				JOptionPane.showMessageDialog(this, "Verified!");
				((Window) this.getRootPane().getParent()).dispose();
				DisplayUserProjects displayProjects = new DisplayUserProjects(user);
				displayProjects.loadProject();
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
