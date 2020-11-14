package test2;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class sqlQueries {

	private static Connection getConnection()
	{
		Connection conn = null;
		String host = "jdbc:mysql://104.12.140.24:3306/prohub";
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

	public static void main(String args[])
	{
//		boolean newe = createNewProject("j", "test");
		//String didit = getProjectPath("j", "test");
		addUserToProject("Juan", "test", "j");
		System.out.println("fe");
	}

  	/*
  	 * returns false if there is another project with the same name registered to the same user
  	 * returns true if project is successfully created and registered to user
  	 * inserts into project table and userstoproject
  	 * does not check if username is valid
  	 */
	public static boolean createNewProject(String username, String project)
	{
		Connection conn = getConnection();
		String path = "C:\\\\Users\\\\juan\\\\Desktop\\\\Juan\\\\Prohub\\\\" + username + "\\\\" + project;
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
	 */
	public static String getProjectPath(String project, String username)
	{
		Connection conn = getConnection();
		String path = "C:\\\\Users\\\\juan\\\\Desktop\\\\Juan\\\\Prohub\\\\" + username + "\\\\" + project;
		System.out.println(path);
		String checkQuery = "SELECT * FROM projects WHERE stored_directory = \"" + path + "\";";
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
	 */
	public static boolean addUserToProject(String username, String project, String owner)
	{
		Connection conn = getConnection();
		String path = "C:\\\\Users\\\\juan\\\\Desktop\\\\Juan\\\\Prohub\\\\" + owner + "\\\\" + project;
		String checkQuery = "SELECT * FROM userstoprojects WHERE username = \"" + username + "\" AND project_directory = \"" + project + "\";";
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

	// if there are no projects to that username, then it returns an empty String[]
	public static String[] userProjects(String username)
	{
		Connection conn = getConnection();
		String[] projects = null;
		String checkQuery = "SELECT * FROM userstoprojects WHERE username = \"" + username + "\";";
		try(Statement stm = conn.createStatement())
		{
			ResultSet res = stm.executeQuery(checkQuery);
			int size =0;
			if (res != null)
			{
				res.last();    // moves cursor to the last row
				size = res.getRow(); // get row id
				projects = new String[size];
				res.first();
				size = 0;
				do {
					projects[size] = res.getString(1);
				}while(res.next());
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
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
		Connection conn = getConnection();
		String sqlQuery = "SELECT * FROM users WHERE username = \"" + username + "\";";
		try(Statement stm = conn.createStatement())
		{
			ResultSet res = stm.executeQuery(sqlQuery);
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
}
