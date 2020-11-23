package json;

import java.io.File;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import treenode.TreeFileNode;

public class ProjectNotes {
  
    
	@SerializedName("project_name")
	private String project;
	
	@SerializedName("files")
	private ArrayList<String> files;
	
	@SerializedName("deadline")
	private String deadline;
	
	
	@SerializedName("notes")
	private ArrayList<String> file_notes;
	
	@SerializedName("project_owner")
	private String owner;
	
	public ProjectNotes(String project,String deadline, String owner, ArrayList<String> files, ArrayList<String> file_notes) {
		this.project = project;
		this.files= files;
		this.file_notes = file_notes;
		this.deadline = deadline;
		this.owner = owner;
	}
	
	public ProjectNotes(String json_string) {
		deepCopy(json_string);
	}
	
	private void deepCopy(String json_string) {
		ProjectNotes p = new Gson().fromJson(json_string, ProjectNotes.class);
		this.project = p.getProjectName();
		ArrayList<String> f = p.getFileNames();
		ArrayList<String> comments = p.getNotes();
		
		
		this.files = new ArrayList<String>();
		this.file_notes = new ArrayList<String>();
		for(int i = 0; i < files.size(); ++i) {
			this.files.add(f.get(i));
			this.file_notes.add(comments.get(i));
		}
		
		if(p.getDeadline() != null) {
			this.deadline = new String(p.getDeadline());
		}
		
		this.owner = new String(p.getOwner());
	}
	
	
	
	public void addFileAndComment(String file, String comment) {
		if(files.contains(file)) {
			System.err.println("config file already has a file with the same name");
			int idx = files.indexOf(file);
			if(!comment.equals("")) {
				file_notes.set(idx, comment);
			}
		}else {
			files.add(file);
			file_notes.add(comment);
		}
	}
	
	public void AddFileAndComment(TreeFileNode node) {
		files.add(node.toString());
		file_notes.add(node.getComment());
	}
	
	public void updateFilesAndComments(ArrayList<String> f, ArrayList<String> comments) {
		for(int i = 0; i < f.size(); ++i) {
			if(files.contains(f.get(i)) && !comments.get(i).equals("") ) {
				file_notes.set(i, comments.get(i));
			}else if(!files.contains(f.get(i))) {
				files.add(f.get(i));
				file_notes.add(comments.get(i));
			}
		}
	}
	
	public boolean containsFile(String fname) {
		return files.contains(fname);
	}
	
	public String toString() {
		String t = "Project name is: " + this.project + " and it was created by " + this.owner + '\n';
		t += "It is due " + this.deadline + '\n';
		
		for(int i=0; i < files.size(); ++i) {
			t+= "The note for file " + files.get(i) + " says" + '\n';
			t+= '\t' +  file_notes.get(i) + '\n';
		}
		
		return t;
	}
	
	public TreeFileNode getFileAndComment(File file) {
		for(int i =0; i < files.size(); ++i) {
			if(files.get(i).equals(file.getName())) {
				TreeFileNode newNode = new TreeFileNode(file, file_notes.get(i));
				return newNode;
			}
		}
		return null;
	}
	
	
	public String toJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String Json = gson.toJson(this);
    	return Json;
	}
	
	
	public String getOwner() {
		return this.owner;
	}
	
	public String getProjectName() {
		return this.project;
	}
	
	public String getDeadline() {
		return deadline;
	}
	
	
	public ArrayList<String> getFileNames() {
		return this.files;
	}
	
	public ArrayList<String> getNotes() {
		return this.file_notes;
	}
	
	

}
