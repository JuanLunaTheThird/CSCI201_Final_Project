package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import serialized.User;
import networking.NetworkFunctions;
public class GuestUserSelect extends JPanel implements ActionListener {

	   /**
	 * 
	 */
	   private static final long serialVersionUID = 1L;
	   private JPanel panel;
	   private JLabel userField, message;
	   private JTextField username;
	   private JButton submit;
	   private ObjectInputStream ois;
	   private ObjectOutputStream oos;

	
	public GuestUserSelect(ObjectOutputStream oos, ObjectInputStream ois) {
			super(new BorderLayout());
			userField = new JLabel();
			userField.setText("Username: ");
			username = new JTextField();
			this.oos = oos;
			this.ois = ois;

			
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
	
	public void createAndShowGUI() {
		
		JFrame frame = new JFrame("Please enter the username of whose projects you would like to see");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GuestUserSelect login = new GuestUserSelect(oos, ois);
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
			User userTry = new User(user, "", "guest");
			boolean authenticated = NetworkFunctions.tryToLogin(userTry, oos, ois);
			if(authenticated) {
				JOptionPane.showMessageDialog(this, "Real user!");
				((Window) this.getRootPane().getParent()).dispose();
				GuestDisplayProjects displayProjects = new GuestDisplayProjects(user, oos, ois);
				displayProjects.loadProject();
			}
			else {
				message.setText("That user doesn't have any projects!");
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
