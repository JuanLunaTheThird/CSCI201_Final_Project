package json_testing;

import java.util.ArrayList;

import json.*;


public class proj_json_tests {
	
	public static void main(String[] args) {
		
		Projects p = GsonFunctions.parseJson();
		ProjectNotes project = p.projectExists("Testy test", "funshinebear");
		
		String update_old = project.toJson();
		Projects serverConfig = GsonFunctions.parseServerJson("C:\\Users\\juanl\\Desktop\\CSCI201\\ProHubGUI\\old_config.txt");
		
		System.err.println("before");
		System.out.println(serverConfig.toString());
		
		serverConfig.updateProjectWithJsonString(update_old);
		GsonFunctions.updateJson(serverConfig.toJson());
		Projects results = GsonFunctions.parseServerJson("C:\\Users\\juanl\\Desktop\\CSCI201\\ProHubGUI\\old_config.txt");
		System.err.println("After");
		System.out.println(results.toString());
		/*
		ArrayList<ProjectNotes> s = new ArrayList<ProjectNotes>();
		
		ArrayList<String> f = new ArrayList<String>();
		ArrayList<String> d = new ArrayList<String>();
		f.add("coolpepe.png");
		d.add("PRETTY POG");
		
		ProjectNotes ex = new ProjectNotes("Testy test", "", "funshinebear",f, d);
		System.err.println("FUCKING WORK U PIECE OF SHIT EXAMPKLE");
		s.add(ex);
		
		
		Projects tryingNull = new Projects(s);
		//tryingNull.addProject("OMEGALUL", "funshinebear");
		String nullVallTest = tryingNull.toJson();
		System.err.println("null value test");
		System.out.println(nullVallTest);
		
		//System.err.println("Pretty printing test");
		//String test = p.toJson();
		//System.out.println(test);
		*/
		
		
		//System.err.println("toString testing");
		String pretty = p.toString();
		//System.out.println(pretty);
	}
	
	
	
}
