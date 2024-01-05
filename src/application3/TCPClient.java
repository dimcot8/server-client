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

package application3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.net.*;
public class TCPClient extends JFrame implements ActionListener {
	public static int num = 0;

   JLabel text;
   JButton button;
   JPanel panel;
   JPanel secpanel;

   JPanel secPanel;
   JPanel fullPanel;

   JTextField textField;
   
   JLabel label;
   JTextArea textArea;
   JTextArea userTextArea;

   public static String username;
   JButton textButton;

   
   Socket socket = null;
   PrintWriter out = null;
   BufferedReader in = null;
   String line;
   TCPClient(){ 		
	   
	   
     text = new JLabel("Enter your name: ");
     textField = new JTextField(20);
     button = new JButton("Send");
     button.addActionListener(this);
     
     panel = new JPanel();
     secPanel = new JPanel();
     
fullPanel  = new JPanel();
secPanel  = new JPanel();
textArea = new JTextArea();

panel.setLayout(new BorderLayout());

secPanel.setLayout(new FlowLayout());

     panel.setBackground(Color.white);
     getContentPane().add(panel);
     panel.add(text, BorderLayout.PAGE_START);
     panel.add(textArea, BorderLayout.CENTER);
     secPanel.add(textField);
     secPanel.add(button);
     
     panel.add(secPanel, BorderLayout.SOUTH);


   } 			
  public void actionPerformed(ActionEvent event){
     Object source = event.getSource();
     	if(source == button){
	
          String str = textField.getText();
          if(username!=null) {
              out.println(username + ": " +str);

          }else {
         out.println(str);
          }
	 	 textField.setText(new String(""));
num++;
	 	 try{
	 	 line = in.readLine();
          System.out.println("Received: " + line); 

          if(num==1) {
        	  username = line;
        	  text.setText("Enter your name: " + username);
          } else {
        	  textArea.append('\n' + username + ": " + str + '\n');
          }
          
       } 
catch (IOException e){ System.out.println("Read failed");}	 
     }
}
public void listenSocket(){
     try{
       socket = new Socket("localhost", 4444); 
      out = new PrintWriter(socket.getOutputStream(), true);
       in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  } 
catch (UnknownHostException e) {
System.out.println("Unknown host:" +e.getMessage());
}
catch  (IOException e) {
System.out.println("IOException"+e.getMessage());
}     
 }

public static void main(String[] args){
        TCPClient frame = new TCPClient();
		frame.setTitle("Client");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
frame.setSize(300,250);
       	frame.setVisible(true);
		
       	frame.listenSocket();
}
}


