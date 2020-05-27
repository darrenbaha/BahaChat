package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import server.ServerUI;

public class Client {

	private static Socket client = null;
	private static int version = 1;
	static UserUI ui = null;
	static PrintWriter sender = null;
	
	static boolean connected = false;
	
	static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
	static Date date = new Date(System.currentTimeMillis());
	
	public static void main(String args[]) {
		
		ui = new UserUI();
		
	}
	
	public static void attemptConnection() {
		
		System.out.println("Attempting to connect to server : " + ui.getIPAddress() + ":" + ui.getPort());
		
		try {
			
			client = new Socket(ui.getIPAddress(), ui.getPort());
			System.out.println("Connected to Server!");
			connected = true; 
			UserUI.changeView(false);
			
			sender = new PrintWriter(client.getOutputStream());
			
			//waitForMessage();
			//sendMessage();
			
		}  catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}
	}
	
	private static void waitForMessage() {
		
		new Thread("clientMessageHandler") {
			public void run() {
				while(connected = true) {
					System.out.println("Connected to server waiting for message...");
				}
				System.out.println("Lost connection to server!");
			}
		}.start();
		
	}
	
	public static void disconnectFromServer() {
		if (connected == true) {
			try {
				sender.println("!exit ");
				sender.flush();
				client.close();
				UserUI.changeView(true);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	public static void sendMessage(String msg) {
		if (connected == true) {
			
		sender.println("[User] " + msg);
		sender.flush();
		
		printConsole(msg);
		}
	}
	
	public static void printConsole(String msg) {
		
		UserUI.console.append(formatter.format(date) + " > " + msg + "\n");
	}
}
