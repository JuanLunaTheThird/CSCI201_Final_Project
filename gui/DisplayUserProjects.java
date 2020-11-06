import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import serialized.Packet;

import javax.swing.JButton;
import javax.swing.JComboBox;
public class DisplayUserProjects extends JPanel implements ActionListener {
	private JPanel panel;
	
	public DisplayUserProjects(String start) {
		super(new BorderLayout());	
		
		if(start.equals("start")) {
			
			
		}
		
		
	
		add(panel, BorderLayout.CENTER);
	}

	
	public void loadUserProjects(Packet projects) {
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
