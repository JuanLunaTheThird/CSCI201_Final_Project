import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class handleUser {

	public static String handleUser(Connection conn)
	{
		String user = "";
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter \"log in\" or \"register\"");
		String input = scan.nextLine();
		input = input.trim();
		input = input.toLowerCase();
		if(input.equals("log in"))
		{
			user = loginUser(conn);

		}
		else if(input.equals( "register"))
		{
			registerUser(conn);
			user = loginUser(conn);
		}
		else
		{
		  System.out.println("invalid option");
		      return "";
		}
		return user;
	}

	private static void registerUser(Connection conn)
	{
		Scanner scan = new Scanner(System.in);
		boolean validUser = false;
		String username = "";
		String password = "";
		while(!validUser)
		{
			System.out.print("Enter new Username: ");
			username = scan.nextLine();
			System.out.print("Enter new password: ");
			password = scan.nextLine();
			String checkQuery = "SELECT * FROM users WHERE username = \"" + username + "\";";
			try(Statement stm = conn.createStatement())
			{
				ResultSet res = stm.executeQuery(checkQuery);
				if(!res.next())
			    {
					validUser = true;
			    }
				else
				{
					System.out.println("username already exists! Pick another.");
				}
			}
			catch(SQLException e)
			{
				System.out.println(e.getMessage());
			}
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
		System.out.println("Successfully registered! Please login.");
	}

	private static String loginUser(Connection conn)
	{
		Scanner scan = new Scanner(System.in);
		boolean validUser = false;
		String username = "";
		String password = "";
		while(!validUser)
		{
			System.out.print("Enter Username:");
			username = scan.nextLine();
			System.out.print("Enter password:");
			password = scan.nextLine();
			String sqlQuery = "SELECT * FROM users WHERE username = \"" + username + "\";";
			try(Statement stm = conn.createStatement())
			{
				ResultSet res = stm.executeQuery(sqlQuery);
				if(!res.next())
				{
					System.out.println("Invalid username or password");
				}
				else
				{
					System.out.println("Welcome user " + username);
					validUser = true;
				}
			}
			catch(SQLException e)
			{
				System.out.println(e.getMessage());
			}
		}
		return username;

	}
}
