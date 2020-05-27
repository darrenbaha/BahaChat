package server;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class ServerUI implements ActionListener {
	
	//GUI Objects
	JFrame frame = null;
	JPanel panel = null;
	JButton start = new JButton();
	JButton stop = new JButton();
	static JTextArea console = new JTextArea(5,25);
	static JScrollPane scroll = new JScrollPane(console);
	
	public ServerUI() {
		
		
		//Start Button
		
		start.setText("Start");
		start.setBounds(190, 300, 80, 30);
		start.addActionListener(this);
		start.setVisible(true);
		
		//Stop Button
		
		stop.setText("Stop");
		stop.setBounds(270, 300, 80, 30);
		stop.addActionListener(this);
		stop.setVisible(true);
		
		//Console text area
		
		console.setFont(new Font("Arial Black", Font.BOLD, 15));
		console.setEditable(false);
		console.setBounds(0, 0, 600, 200);
		
		//Scroll vertical 
		
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(0, 0, 600, 200);
		
		//Base Panel
		panel = new JPanel();
		panel.setSize(600,400);
		panel.setLocation(0, 0);
		panel.setLayout(null);
		
		panel.add(scroll);
		panel.add(start);
		panel.add(stop);
		
		//Frame
		frame = new JFrame(ServerConstants.serverName+  " v" + ServerConstants.version);
		frame.setSize(600,400);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.getContentPane().setBackground(new Color(218,214,183));
		frame.add(panel);
		
		frame.setVisible(true);
		panel.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == start) {
			Server.Start();
		}
		
		if (e.getSource() == stop) {
			Server.Stop();
		}
		
	}
}
