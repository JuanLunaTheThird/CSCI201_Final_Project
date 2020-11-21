package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import fileIO.FileZip;
import gui.FileExportSwingWorker;
import serialized.User;


public class FileDisplayPanel extends JPanel 
                             implements ActionListener {
    
	private static final long serialVersionUID = 1L;
	private static String ADD_COMMAND = "Add File";
    private static String REMOVE_COMMAND = "Remove File";
    private static String IMPORT_COMMAND = "Import Project";
    private static String EXPORT_COMMAND = "Export Project";
    private static String ADD_USER_COMMAND = "add_user";
    File root;
    private DirectoryPanel treePanel;
    User user;
    String projectName;
    public FileDisplayPanel(User user, boolean start) {
    	super(new BorderLayout());
    	
    	this.user = user;
    	
    	if(start == true){
	        //Create the components.
	        root = getDir();
	        treePanel = new DirectoryPanel(root);
	        populateTree(treePanel, root);
	
	        JButton addButton = new JButton("Add File");
	        addButton.setActionCommand(ADD_COMMAND);
	        addButton.addActionListener(this);
	        
	        JButton removeButton = new JButton("Remove File");
	        removeButton.setActionCommand(REMOVE_COMMAND);
	        removeButton.addActionListener(this);
	        
	        JButton importButton = new JButton("Import Project");
	        importButton.setActionCommand(IMPORT_COMMAND);
	        importButton.addActionListener(this);
	          
	        JButton exportButton = new JButton("Export Project");
	        exportButton.setActionCommand(EXPORT_COMMAND);
	        exportButton.addActionListener(this);
	        
	        JButton addUser = new JButton("Add a user to the project");
	        addUser.setActionCommand(ADD_USER_COMMAND);
	        addUser.addActionListener(this);
	        
	        
	        
	        //Lay everything out.
	        treePanel.setPreferredSize(new Dimension(300, 150));
	        add(treePanel, BorderLayout.CENTER);
	
	        JPanel panel = new JPanel(new GridLayout(0,3));
	        panel.add(addButton);
	        panel.add(removeButton); 
	        panel.add(importButton);
	        panel.add(exportButton);
	        panel.add(addUser);
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
    }
    
    public void populateTreeHelper(DirectoryPanel treePanel, File[] files, DefaultMutableTreeNode parent, DefaultMutableTreeNode PrevDirParent) {
    	
    	if(files == null) {
    		return;
    	}
    	
    	PrevDirParent = parent;
    	
    	for (File file : files) {
    		if(file.isDirectory()) {
    			DefaultMutableTreeNode NewNode = treePanel.addObject(PrevDirParent, file.getName());
    			populateTreeHelper(treePanel, file.listFiles(), NewNode, parent);
    		}else {
    			treePanel.addObject(PrevDirParent, file.getName());
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
    		treePanel.addNewFile(newFile);
    	}
    }
    
    
    
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (ADD_COMMAND.equals(command)) {
           addFileToDir();
        } else if (REMOVE_COMMAND.equals(command)) {
            //Remove button clicked
            treePanel.removeCurrentNode();
        } else if (IMPORT_COMMAND.equals(command)) {
            
        }else if (EXPORT_COMMAND.equals(command)) {
        	String srcdir = root.getPath();
        	String targetdir = srcdir + File.separator + user.getProject() + ".zip";
        	new FileExportSwingWorker(srcdir, targetdir).execute();
        }else if (ADD_USER_COMMAND.equals(command)) {	//add a new user to the project
        	System.err.println("entering if");
        	AddUserPopup addUser = new AddUserPopup(projectName);
        	addUser.addUser();
        }
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
        FileDisplayPanel newContentPane = new FileDisplayPanel(this.user, true);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        System.err.println("username");
        System.err.println(user.getUsername());
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