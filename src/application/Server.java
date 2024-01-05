/**********************************************
Workshop #9
Course: JAC444 - 4 Semester
Last Name: Benko
First Name: Dmytro
ID: 115223208
Section: ZAA
This assignment represents my own work in accordance with Seneca Academic Policy.
Dmytro Benko
Date: 01.12.2022
**********************************************/
package application;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Server extends JFrame{
	java.util.Date date = new java.util.Date();    
	static int num=0;
   JLabel Jstart = new JLabel("MultiThread Server started "+date );
   ArrayList<String> names;
   
   JPanel panel;
   JTextArea textArea = new JTextArea();
   ServerSocket server = null;
   Server(){ 		
     panel = new JPanel();
     panel.setLayout(new BorderLayout());
     panel.setBackground(Color.white);
     getContentPane().add(panel);
     panel.add("North", Jstart);
          panel.add("Center", textArea);
   	} 			
  public void listenSocket(){
    	try{
      server = new ServerSocket(4444); 
  
    	} 
catch (IOException e) { 
System.out.println("Could not listen on port 4444"+e.getMessage());
}
   while(true){
      ClientWorker w;

      try{
        w = new ClientWorker(server.accept(), textArea);
        Thread t = new Thread(w);
  	  ++num;
        t.start();
      } 
catch (IOException e) {
System.out.println("Accept failed: 4444"+e.getMessage());
}
}
   }
 protected void finalize(){
     try{
        server.close();
    	 } 
catch (IOException e) {
System.out.println("Could not close socket");
} 
}
  public static void main(String[] args){
      Server frame = new Server();
	frame.setTitle("Server Program: ");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
frame.setSize(300,250);
   frame.setVisible(true);
      frame.listenSocket();
  }
}


class ClientWorker implements Runnable {
  	private Socket client;
  	private JTextArea textArea;
	static String username="";
	Map<Integer, java.net.Socket> clients = new HashMap<Integer, java.net.Socket> ();

	static int num =0;
  	ClientWorker(Socket client, JTextArea textArea) {
   	this.client = client;
   	this.textArea = textArea;   
	
   	java.util.Date date = new java.util.Date();    
    System.out.println(date);   
    textArea.append("\nConnected from Socket [addr=" + client.getRemoteSocketAddress().toString()+ ",port=" +client.getPort()
     + ",localport=" + client.getLocalPort()+"] at " + date + "\n");
    clients.put(client.getPort(), client);

    num =0;
  	}
  	
public void run(){
    	String line;
    	BufferedReader in = null;
    	PrintWriter out = null;

    	

  
   while(true){
	   for (Iterator<Integer> iter = clients.keySet().iterator(); iter.hasNext(); )
   	{
		   
   		 int key = iter.next();
   		 java.net.Socket client = clients.get(key);
	   num++;
	   try{
	    	
		   in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	         out = new PrintWriter(client.getOutputStream(), true);

	         
	         line = in.readLine();
		    	System.out.println(clients.get(key));

	         out.println(line);

	 		 if(num==1) {
	       	  username = line;
	      	textArea.append("\nReceived username: " + line +"\n");
	         } else {
	         	textArea.append("\n" + line +'\n');
	         }

	      
	         
	    } 
	catch (IOException e) {
	System.out.println("in or out failed"+ e.getMessage());
	}
    }
    	}
  }
}






