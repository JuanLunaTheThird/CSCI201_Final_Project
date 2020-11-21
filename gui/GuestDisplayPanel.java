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


public class GuestDisplayPanel extends JPanel 
                             implements ActionListener {
    
	private static final long serialVersionUID = 1L;
    private static String IMPORT_COMMAND = "Import Project";
    File root;
    private DirectoryPanel treePanel;
    User user;
    String projectName;
    public GuestDisplayPanel(User user, boolean start) {
    	super(new BorderLayout());
    	
    	this.user = user;
    	
    	if(start == true){
	        //Create the components.
	        root = getDir();
	        treePanel = new DirectoryPanel(root);
	        populateTree(treePanel, root);
	
	        
	        JButton importButton = new JButton("Import Project");
	        importButton.setActionCommand(IMPORT_COMMAND);
	        importButton.addActionListener(this);
	         
	        
	        //Lay everything out.
	        treePanel.setPreferredSize(new Dimension(300, 150));
	        add(treePanel, BorderLayout.CENTER);
	
	        JPanel panel = new JPanel(new GridLayout(0,3));
	        panel.add(importButton);;
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
    
   
    
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (IMPORT_COMMAND.equals(command)) {
            
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