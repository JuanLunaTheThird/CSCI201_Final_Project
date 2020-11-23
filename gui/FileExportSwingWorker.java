package gui;
import javax.swing.SwingWorker;
import fileIO.FileZip;
import networking.FileTransfer;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class FileExportSwingWorker extends SwingWorker<Object, Object> {
	private String targetdir;
	private FileZip zip;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String project;
	private String project_owner;
	private String jsonString;
	public FileExportSwingWorker(String srcdir, String targetdir, String project, String project_owner, String config_file, ObjectInputStream ois, ObjectOutputStream oos) {
		zip = new FileZip(srcdir, targetdir);
		this.targetdir = targetdir;
		this.oos = oos;
		this.ois = ois;
		this.project = project;
		this.project_owner = project_owner;
		this.jsonString = config_file;
	}
	
	
	@Override
	protected String doInBackground() throws Exception {
		zip.zipDir();
		FileTransfer toServer = new FileTransfer(oos, ois);
		toServer.sendFileToServer(targetdir, project, project_owner, jsonString);
		File toDelete = new File(targetdir);
	
		if(toDelete.delete()) {
			System.err.println("File deleted");
		}else {
			System.err.println("Error deleting file");
		}
		
		
		return null;
	}

}
