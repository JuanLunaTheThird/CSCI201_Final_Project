import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class MainGUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private static String LOGIN = "Login";
    private static String REGISTER = "Register";
	
	public MainGUI() {
		super(new BorderLayout());
		
		
		JButton login = new JButton(LOGIN);
		login.setActionCommand(LOGIN);
		login.addActionListener(this);
		
		
		JButton register = new JButton(REGISTER);
		register.setActionCommand(REGISTER);
		register.addActionListener(this);
		
		panel = new JPanel(new GridLayout(1,0));
		panel.add(login);
		panel.add(register);
		
		add(panel, BorderLayout.CENTER);
	}
	
	public static void createAndShowGUI() {
		
		JFrame frame = new JFrame("Please login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MainGUI credentials = new MainGUI();
		credentials.setOpaque(true);
		frame.setContentPane(credentials);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (LOGIN.equals(command)) {
			((Window) this.getRootPane().getParent()).dispose();
			LoginForm loginForm = new LoginForm("");
			loginForm.addCredentials();
        } else if (REGISTER.equals(command)) {
        	((Window) this.getRootPane().getParent()).dispose();
            LoginForm registerForm = new LoginForm("");
            registerForm.addCredentials();
        } 
    }
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

}
	
	

