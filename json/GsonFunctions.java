package json;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;



public class GsonFunctions {
	private static Gson gson = new Gson();
	private final static String config_file = "config.txt";
	public static void updateJson(String project_json_string) {
    	FileWriter fw = null;
    	PrintWriter pw = null;
    	
    	try {
    		fw = new FileWriter(config_file);
            pw = new PrintWriter(fw);
            pw.print(project_json_string);
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		try {
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
    		pw.close();
    	}	
	}
	
	public static Projects parseServerJson(String pathname) {
		Projects projects = null;
		try {
			projects = gson.fromJson(new FileReader(pathname), new TypeToken<Projects>() {}.getType());
		} catch (JsonIOException e) {
			
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.err.println("config doesn't exist, making one now");
		}
		return projects;	
	}
	
	public static void createServerConfig(ArrayList<ProjectNotes> p, String pathname) {
		Projects projects = new Projects(p);
		Gson g = new GsonBuilder().setPrettyPrinting().create();
		String toJson = g.toJson(projects);
		FileWriter fw = null;
    	PrintWriter pw = null;
    	
    	try {
    		fw = new FileWriter(pathname);
            pw = new PrintWriter(fw);
            pw.print(toJson);
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		try {
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
    		pw.close();
    	}
	}
	
	public static void initConfigFile(ArrayList<ProjectNotes> p) {
		Projects projects = new Projects(p);
		Gson g = new GsonBuilder().setPrettyPrinting().create();
		String toJson = g.toJson(projects);
		updateJson(toJson);
	}
		
	public static Projects parseJson(){
		Projects projects = null;
		try {
			projects = gson.fromJson(new FileReader(config_file), new TypeToken<Projects>() {}.getType());
		} catch (JsonIOException e) {
			
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.err.println("config doesn't exist, making one now");
		}
		return projects;	
	}
	

}
