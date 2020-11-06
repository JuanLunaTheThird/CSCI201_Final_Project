
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;



public class UserGui extends JPanel 
                             implements ActionListener {
    
	private static final long serialVersionUID = 1L;
	private static String ADD_COMMAND = "Add File";
    private static String REMOVE_COMMAND = "Remove File";
    private static String IMPORT_COMMAND = "Import Project";
    private static String EXPORT_COMMAND = "Export Project";
    File root;
    private DirectoryPanel treePanel;

    public UserGui(String start) {
    	super(new BorderLayout());
    	
    	if(start.equals("start")){
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
	        
	
	        //Lay everything out.
	        treePanel.setPreferredSize(new Dimension(300, 150));
	        add(treePanel, BorderLayout.CENTER);
	
	        JPanel panel = new JPanel(new GridLayout(0,3));
	        panel.add(addButton);
	        panel.add(removeButton); 
	        panel.add(importButton);
	        panel.add(removeButton);
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
            //Clear button clicked.
        }else if (EXPORT_COMMAND.equals(command)) {
            //Clear button clicked.
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
    	
        //Create and set up the window.
        JFrame frame = new JFrame("Project Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        UserGui newContentPane = new UserGui("start");
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