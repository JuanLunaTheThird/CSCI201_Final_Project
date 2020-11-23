package treenode;

import java.io.File;
import java.io.IOException;

public class TreeFileNode {
	
	private File file;
	private String comment;
	
	public TreeFileNode(File file, String comment) {
		this.file = file;
		this.comment = comment;
	}
	
	
	public String getCanonicalPath() throws IOException {
		return file.getCanonicalPath();
	}
	
	public String getComment() {
		return comment;
	}
	
	public String toString() {
		return file.getName();
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	public File getFile() {
		return this.file;
	}
	
}
