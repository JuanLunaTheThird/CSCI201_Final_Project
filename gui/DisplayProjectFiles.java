package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import com.google.gson.Gson;

import json.GsonFunctions;
import json.ProjectNotes;
import json.Projects;
import networking.FileTransfer;
import networking.NetworkFunctions;
import serialized.User;
import treenode.TreeFileNode;

public class DisplayProjectFiles extends JPanel 
                             implements ActionListener {
    
	private static final long serialVersionUID = 1L;
	private static String ADD_COMMAND = "Add File";
    private static String REMOVE_COMMAND = "Remove File";
    private static String IMPORT_COMMAND = "Import Project";
    private static String EXPORT_COMMAND = "Export Project";
    private static String ADD_USER_COMMAND = "add_user";
    private static String SEE_COMMENT_COMMAND = "comment";
    File root;
    private DirectoryPanel treePanel;
    User user;
    String projectName;
    private ObjectInputStream ois;
	private ObjectOutputStream oos;
    private String project_owner;
    private ProjectNotes project;
    private boolean making_new_projectnotes;
    
    public DisplayProjectFiles(User user, String project_owner, boolean start,  ObjectOutputStream oos, ObjectInputStream ois) {
    	super(new BorderLayout());
    	this.projectName = user.getProject();
    	this.project_owner = project_owner;
    	this.user = user;
    	this.oos = oos;
		this.ois = ois;
    	if(start == true){
	        //Create the components.
	        root = getDir();
	        File config = new File("config.txt");
	        boolean has_config = config.exists();
	        project = null;
	        if(has_config) {
	        	Projects p = GsonFunctions.parseJson();
	        	project = p.projectExists(projectName, project_owner);
	        	if(project == null) {
	        		making_new_projectnotes = true;
	        		project = new ProjectNotes(projectName, "", project_owner, new ArrayList<String>(), new ArrayList<String>());
	        	}else {
	        		making_new_projectnotes = false;
	        	}
	        }
	        
	        
	        Image addFileimg = null;
			try {
				addFileimg = ImageIO.read(getClass().getResource("/resources/add.png"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	        
	        
	        
	        treePanel = new DirectoryPanel(root, projectName, project_owner, project);
	        populateTree(treePanel, root);
	
	        JButton addButton = new JButton("Add File");
	        addButton.setActionCommand(ADD_COMMAND);
	        addButton.addActionListener(this);
	        addButton.setIcon(new ImageIcon(addFileimg));
	        
	        Image r = null;
			try {
				r = ImageIO.read(getClass().getResource("/resources/remove.png"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			
			 Image importImg = null;
			 Image exportImg = null;
			 Image addImg = null;
			 Image viewImg = null;
			 Image makeCommentImg = null;
			 Image addU = null;
				try {
					importImg = ImageIO.read(getClass().getResource("/resources/import.png"));
					
					exportImg = ImageIO.read(getClass().getResource("/resources/export.png"));
					addImg = ImageIO.read(getClass().getResource("/resources/add.png"));
					viewImg = ImageIO.read(getClass().getResource("/resources/view.png"));
					makeCommentImg = ImageIO.read(getClass().getResource("/resources/comment.png"));
					addU = ImageIO.read(getClass().getResource("/resources/register.png"));
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
	        
	        
	        
	        
	        JButton removeButton = new JButton("Remove File");
	        removeButton.setActionCommand(REMOVE_COMMAND);
	        removeButton.addActionListener(this);
	        removeButton.setIcon(new ImageIcon(r));
	        
	        
	        JButton importButton = new JButton("Import Project");
	        importButton.setActionCommand(IMPORT_COMMAND);
	        importButton.addActionListener(this);
	        importButton.setIcon(new ImageIcon(importImg));
	        
	          
	        JButton exportButton = new JButton("Export Project");
	        exportButton.setActionCommand(EXPORT_COMMAND);
	        exportButton.addActionListener(this);
	        exportButton.setIcon(new ImageIcon(exportImg));
	        
	        
	        JButton addUser = new JButton("Add a user to the project");
	        addUser.setActionCommand(ADD_USER_COMMAND);
	        addUser.addActionListener(this);
	        addUser.setIcon(new ImageIcon(addU));
	        
	        JButton seeComment = new JButton("View the selected files comment");
	        seeComment.setActionCommand(SEE_COMMENT_COMMAND);
	        seeComment.addActionListener(this);
	        seeComment.setIcon(new ImageIcon(viewImg));
	        
	        JButton why = new JButton("Add a comment");
	        why.setActionCommand("why");
	        why.addActionListener(this);
	        why.setIcon(new ImageIcon(makeCommentImg));
	        //Lay everything out.
	
	        
	        treePanel.setPreferredSize(new Dimension(300, 150));
	        add(treePanel, BorderLayout.CENTER);
	
	        JPanel panel = new JPanel(new GridLayout(0,3));
	        panel.add(addButton);
	        panel.add(removeButton); 
	        panel.add(importButton);
	        panel.add(exportButton);
	        panel.add(addUser);
	        panel.add(seeComment);
	        panel.add(why);
	        add(panel, BorderLayout.SOUTH);
    	}
    }
    
    public File getDir() {
    	
    	JFileChooser buildTree = new JFileChooser();
    	buildTree.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	int returnVal = buildTree.showOpenDialog(this);
    	
    	if(returnVal == JFileChooser.APPROVE_OPTION) {
    		return buildTree.getSelectedFile();
    	}
   
		return null;
    }

    public void populateTree(DirectoryPanel treePanel, File selectedDir) {
 
    	populateTreeHelper(treePanel, selectedDir.listFiles(), null, null);
    	Projects p = GsonFunctions.parseJson();
    	p.changeExistingProject(project);
    	String project_json_string = p.toJson();
    	GsonFunctions.updateJson(project_json_string);
    }
    
    
    //im like 90% sure this runtime is garbage now but YOLO
    
    public void populateTreeHelper(DirectoryPanel treePanel, File[] files, DefaultMutableTreeNode parent, DefaultMutableTreeNode PrevDirParent) {
    	
    	if(files == null) {
    		return;
    	}
    	
    	PrevDirParent = parent;
    	
    	for (File file : files) {
    		if(file.isDirectory()) {
    			if(project != null && !making_new_projectnotes) { //if we aren't making a new directory, we don't have to check for existing files
    				TreeFileNode node = project.getFileAndComment(file);
    				if(node != null) {
    					DefaultMutableTreeNode NewNode = treePanel.addObject(PrevDirParent, node);
            			populateTreeHelper(treePanel, file.listFiles(), NewNode, parent);	
    				}else { //if the node doesn't exist, just add it to the config
    					project.addFileAndComment(file.getName(), "");
    					node = new TreeFileNode(file, "");
        				DefaultMutableTreeNode NewNode = treePanel.addObject(PrevDirParent, node);
            			populateTreeHelper(treePanel, file.listFiles(), NewNode, parent);
    				}
    			}
    			else {								
    				//else we can just add to the project notes without checking if the file exists
    				TreeFileNode node = new TreeFileNode(file, "");
    				project.AddFileAndComment(node);
    				DefaultMutableTreeNode NewNode = treePanel.addObject(PrevDirParent, node);
        			populateTreeHelper(treePanel, file.listFiles(), NewNode, parent);
    			}
    		}else {
    			if(project != null && !making_new_projectnotes) {
    				TreeFileNode node = project.getFileAndComment(file);
    				if(node != null) {
    					treePanel.addObject(PrevDirParent, node);
    				}else {
    					project.addFileAndComment(file.getName(), "");
    					node = new TreeFileNode(file, "");
    					treePanel.addObject(PrevDirParent, node);
    				}
    			}else {
    				TreeFileNode node = new TreeFileNode(file, "");
    				project.AddFileAndComment(node);
    				treePanel.addObject(PrevDirParent, node);
    			}
    		}
    	}
    }
    
    private void addFileToDir() {
    	JFileChooser addToDir = new JFileChooser();
    	File f = addToDir.getCurrentDirectory();
    	addToDir.setCurrentDirectory(f);
    	int returnVal = addToDir.showOpenDialog(this);
    	
    	if(returnVal == JFileChooser.APPROVE_OPTION) {
    		File newFile =  addToDir.getSelectedFile();
    		TreeFileNode newNode = new TreeFileNode(newFile, "");
    		if(project.containsFile(newFile.getName()))
    		{
    			project.addFileAndComment(newFile.getName(), "");
    		}else {
    			project.AddFileAndComment(newNode);	
    		}
    		treePanel.addNewFile(newNode);
    	}
    }
    
    
    
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println(command);
        
        
        
        if (ADD_COMMAND.equals(command)) {
        	//add a file to the directory and update it on the gui
           addFileToDir();
        } else if (REMOVE_COMMAND.equals(command)) {
            File f;
			try {
				 f = treePanel.getCurrentNode();
				 if(f.delete()) {
					 System.err.print("deleted");
				 }
				 treePanel.removeCurrentNode();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } else if (IMPORT_COMMAND.equals(command)) {
        	//request all current files from server
        	User project_req = new User(project_owner, user.getProject(),"file_request");
            String json = NetworkFunctions.importProject(project_req,root.getAbsolutePath() , oos, ois);
            ProjectNotes j = new Gson().fromJson(json, ProjectNotes.class);
            
            project.updateFilesAndComments(j.getFileNames(), j.getNotes());
        	treePanel.clear();
        	populateTree(treePanel, root);
        	Projects proj = GsonFunctions.parseJson();
        	proj.updateProjectWithJsonString(json);
        	proj.changeExistingProject(project);
        	GsonFunctions.updateJson(proj.toJson());
    ;
        }else if (EXPORT_COMMAND.equals(command)) {
        	String srcdir = root.getPath();
        	String targetdir = srcdir + File.separator + user.getProject() + ".zip";
        	
        	Projects p = GsonFunctions.parseJson();
        	p.changeExistingProject(project);
        	String project_json_string = p.toJson();
        	GsonFunctions.updateJson(project_json_string);
        	
        	ProjectNotes recent_project = p.projectExists(project.getProjectName(), project.getOwner());
        	String this_project_json = recent_project.toJson();
        	
        	new FileExportSwingWorker(srcdir, targetdir, projectName, project_owner, this_project_json,  ois, oos).execute();
        }else if (ADD_USER_COMMAND.equals(command)) {	//add a new user to the project
        	AddUserPopup addUser = new AddUserPopup(projectName,project_owner, oos, ois);
        	addUser.addUser();
        }else if(SEE_COMMENT_COMMAND.equals(command)) {
        	String comment = treePanel.getCurrentNodeComment();
        	if(comment.equals("")) {
        		JOptionPane.showMessageDialog(this, "There is no comment for this file");
        	}else {
        		JOptionPane.showMessageDialog(this, comment);
        	}
        }
        else if(command.equals("why")) {
        	promptComment();
        }
        
        else if(command.equals("not")) {
        	File file = null;
        	String srcdir = null;
			try {
				file = treePanel.getCurrentNode();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        	
        	
        	
        	if(file == null) {
        		JOptionPane.showMessageDialog(this, "Click the file you want to upload first!");
        		return;
        	}
       
			if(file.isDirectory()) {
				String targetdir = srcdir + File.separator + user.getProject() + ".zip";
	        	Projects p = GsonFunctions.parseJson();
	        	p.changeExistingProject(project);
	        	String project_json_string = p.toJson();
	        	GsonFunctions.updateJson(project_json_string);
	          	ProjectNotes recent_project = p.projectExists(project.getProjectName(), project.getOwner());
	        	String this_project_json = recent_project.toJson();
	        	new FileExportSwingWorker(srcdir, targetdir, projectName, project_owner, this_project_json,  ois, oos).execute();
			}else {
				Projects p = GsonFunctions.parseJson();
	        	p.changeExistingProject(project);
	        	String project_json_string = p.toJson();
	        	GsonFunctions.updateJson(project_json_string);
	          	ProjectNotes recent_project = p.projectExists(project.getProjectName(), project.getOwner());
	        	String this_project_json = recent_project.toJson();
				FileTransfer f = new FileTransfer(oos, ois);
				f.sendFileToServer(file.getAbsolutePath(), project.getProjectName(), project.getOwner(), this_project_json);
			}
        }
        
        
    }
    
    
    //makes a small dialog to comment a box
    private void promptComment() {
       JFrame commentFrame = new JFrame("Add a comment to the file");
       JPanel commentbox;
 	   JLabel comment = new JLabel();
 	   JTextField text;
 	   JButton submit;
    	
 
		comment.setText("Set Comment: ");
		text = new JTextField();
		submit = new JButton("Comment");
		
		
		commentbox = new JPanel(new GridLayout(2,1));
		commentbox.add(comment);
		commentbox.add(submit);
		commentbox.add(text);
		submit.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String com = text.getText();
				TreeFileNode curr_node = treePanel.addCommentToNode(com);
				
				if(curr_node != null) {
					project.addFileAndComment(curr_node.toString(), curr_node.getComment());
					Projects p = GsonFunctions.parseJson();
					p.updateProjectWithJsonString(project.toJson());
					GsonFunctions.updateJson(p.toJson());
				}
				
				((Window) commentbox.getRootPane().getParent()).dispose();
			}
		}
	  );
		commentbox.add(submit);
    	commentbox.setOpaque(true);
    	commentFrame.setContentPane(commentbox);
    	commentFrame.pack();
    	commentFrame.setVisible(true);
    }
    
    

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
    	
        //Create and set up the window.
        JFrame frame = new JFrame("Project Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        DisplayProjectFiles newContentPane = new DisplayProjectFiles(this.user, project_owner, true, oos, ois);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void startProject() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}