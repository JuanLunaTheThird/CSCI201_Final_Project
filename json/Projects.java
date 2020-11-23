package json;

import java.io.File;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import treenode.TreeFileNode;

public class Projects {
	
	@SerializedName("projects")
	private ArrayList<ProjectNotes> projects;
	
	
	public Projects(ArrayList<ProjectNotes> projects) {
		this.projects = projects;
	}
	
	
	public ArrayList<ProjectNotes> getProjects() {
		return this.projects;
	}
	
	public String toString() {
		String t = new String();
		for(ProjectNotes n : projects) {
			t+= n.toString();
		}
		return t;
	}
	
	public void addProjectNotes(ProjectNotes p) {
		projects.add(p);
	}
	
	
	public void addProject(String project, String owner) {
		String deadline = "null";
		ArrayList<String> files = new ArrayList<String>();
		ArrayList<String> notes = new ArrayList<String>();
		ProjectNotes newProject = new ProjectNotes(project, deadline, owner,files, notes);
		projects.add(newProject);
	}
	
	public void changeExistingProject(ProjectNotes project) {
		ProjectNotes p = projectExists(project.getProjectName(), project.getOwner());
		if( p != null) {
			p.updateFilesAndComments(project.getFileNames(), project.getNotes());
		}else {
			projects.add(project);
		}
	}
	
	
	public TreeFileNode getFileAndComment(String project, String owner, File file) {
		
		for(ProjectNotes p : projects) {
			if(p.getOwner().equals(owner) && p.getProjectName().equals(project)) {
				ArrayList<String> files = p.getFileNames();
				for(int i =0; i < files.size(); ++i) {
					if(files.get(i).equals(file.getName())) {
						TreeFileNode newNode = new TreeFileNode(file, p.getNotes().get(i));
						return newNode;
					}
				}
			}
		}	
		return null;
	}
	
	
	public ProjectNotes projectExists(String project, String owner) {
		ProjectNotes exists = null;
		for(ProjectNotes p : projects) {
			if(p.getOwner().equals(owner) && p.getProjectName().equals(project)) {
				exists = p;
				break;
			}
		}
		return exists;
	}
	
	public void updateProjectWithJsonString(String json_string) {
		Gson gson = new Gson();
		ProjectNotes update = gson.fromJson(json_string, ProjectNotes.class);
		this.changeExistingProject(update);
	}
	
	public String toJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String Json = gson.toJson(this);
    	return Json;
	}
	
	
}
