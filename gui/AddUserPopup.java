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
	   private final String project_owner;
	   private ObjectInputStream ois;
	   private ObjectOutputStream oos;
	
	public AddUserPopup(String project, String project_owner, ObjectOutputStream oos, ObjectInputStream ois) {
		super(new BorderLayout());
		
		this.oos = oos;
		this.ois = ois;
		this.project = project;
		this.project_owner = project_owner;
		
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
		AddUserPopup login = new AddUserPopup(project, project_owner, oos, ois);
		login.setOpaque(true);
		frame.setContentPane(login);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String user = username.getText();
		
		User toAdd = new User(user, project_owner, project, reqType);
		boolean success = NetworkFunctions.sendUsertoAdd(toAdd, oos, ois);
		if(success) {
			JOptionPane.showMessageDialog(this, "Added!");
			((Window) this.getRootPane().getParent()).dispose();
		}else {
			message.setText("Problem adding that user");
			((Window) this.getRootPane().getParent()).dispose();
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
