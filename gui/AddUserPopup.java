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
public class AddUserPopup extends JPanel implements ActionListener {

	   /**
	 * 
	 */
	   private static final long serialVersionUID = 1L;
	   private JPanel panel;
	   private JLabel userField, message;
	   private JTextField username;
	   private JButton submit;
	   private final String reqType = "add_user";
	   private final String project;
	
	public AddUserPopup(String project) {
		super(new BorderLayout());
		this.project = project;
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
	
	public void createAndShowGUI() {
		
		JFrame frame = new JFrame("Please enter your credentials");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String p = project;
		AddUserPopup login = new AddUserPopup(p);
		login.setOpaque(true);
		frame.setContentPane(login);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String user = username.getText();
		
		User toAdd = new User(user, project, reqType);
		boolean success = ClientLoginFunctionality.sendUsertoAdd(toAdd);
		if(success) {
			JOptionPane.showMessageDialog(this, "Added!");
			((Window) this.getRootPane().getParent()).dispose();
		}else {
			message.setText("Problem adding that user");
		}
	}
	
	public void addUser() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

}
