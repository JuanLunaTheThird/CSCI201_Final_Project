import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



class console {
	public static void main(String[] args)
	{
		String user = "";
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter \"log in\" or \"register\"");
		String input = scan.nextLine();
		input = input.trim();
		input = input.toLowerCase();
		Connection conn = null;
		try
		{
			conn = getConnection();
		}
		catch(Exception e)
		{
			return;
		}
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
		      return;
		}
	}

	private static Connection getConnection()
	{
		Scanner scan = new Scanner(System.in);
		Connection conn = null;
		String host = "jdbc:mysql://localhost:3306/cscifinalproject";
		String username = "root";
		String password = "root";
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

	private static void registerUser(Connection conn)
	{
		Scanner scan = new Scanner(System.in);
		boolean validUser = false;
		String username = "";
		String password = "";
		while(!validUser)
		{
			System.out.println("Enter new Username: ");
			username = scan.nextLine();
			System.out.println("Enter new password: ");
			password = scan.nextLine();
			String checkQuery = "SELECT * FROM users WHERE username = \"" + username + "\";";
			try(Statement stm = conn.createStatement())
			{
				ResultSet res = stm.executeQuery(checkQuery);
				if(!res.next())
			    {
					validUser = true;
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
		System.out.println("done registering");
	}

	private static String loginUser(Connection conn)
	{
		Scanner scan = new Scanner(System.in);
		boolean validUser = false;
		String username = "";
		String password = "";
		while(!validUser)
		{
			System.out.println("Enter Username:");
			username = scan.nextLine();
			System.out.println("Enter password:");
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
