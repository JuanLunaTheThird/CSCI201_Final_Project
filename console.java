import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

class console {
	
	
	public static main void(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter \"log in\" or \"register\"");
		String input = scan.nextLine();
		input = input.trim();
		input = input.toLowerCase();
		
		try
		{
			Connection conn = getConnection();
		}
		catch(Exception e)
		{
			return 0;
		}
		if(input == "log in")
		{
		  loginUser();
		}
		else if(input == "register")
		{
			registerUser();
			loginUser();
		}
		else
		{
		  System.out.println("invalid option");
		      return 0;
		}
	}

	private Connection getConnection()
	{
		Connection conn = null;
		String host = "jdbc:sqlserver://localhost:3306/cscifinalproject";
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
  
	private void registerUser(Connection conn)
	{
		bool validUser = false;
		while(!validUser)
		{
			System.out.println("Enter new Username: ");
			String username = scan.nextLine();
			System.out.println("Enter new password: ");
			String password = scan.nextLine();
			String checkQuery = "SELECT * FROM users WHERE username = \"" + username + "\";";
			try(Statement stm = conn.createStatement())
			{
				Results res = stm.execute(checkQuery);
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
		String insertUser = "INSERT INTO users VALUES(" + username + ", " + password + ");";
		try(Statement stm = conn.createStatement())
		{
			Results res = stm.execute(insertUser);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
  
	private void loginUser()
	{
		System.out.println("Enter Username:");
		String username = scan.nextLine();
		System.out.println("Enter password:");
		String password = scan.nextLine();
	}
}
