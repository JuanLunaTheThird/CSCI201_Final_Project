package gui;
import java.awt.BorderLayout;
import json.*;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;

import networking.NetworkFunctions;
import serialized.User;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
@SuppressWarnings("serial")
public class DisplayUserProjects extends JPanel implements ActionListener {
	private JPanel panel;
	private String username;
	private JButton newProject;
	private final String NEW_PROJ = "NEW_PROJ";
	private final String SELECT_PROJ = "SELECT_PROJ";
	private JLabel projectField;
	private JTextField projName;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private User projects_and_owners;
	
	
	public DisplayUserProjects(String username,  ObjectOutputStream oos, ObjectInputStream ois) {
		
		
		super(new BorderLayout());
		this.oos = oos;
		this.ois = ois;
		this.username = username;
		projects_and_owners = loadUserProjects();
		int num_projects = projects_and_owners.getProjects().length;
		
		String[] projects = new String[num_projects];
		
		for(int i =0; i < num_projects; ++i) {
			projects[i] = "Owner: " + projects_and_owners.getProjectOwners()[i] + " Project: " + projects_and_owners.getProjects()[i];
		}
		
	
		newProject = new JButton("Register new Project");
		newProject.setActionCommand(NEW_PROJ);
		newProject.addActionListener(this);
		
		
		

		
		
		projName = new JTextField();
		
		if(projects.length > 0) {
			configFile();
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
		panel.add(newProject);
		add(panel, BorderLayout.CENTER);
		
		
	}

	private void configFile() {
		Projects config_file = GsonFunctions.parseJson();
		String[] projects = projects_and_owners.getProjects();
		String[] owners = projects_and_owners.getProjectOwners();
		if(config_file != null) {
			for(int i = 0; i < projects.length; ++i) {
				ProjectNotes already_in_config = null;
				already_in_config = config_file.projectExists(projects[i], owners[i]);
				if(already_in_config == null) {
					config_file.addProject(projects[i], owners[i]);
				}	
			}
			GsonFunctions.updateJson(config_file.toJson());
		}else {
			ArrayList<ProjectNotes> initConfig = new ArrayList<ProjectNotes>();
			Projects new_config = new Projects(initConfig);
			for(int i = 0; i < projects.length; ++i) {
				new_config.addProject(projects[i], owners[i]);
			}
			GsonFunctions.initConfigFile(new_config.getProjects());
		}
		
		
	}
	
	
	private User loadUserProjects() {
		User projectRequest = new User(username, "", "guest");
		User projects = NetworkFunctions.getUserProjects(projectRequest, oos, ois);
		return projects;
	}
	
	public void createAndShowGUI(){
		JFrame frame = new JFrame("Select a current project or register a new one");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DisplayUserProjects displayProjects = new DisplayUserProjects(username, oos, ois );
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
		
		if(cmd.equals(NEW_PROJ)) {
			String project = projName.getText();
			//send new project to sql server
			if(project.equals("")) {
				JOptionPane.showMessageDialog(this, "For the love of god name your project");
			}else {
			User client = new User(username, project, "register_project");
			boolean successful_register = NetworkFunctions.sendGuest(client, oos, ois);
			if(successful_register) {
				((Window) this.getRootPane().getParent()).dispose();
				DisplayProjectFiles gui = new DisplayProjectFiles(client, client.getUsername(), false, oos, ois);
				gui.startProject();
			}else {
				JOptionPane.showMessageDialog(this, "You already have a project with the same name!");
			}
		}
			
		}else if(cmd.equals(SELECT_PROJ)) {
			JComboBox<?> p = (JComboBox<?>)e.getSource();
			int idx = p.getSelectedIndex();
			String project_owner = projects_and_owners.getProjectOwners()[idx];
			String project = projects_and_owners.getProjects()[idx];
			
			System.err.println(project_owner + " " + project);
			
			
			User client = new User(username, project);
			((Window) this.getRootPane().getParent()).dispose();
			DisplayProjectFiles gui = new DisplayProjectFiles(client, project_owner, false, oos, ois);
			gui.startProject();
		}

	}
}
