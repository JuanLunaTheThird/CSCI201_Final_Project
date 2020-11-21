package fileIO;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class FileZipForNetworkIO {

	public void zip(List<File> files, String dirname) {
		try {
			ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(dirname));
			
			for(File file : files) {
				if(file.isDirectory()) {
					zipDirectory(file, file.getName(), zip);
					continue;
				}else {
					zipFile(file, zip);
				}
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void zipDirectory(File folder, String parentFolder, ZipOutputStream zip) {
		
		for(File file : folder.listFiles()) {
			if(file.isDirectory()) {
				zipDirectory(file, parentFolder + "//" + file.getName(), zip);
			}
			try {
				zip.putNextEntry(new ZipEntry(parentFolder + "//" + file.getName()));
				BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
				byte[] bytes = new byte[4096];
				int read = 0;
				long bytesRead = 0;
				while((read = input.read(bytes)) != -1) {
					zip.write(bytes, 0 ,read);
					bytesRead += read;
				}
				zip.closeEntry();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
		
	 public void zip(String[] files, String projectDir, String destZipFile) throws FileNotFoundException, IOException {
	        List<File> listFiles = new ArrayList<File>();
	        for (int i = 0; i < files.length; i++) {
	            listFiles.add(new File(projectDir + files[i]));
	            
	        }
	        zip(listFiles, destZipFile);
	    }
	
	
	public void zipFile(File file, ZipOutputStream zip) throws IOException {
		zip.putNextEntry(new ZipEntry(file.getName()));
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
		System.out.println(file.getCanonicalPath());
		long bytesRead = 0;
		byte[] bytes = new byte[4096];
		int read = 0;
		while((read = input.read(bytes)) != -1) {
			zip.write(bytes, 0 ,read);
			bytesRead += read;
		}
		zip.closeEntry();
	}
	
	
	
}
