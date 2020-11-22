package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;
import javax.swing.JTextField;

import networking.NetworkFunctions;
import serialized.User;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
@SuppressWarnings("serial")
public class GuestDisplayProjects extends JPanel implements ActionListener {
	private JPanel panel;
	private String username;
	
	private final String SELECT_PROJ = "SELECT_PROJ";
	private JLabel projectField;
	private JTextField projName;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private User projects_and_owners;
	public GuestDisplayProjects(String username,  ObjectOutputStream oos, ObjectInputStream ois) {
		
		
		super(new BorderLayout());	
		this.username = username;
		this.ois = ois;
		this.oos = oos;
		projects_and_owners = loadUserProjects();
		int num_projects = projects_and_owners.getProjects().length;
		
		String[] projects = new String[num_projects];
		
		for(int i =0; i < num_projects; ++i) {
			projects[i] = "Owner: " + projects_and_owners.getProjectOwners()[i] + "Project: " + projects_and_owners.getProjects()[i];
		}
		
		projName = new JTextField();
		
		if(projects.length > 0) {
			JComboBox<String> projectList = new JComboBox<String>(projects);
			projectList.setActionCommand(SELECT_PROJ);
			projectList.addActionListener(this);
			projectField = new JLabel();
			projectField.setText("Select a working project");
			panel = new JPanel(new GridLayout(1,1));
			panel.add(projectList);
			
		}else {
			panel = new JPanel(new GridLayout(1,1));
		}
		
		
		panel.add(projName);
		add(panel, BorderLayout.CENTER);
	}

	
	
	
	private User loadUserProjects() {
		User projectRequest = new User(username, "", "guest");
		User projects = null;
		projects = NetworkFunctions.getUserProjects(projectRequest, oos, ois);
		return projects;
	}
	
	public void createAndShowGUI(){
		JFrame frame = new JFrame("Select a current project or register a new one");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GuestDisplayProjects displayProjects = new GuestDisplayProjects(username, oos, ois);
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
			JComboBox<?> p = (JComboBox<?>)e.getSource();
			int idx = p.getSelectedIndex();
			String project_owner = projects_and_owners.getProjectOwners()[idx];
			String project = projects_and_owners.getProjects()[idx];
			
			System.err.println(project_owner + " " + project);
			
			
			User client = new User(username, project);
			((Window) this.getRootPane().getParent()).dispose();
			GuestDisplayPanel gui = new GuestDisplayPanel(client,false, oos, ois);
			gui.startProject();
		}

	}
}
