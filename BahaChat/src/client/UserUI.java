package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class UserUI implements ActionListener {
	
	private static String sIPAddress = "";
	private static String cName = "";
	private static int sPort = 0;
	
	JFrame frame = null;
	static JPanel panel = null;
	static JPanel cPanel = null; 
	
	JTextField ip_add, port, name, sendText = new JTextField(); 
	JButton submit, disconnect, send = new JButton(); 
	JLabel lName, lPort, lIPAddress = new JLabel();
	
	static JTextArea console = new JTextArea(5,25);
	static JScrollPane scroll = new JScrollPane(console);
	
	public UserUI() {
		
		//Name
		name = new JTextField();
		name.setBounds(250, 30, 120, 30);
		name.setVisible(true);
		
		//IP Address
		ip_add = new JTextField();
		ip_add.setBounds(250, 120, 120, 30);
		ip_add.setVisible(true);
		
		//Port
		port = new JTextField();
		port.setBounds(250, 210, 120, 30);
		port.setVisible(true);
		
		//Submit Button
		
		submit = new JButton();
		submit.setBounds(270, 300, 80, 30);
		submit.setText("Submit");
		submit.addActionListener(this);
		submit.setVisible(true);
		
		//Name Label
		lName = new JLabel();
		lName.setText("Enter your name");
		lName.setBounds(150, 35, 100, 20);
		
		//IP Label
		lIPAddress = new JLabel();
		lIPAddress.setText("Enter the Server IP Address");
		lIPAddress.setBounds(80, 125, 180, 20);
		
		//Port Label
		lPort = new JLabel();
		lPort.setText("Enter the Server Port");
		lPort.setBounds(100, 215, 130, 20);
		
		//Panel
		
		panel = new JPanel();
		panel.setSize(600,400);
		panel.setBackground(Color.GRAY);
		panel.setLayout(null);
		
		panel.add(name);
		panel.add(ip_add);
		panel.add(port);
		panel.add(submit);
		panel.add(lName);
		panel.add(lIPAddress);
		panel.add(lPort);
		
		
		//Send Button
		
		send= new JButton();
		send.setBounds(513, 200, 80, 25);
		send.setText("Send");
		send.addActionListener(this);
		
		//Disconnect Button
		
		disconnect = new JButton();
		disconnect.setBounds(250, 300, 100, 30);
		disconnect.setText("Disconnect");
		disconnect.addActionListener(this);
		
		//Console text area
		
		console.setFont(new Font("Arial Black", Font.BOLD, 15));
		console.setEditable(false);
		console.setBounds(0, 0, 600, 200);
		
		//Scroll vertical 
		
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(0, 0, 600, 200);
		
		//Write message
		sendText = new JTextField();
		sendText.setBounds(0, 200, 513, 25);
		sendText.addActionListener(this);
		
		//Chatting panel
		
		cPanel = new JPanel();
		cPanel.setSize(600,400);
		cPanel.setBackground(new Color(218,214,183));
		cPanel.setLayout(null);
		cPanel.add(send);
		cPanel.add(disconnect);
		cPanel.add(scroll);
		cPanel.add(sendText);
		
		//Frame
		frame = new JFrame("Baha Client");
		frame.setSize(600,400);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.add(panel);
		frame.add(cPanel);
		
		frame.setVisible(true);
		panel.setVisible(true);
		cPanel.setVisible(false);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			
			//TODO : Robust checks for IP Address, name, port 
			this.sIPAddress = ip_add.getText();
			this.cName = name.getText();
			this.sPort = Integer.parseInt(port.getText());
			
			System.out.println("Registered New Client : ");
			System.out.println("Name : " + this.cName);
			System.out.println("Connect to : " + this.sIPAddress + ":" + this.sPort);
			
			Client.attemptConnection();
		} else if (e.getSource() == disconnect) {
			Client.disconnectFromServer();
		} else if (e.getSource() == send) {
			Client.sendMessage(sendText.getText());
			sendText.setText("");
		} else {
			System.out.println("Unhandled source : " + e.getSource());
		}
		
	}
	
	public static String getName() {
		return cName;
	}
	
	public static String getIPAddress() {
		return sIPAddress;
	}

	public static int getPort() {
		return sPort;
	}
	
	public static void changeView(boolean option) {		
		if (option == true) { 		
			panel.setVisible(true);
			cPanel.setVisible(false);	
		} else { 	
			panel.setVisible(false);
			cPanel.setVisible(true);	
		}
	}
}
