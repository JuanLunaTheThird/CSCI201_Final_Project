package ziptest;

import fileIO.FileZip;



public class ZipTest {
	
    public static void main(String[] args) {
    
    	
    
    	
    	
    	String srcdir = "C:\\Users\\juanl\\Desktop\\to zip";
    	String targetdir = "C:\\Users\\juanl\\Desktop\\write to\\pog.zip";
    
    	FileZip fz = new FileZip(srcdir, targetdir);
    	fz.zipDir();
    	String newsrc = "C:\\Users\\juanl\\Desktop\\write to\\";
    	FileZip unzip = new FileZip(targetdir, newsrc);
    	unzip.unzipDir();
    }
    	
	
}
