package gui;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import fileIO.LocalFileIO;
import json.ProjectNotes;


import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import treenode.TreeFileNode;
public class DirectoryPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    String pathName;
    
    public DirectoryPanel(File root, String project, String owner, ProjectNotes projectNote) {
        super(new GridLayout(1,0));
        
        try {
			pathName = root.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
       if(projectNote != null) {
    	   TreeFileNode newNode = projectNote.getFileAndComment(root);
    	   if(newNode != null) {
    		   rootNode = new DefaultMutableTreeNode(newNode);
    	   }else {
    		   rootNode = new DefaultMutableTreeNode(new TreeFileNode(root, ""));
    	   }  
       }else {
    	   rootNode = new DefaultMutableTreeNode(new TreeFileNode(root, ""));
       }
        
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new MyTreeModelListener());
        tree = new JTree(treeModel);
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }

    /** Remove all nodes except the root node. */
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    /** Remove the currently selected node. */
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        } 

        // Either there was no selection, or the root was selected.
        toolkit.beep();
    }

    /** Add child to the currently selected node. */
    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode)
                         (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child) {
        return addObject(parent, child, false);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child, 
                                            boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = 
                new DefaultMutableTreeNode(child);

        if (parent == null) {
            parent = rootNode;
        }
	
	   //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        treeModel.insertNodeInto(childNode, parent, 
                                 parent.getChildCount());
        
        
        
        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }
    
    public String getCurrentNodeComment() {
    	TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         currentSelection.getLastPathComponent();
           TreeFileNode obj = (TreeFileNode) currentNode.getUserObject();
           return obj.getComment();
        } 
        
        // Either there was no selection, or the root was selected.
        toolkit.beep();
        return "";
    }
    
    public File getCurrentNode() throws IOException {
    	TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         currentSelection.getLastPathComponent();
           TreeFileNode obj = (TreeFileNode) currentNode.getUserObject();
           return obj.getFile();
        } 
        
        // Either there was no selection, or the root was selected.
        toolkit.beep();
        return null;
    }
    
    
    
    
    public TreeFileNode addCommentToNode(String comment) {
    	TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         currentSelection.getLastPathComponent();
           ((TreeFileNode) currentNode.getUserObject()).setComment(comment);
           return (TreeFileNode) currentNode.getUserObject();
        } 
        
        // Either there was no selection, or the root was selected.
        toolkit.beep();
        return null;
    }
    
    
    public DefaultMutableTreeNode addNewFile(Object file) {
    	DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode)
                         (parentPath.getLastPathComponent());
        }
        
        Object[] objPath =  parentNode.getUserObjectPath();
        
        String path = pathName;
        
        //I did not know you can't add \
    
        for(int i = 0; i < objPath.length-1; ++i) {
        	path += "\\" + (String)objPath[i];
        }
        
        String fromPath = null;
		try {
			fromPath = ((TreeFileNode) file).getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        System.err.println(path);
        
        byte[] fileData = LocalFileIO.sendFile(fromPath);
        LocalFileIO.receiveFile(fileData, path, ((TreeFileNode) file).toString() );
        return addObject(parentNode, file, true);
    }
    

    class MyTreeModelListener implements TreeModelListener {
        public void treeNodesChanged(TreeModelEvent e) {
            DefaultMutableTreeNode node;
            node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());

            /*
             * If the event lists children, then the changed
             * node is the child of the node we've already
             * gotten.  Otherwise, the changed node and the
             * specified node are the same.
             */

                int index = e.getChildIndices()[0];
                node = (DefaultMutableTreeNode)(node.getChildAt(index));

            System.out.println("The user has finished editing the node.");
            System.out.println("New value: " + node.getUserObject());
        }
        public void treeNodesInserted(TreeModelEvent e) {
        }
        public void treeNodesRemoved(TreeModelEvent e) {
        }
        public void treeStructureChanged(TreeModelEvent e) {
        }
    }
}