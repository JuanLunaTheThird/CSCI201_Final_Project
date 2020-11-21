package gui;
import javax.swing.SwingWorker;
import fileIO.FileZip;
import networking.FileTransfer;
import java.io.File;


public class FileExportSwingWorker extends SwingWorker<Object, Object> {
	private String targetdir;
	private FileZip zip;
	
	
	public FileExportSwingWorker(String srcdir, String targetdir) {
		zip = new FileZip(srcdir, targetdir);
		this.targetdir = targetdir;
	}
	
	
	@Override
	protected String doInBackground() throws Exception {
		zip.ZipDir();
		FileTransfer toServer = new FileTransfer(targetdir);
		toServer.sendFile(targetdir);
		File toDelete = new File(targetdir);
	
		if(toDelete.delete()) {
			System.err.println("File deleted");
		}else {
			System.err.println("Error deleting file");
		}
		
		
		return null;
	}

}
