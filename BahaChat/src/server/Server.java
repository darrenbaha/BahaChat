package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Scanner;

public class Server {
	
	//Server Objects
	private static Socket[] clients = new Socket[3]; //Max 4 clients
	private static ServerSocket server = null; 
	private static Scanner inputData = null; 
	private static Scanner[] inputDataMulti = new Scanner[3]; //Max 4 clients
	
	//Server GUI Objects
	static ServerUI ui = null; 
	
	//Server Variables
	private static boolean serverStatus = false; 
	private static int clientCounter = 0; 
	static SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
	static Date date = new Date(System.currentTimeMillis());
	
	public static void main(String args[]) {
		
		//Pre-initialization
		
		
		//start server
		ui = new ServerUI();
		
	}

	public static void Start() {
		
		if (serverStatus == false) {
		new Thread("serverConnectionHandler") {
			public void run() {
				try {
					printConsole("Starting Server...");		
					
					server = new ServerSocket(ServerConstants.port);
					
					serverStatus = true; 
					
					messageHandlerSingleThreadAlgorithm();
					
					printConsole("Server started!");
					
					while(serverStatus == true) {
					
					clients[clientCounter] = server.accept();
					inputData = new Scanner(clients[clientCounter].getInputStream());
					inputDataMulti[clientCounter] = new Scanner(clients[clientCounter].getInputStream());
					printConsole("Client " + clientCounter + " has joined.");
					clientCounter++;
					
					}
					
					
				} catch (IOException i) {
					System.out.println(i);
				} 
			}
		}.start();
		
		} else {
			System.out.println("Server has already started.");
		}
	}
	
	public static void messageHandler() {
		new Thread("serverMessageHandler") {
			public void run() {
				
				String msg = "";
				
				while(serverStatus) {
					if (inputData != null) {
						if (inputData.hasNextLine()) {
							msg = inputData.nextLine();
							System.out.println(msg);
							if (msg.equals("!exit")) {
								System.out.println("This client has exited!");
								clientCounter--;
							} else {
								printConsole(msg);	
								//TODO: Broadcast message to other clients.
							}
						}
					}
					try {
						Thread.sleep(ServerConstants.messageDelayTimer);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public static void messageHandlerSingleThreadAlgorithm() {
		new Thread("serverMessageHandler") {
			public void run() {
				
				String msg = "";
				
				while(serverStatus) {
					System.out.println("we here?");
					for (int i = 0; i < clientCounter; i++) {
							System.out.println("n da loop");
						if (inputDataMulti[i] != null) {
							System.out.println("Multi...");
							if (inputDataMulti[i].hasNextLine()) {
								System.out.println("but we don't have a next line?");
								msg = inputDataMulti[i].nextLine();
								System.out.println(msg);
								if (msg.equals("!exit")) {
									System.out.println("This client has exited!");
									clientCounter--;
								} else {
									printConsole(msg);	
									//TODO: Broadcast message to other clients.
								}
							}
						}
						try {
							Thread.sleep(ServerConstants.messageDelayTimer);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}.start();
	}
	
	public static void Stop() {
		
		System.out.print("Attempting to close server...");
		
		try {
			printConsole("Attempting to shutdown the server...");
			server.close();	
			serverStatus = false; 
			System.out.print("Done!");
			System.out.println();
			printConsole("Server is offline");
			for (int i = 0; i < clients.length; i++) {
				if (clients[i] != null) {
					clients[i].close();
				}
			}
			System.out.println();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static void printConsole(String msg) {
		
		ServerUI.console.append("[Console]" + formatter.format(date) + " - " + msg + "\n");
	}
}
