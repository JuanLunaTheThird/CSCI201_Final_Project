package ziptest;

import java.io.File;
import fileIO.FileZip;



public class ZipTest {
	
    public static void main(String[] args) {
    	File file = new File("C:\\Users\\juanl\\Desktop\\fenox");
    	
    	File test = new File("C:\\Users\\juanl\\Desktop\\fenox\\src.rar");
    	
    	
    
    	
    	
    	String srcdir = "C:\\Users\\juanl\\Desktop\\fenox";
    	String targetdir = "C:\\Users\\juanl\\Desktop\\Prohub\\fenox.zip";
    
    	FileZip fz = new FileZip(srcdir, targetdir);
    	fz.ZipDir();
    	String newsrc = "C:\\Users\\juanl\\Desktop\\Prohub";
    	FileZip unzip = new FileZip(targetdir, newsrc);
    	unzip.unzipDir();
    }
    	
	
}
