package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import networking.GetUserProjects;
import serialized.Packet;
import serialized.User;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class GuestDisplayProjects extends JPanel implements ActionListener {
	private JPanel panel;
	private String username;
	
	private final String SELECT_PROJ = "SELECT_PROJ";
	private JLabel projectField;
	private JTextField projName;
	
	
	public GuestDisplayProjects(String username) {
		
		
		super(new BorderLayout());	
		this.username = username;
		String [] projects = {"On mamas", "Diego"};
				//loadUserProjects();
		JComboBox<String> projectList = new JComboBox<String>(projects);
		
		projectList.setActionCommand(SELECT_PROJ);
		projectList.addActionListener(this);
		
		panel = new JPanel(new GridLayout(2,1));

		
		projectField = new JLabel();
		projectField.setText("Select a project");
		projName = new JTextField();
		
		
		panel.add(projectField);
		panel.add(projName);
		panel.add(projectList);
		add(panel, BorderLayout.CENTER);
	}

	
	
	
	private String[] loadUserProjects() {
		User projectRequest = new User(username);
		String[] projects = null;
		projects = GetUserProjects.getUserProjects(projectRequest);
		return projects;
	}
	
	public void createAndShowGUI(){
		JFrame frame = new JFrame("Select a current project or register a new one");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GuestDisplayProjects displayProjects = new GuestDisplayProjects(username);
		displayProjects.setOpaque(true);
		frame.setContentPane(displayProjects);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void loadProject() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals(SELECT_PROJ)) {
			JComboBox p = (JComboBox)e.getSource();
			String project = (String)p.getSelectedItem();
			User client = new User(username, project); 
			((Window) this.getRootPane().getParent()).dispose();
			FileDisplayPanel gui = new FileDisplayPanel(client, false);
			gui.startProject();
		}

	}
}