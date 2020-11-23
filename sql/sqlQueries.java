package sql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import serialized.User;

public class sqlQueries {

	private static Connection getConnection()
	{
		Connection conn = null;
		String host = "jdbc:mysql://192.168.1.104:3306/prohub";
		String username = "jonah";
		String password = "Funshine!";
		try {
			conn = DriverManager.getConnection(host, username, password);
		}
		catch(SQLException e)
		{
			System.out.println("error establishing connection to sql database");
			System.out.println(e.getMessage());
		}
		return conn;
	}

  	/*
  	 * returns false if there is another project with the same name registered to the same user
  	 * returns true if project is successfully created and registered to user
  	 * inserts into project table and userstoproject
  	 * does not check if username is valid
  	 * username of founder and name of project
  	 */
	public static boolean createNewProject(String username, String project)
	{
		Connection conn = getConnection();
		String path = "C:\\\\Users\\\\juanl\\\\Desktop\\\\Prohub\\\\" + username + "\\\\" + project;
		System.out.println(path);
		String checkQuery = "SELECT * FROM userstoprojects WHERE username = \"" + username + "\" && project_directory = \"" + path + "\";";
		try(Statement stm = conn.createStatement())
		{
			ResultSet res = stm.executeQuery(checkQuery);
			if(res.next())
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		checkQuery = "INSERT INTO projects (project, stored_directory, username) "
				+ "VALUES (\"" + project + "\", \"" + path + "\", \"" + username + "\");";
		System.out.println(checkQuery);
		try(Statement stm = conn.createStatement())
		{
			stm.executeUpdate(checkQuery);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		checkQuery = "INSERT INTO userstoprojects (username, project_directory) VALUES (\"" + username + "\", \"" + path + "\");";
		try(Statement stm = conn.createStatement())
		{
			stm.executeUpdate(checkQuery);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * return empty string if there is no project assigned to username
	 * returns string of path otherwise
	 * project name and username of the founder
	 */
	public static String getProjectPath(String project, String username)
	{
		Connection conn = getConnection();
		String checkQuery = "SELECT projects.stored_directory FROM projects WHERE project = \"" + project + "\" AND username = \"" + username + "\" ;";
		try(Statement stm = conn.createStatement())
		{
			ResultSet res = stm.executeQuery(checkQuery);
			if(!res.next())
			{
				return "";
			}
			else
			{
				 return res.getString(1);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return "";
	}

	/*
	 * returns false if the user is already added to the project
	 * returns true if successful
	 * should check if the user exists
	 */
	public static boolean addUserToProject(String username, String project, String owner)
	{
		Connection conn = getConnection();
		String path = "C:\\\\Users\\\\juanl\\\\Desktop\\\\Prohub\\\\" + owner + "\\\\" + project;
		String checkQuery = "SELECT * FROM userstoprojects WHERE username = \"" + username + "\" AND project_directory = \"" + path + "\";";
		try(Statement stm = conn.createStatement())
		{
			ResultSet res = stm.executeQuery(checkQuery);
			if(res.next())
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		checkQuery = "INSERT INTO userstoprojects(username, project_directory) VALUES(\"" + username + "\", \"" + path + "\");";
		try(Statement stm = conn.createStatement())
		{
			stm.executeUpdate(checkQuery);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	//returns all versions for a project
	
	public static String[] returnProjectVersions(String project, String username) {
		
		String path = getProjectPath(project, username);
		ArrayList<String> arrlist = new ArrayList<String>();
		Connection conn = getConnection();
		String queryLargestVersion = "SELECT version from project_versions where project_directory = ?;";
		
		
		
		try {
			PreparedStatement ptst = conn.prepareStatement(queryLargestVersion);
			ptst.setString(1, path);
			ResultSet r1 = ptst.executeQuery();
			while(r1.next()) {
				arrlist.add(r1.getString(1));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		String[] version = new String[arrlist.size()];
		
		for(int i =0; i < arrlist.size(); ++i) {
			version[i] = arrlist.get(i);
		}
		return version;
	}
	
	// returns String[] of who pushed the version
	public static String[] returnWhoPushedTheVersion(String project, String username, String[] version) {
		
		Connection conn = getConnection();
		ArrayList<String> arrlist = new ArrayList<String>();
		String path = getProjectPath(project, username);
		String[] people = null;
		for(int i = 0; i < version.length; ++i) {
			String checkQuery = "SELECT user_who_pushed FROM project_versions WHERE version = \"" + version[i] + "\" AND project_directory = \"" + path + "\";";
			
			try(PreparedStatement stm = conn.prepareStatement(checkQuery))
			{
				stm.setString(1, path);
				ResultSet res = stm.executeQuery(checkQuery);
				if(res.next()) {
					arrlist.add(res.getString(1));
				}

			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}	
		}
		

		people = new String[arrlist.size()];
		System.err.println(username + "'s projects are: ");
		for(int i = 0; i < arrlist.size(); i++)
		{
			people[i] = arrlist.get(i);
		}
		return people;
		
	}
	
	//increments version number for each user push and creates a unique version directory that has that version's files
	
	public static Integer createNewVersion(String username, String project) {
		Connection conn = getConnection();
		String path = getProjectPath(project, username);
		System.out.println(path);
		String queryLargestVersion = "SELECT MAX(version) + 1 from project_versions where project_directory = ?;";
		int newVersion = 0;
		
		
		try {
			PreparedStatement ptst = conn.prepareStatement(queryLargestVersion);
			ptst.setString(1, path);
			ResultSet r1 = ptst.executeQuery();
			
			
			if(r1.next()) {
				newVersion = r1.getInt(1);
			}else {
				newVersion = 0;
			}
			Connection c = getConnection();
			PreparedStatement prep = c.prepareStatement("INSERT into project_versions(project_directory, version, user_who_pushed, version_directory) VALUES(?, ?, ?, ?);");
			prep.setString(1, path);
			prep.setString(2, Integer.toString(newVersion));
			prep.setString(3, username);
			prep.setString(4, path + "\\version" + Integer.toString(newVersion));
			
			prep.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(newVersion == 0) {
			return 0;
		}
		return newVersion;
	}
	
	
	
	
	// if there are no projects to that username, then it returns an empty String[]
	public static String[] userProjects(String username)
	{
		Connection conn = getConnection();
		ArrayList<String> arrlist = new ArrayList<String>();
		String[] projects = null;
		String checkQuery = "SELECT projects.project FROM userstoprojects JOIN projects ON projects.stored_directory = project_directory WHERE userstoprojects.username = \"" + username + "\";";
		try(Statement stm = conn.createStatement())
		{
			ResultSet res = stm.executeQuery(checkQuery);
			while(res.next())
			{
				arrlist.add(res.getString(1));
			}

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		projects = new String[arrlist.size()];
		System.err.println(username + "'s projects are: ");
		for(int i = 0; i < arrlist.size(); i++)
		{
			projects[i] = arrlist.get(i);
		}
		
		
		return projects;
	}
	
	public static String[] ownersOfProjectsForUser(String username) {
		String[] owners = null;
		Connection conn = getConnection();
		ArrayList<String> arrlist = new ArrayList<String>();
		String checkQuery = "SELECT projects.username FROM projects JOIN userstoprojects ON projects.stored_directory = project_directory WHERE userstoprojects.username = \"" + username + "\";";
		try(Statement stm = conn.createStatement())
		{
			ResultSet res = stm.executeQuery(checkQuery);
			while(res.next())
			{
				arrlist.add(res.getString(1));
			}

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		owners = new String[arrlist.size()];
		for(int i = 0; i < arrlist.size(); i++)
		{
			owners[i] = arrlist.get(i);
			System.err.println(owners[i]);
		}
		return owners;
		
	}
	

	public static String[] userProjectNames(String username)
	{
		Connection conn = getConnection();
		ArrayList<String> arrlist = new ArrayList<String>();
		String[] projects = null;
		String checkQuery = "SELECT projects.project FROM userstoprojects JOIN projects ON projects.stored_directory = project_directory WHERE userstoprojects.username = \"" + username + "\";";
		try(Statement stm = conn.createStatement())
		{
			ResultSet res = stm.executeQuery(checkQuery);
			while(res.next())
			{
				arrlist.add(res.getString(1));
			}

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		projects = new String[arrlist.size()];
		for(int i = 0; i < arrlist.size(); i++)
		{
			projects[i] = directoryToProjectName(arrlist.get(i));
		}
		return projects;
	}


	/*
	 * Checks if username is in the database, returns false if it is
	 * Otherwise inserts it into database and returns true
	 */
	public static boolean registerUser(String username, String password)
	{
		Connection conn = getConnection();

		String checkQuery = "SELECT * FROM users WHERE username = \"" + username + "\";";
		try(Statement stm = conn.createStatement())
		{
			ResultSet res = stm.executeQuery(checkQuery);
			if(res.next())
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}

		String insertUser = "INSERT INTO users (username, password)" + " VALUES (\"" + username + "\", \"" + password + "\");";
		try(Statement stm = conn.createStatement())
		{
			stm.executeUpdate(insertUser);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return true;
	}


	/*
	 * Takes username and password
	 * returns false if username and password does not exist in sql database
	 */
	public static boolean loginUser(String username, String password)
	{
		System.out.println(username + password);
		Connection conn = getConnection();
		String sqlQuery = "SELECT * FROM users WHERE username = ? AND password = ?;";
		try{
			PreparedStatement stm = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?;");
			stm.setString(1, username);
			stm.setString(2, password);
			ResultSet res = stm.executeQuery();
			if(!res.next())
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}

	/*
	*
	*/
	public static String directoryToProjectName(String directory)
	{
		String [] arr = directory.split("\\", 10);
		return arr[arr.length];
	}

	public static String directoryToOwner(String directory)
	{
		String [] arr = directory.split("\\", 10);
		return arr[arr.length - 2];
	}

}
