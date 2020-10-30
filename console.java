import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



class console {
	public static void main(String[] args)
	{
		handleUser handler = new handleUser();
		Connection conn = null;
		try
		{
			conn = getConnection();
		}
		catch(Exception e)
		{
			return;
		}
		handler.handleUser(conn);
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
}
