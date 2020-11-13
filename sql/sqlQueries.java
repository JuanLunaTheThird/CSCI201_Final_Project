import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class sqlQueries {

  private static Connection getConnection()
	{
		Scanner scan = new Scanner(System.in);
		Connection conn = null;
		String host = "jdbc:mysql://104.12.140.24:3306/cscifinalproject";
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
	  registerUser("k", "k");
  }

  public boolean registerUser(String username, String password)
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

	public static boolean loginUser(String username, String password)
	{
		Connection conn = getConnetion();
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
