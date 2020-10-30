import java.util.Scanner;

public class userInterface {
	public void run(String user)
	{
		String projectName = "";
		Scanner scan = new Scanner(System.in);
		printMenu();
		String input = scan.nextLine();
		if(input.equals("1"))
		{

		}
		else if(input.equals("2"))
		{

		}
		else if(input.equals("3"))
		{

		}
		else
		{
			return;
		}
	}

	private void printProjectMenu()
	{
		String menu = "Please select an option. \n"
				+ "1.) View your project \n"
				+ "2.) Add a file \n"
				+ "3.) View your deadlines \n"
				+ "4.) View all filenames \n"
				+ "5.) View a file \n"
				+ "6.) Add a deadline \n"
				+ "7.) Add a comment \n"
				+ "8.) Add a task \n"
				+ "9.) Add a user \n";

		System.out.println(menu);
	}

	private void printMenu()
	{
		String menu = "Please select an option. \n"
				+ "1.) Log into a project \n"
				+ "2.) View your projects \n"
				+ "3.) Add a project";
		System.out.println(menu);
	}

	private String selectProject()
	{
		return "";
	}

	private void printProjects()
	{

	}

	private void addProject()
	{

	}

	private void printProject()
	{

	}

	private boolean addFile()
	{
		return true;
	}

	private void printDeadlines()
	{

	}

	private void printFiles()
	{

	}

	private void printFile()
	{

	}

	private boolean addDeadline()
	{
		return true;
	}

	private boolean addComment()
	{
		return true;
	}

	private boolean addTask()
	{
		return true;
	}

	private boolean addUser()
	{
		return true;
	}
}
