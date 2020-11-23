package sql;

public class SQLtest {
	
	public static void main(String[] args) {
		//sqlQueries.createNewVersion("funshinebear", "Testy test");
		sqlQueries.createNewVersion("amanda", "Testy test");
		//String[] proj = sqlQueries.returnProjectVersions("Testy test", "funshinebear");
		System.out.println(sqlQueries.createNewVersion("amanda", "Testy test"));
		
		
	}
	
}
