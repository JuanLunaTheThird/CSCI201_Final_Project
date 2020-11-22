package fileIO;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.util.ArrayList;


public class FileZip {

	private ArrayList<String> files;
	private String srcdir;
	private String targetdir;
	
	public FileZip(String srcdir, String targetdir) {
		files = new ArrayList<String>();
		this.srcdir = srcdir;
		this.targetdir = targetdir;
	}
	
	
	
	public void unzipDir() {
		ZipInputStream zip = null;
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(srcdir);
			zip = new ZipInputStream(fis);
			
			ZipEntry ze = null;
			
			while( (ze = zip.getNextEntry()) != null) {
				File file = new File(targetdir, ze.getName());
				
				if(ze.isDirectory()) {
					file.mkdir();
					continue;
				}
				
				byte[] buf = new byte[4096];
				file.getParentFile().mkdirs();
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				int read;
				
				while((read = zip.read(buf)) != -1) {
					bos.write(buf, 0, read);
				}
				bos.close();
			}
			
			zip.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("file unzipped");
	
	}
	
	private static boolean isEmptyDir(Path dir) throws IOException {
		 try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir)) {
		        return !dirStream.iterator().hasNext();
		    }
	}
	
	
	
	public void zipDir() {
		
		Path path = Paths.get(srcdir);
		
		try {
			if(isEmptyDir(path)) {
				return;
			}
		} catch (IOException e1) {
			System.err.println("empty or doesn't exist");
		}
		
		fillFiles(new File(srcdir));
		byte[] buf = new byte[4096];
		FileOutputStream fileoutput = null;
		ZipOutputStream zip = null;
		System.err.println(targetdir);
		try {
			fileoutput = new FileOutputStream(targetdir);
			zip = new ZipOutputStream(fileoutput);
			
			FileInputStream in = null;
		
			
			
            for (String file : files) {
            	//original in case of errors 
            	//ZipEntry ze = new ZipEntry(new File(srcdir).getName() + File.separator + file);

                ZipEntry ze = new ZipEntry(file);
                zip.putNextEntry(ze);
                try {
                    in = new FileInputStream(srcdir + File.separator + file);
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        zip.write(buf, 0, len);
                    }
                } finally {
                	if(in != null) {
                		in.close();
                	}
                }
            }
			
			zip.closeEntry();
			System.out.println("compressed file");
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				zip.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void fillFiles(File file) {
		
		if(file.isFile()) {
			String substr = file.toString().substring(srcdir.length() + 1, file.toString().length());
			files.add(substr);
		}
		
		if(file.isDirectory()) {
			String[] subfiles = file.list();
			
			for(String s : subfiles) {
				fillFiles(new File(file, s));
			}
		}
	}
	
	
}
