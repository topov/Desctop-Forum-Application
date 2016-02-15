import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.*;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Messages extends JFrame implements Runnable
{
	JPanel panel;
	int userIDin=0;
	JTextArea container;
	public Messages() 
	  {
		  	setTitle("NEW THUG THEME");
		    setSize(400, 600);
		    setLocationRelativeTo(null);
	  }
	
	
	private void addPanel()
	{
		this.panel=new JPanel();
		panel.setLayout(null);
		this.getContentPane().add(panel);
		panel.setBackground(Color.WHITE);
		
		ImageIcon thug=new ImageIcon("thug1.png");
		JLabel label=new JLabel();
		label.setBounds(80,1, 400, 100);
		label.setIcon(thug);
		panel.add(label);	
		
		this.container = new JTextArea();
		this.container.setBounds(0,90, 383, 470);
		this.container.setEditable(false);
		this.container.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(container);
	}
	
	public void getInfo()
	{
		Socket connection = null;
    	Scanner socketIn = null;
    	PrintWriter socketOut = null;
    	Scanner keyboardIn = new Scanner(System.in);
    	int port = 1234;
    	String host = "localhost";
    	int counter=0;
    	ArrayList mess=new ArrayList();
    	ArrayList userSend=new ArrayList();
    	
    	try
	     {
	     try{connection = new Socket(host, port);}
	     catch(ConnectException e) {System.err.println("Не мога да осъществя връзка със сървъра");}
	     
	     socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(10);
		 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
		 socketIn.nextInt();
		 
		 socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(userIDin);
		 
		 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
		 counter=socketIn.nextInt();
		 socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(1);
		 if (counter>0)
		 {
			 
			 for(int i=0;i<counter;i++)
 	    	{ 	

		    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			        userSend.add(socketIn.nextLine());
			        socketOut = new PrintWriter(connection.getOutputStream(), true);
				 	socketOut.println(1);
				 
		    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
		    		mess.add(socketIn.nextLine());
		    		socketOut = new PrintWriter(connection.getOutputStream(), true);
					socketOut.println(1);
					
					this.container.append(userSend.get(i)+" says: "+ mess.get(i)+" \n");
 	    	}
	 	    
			 System.out.println(userSend.toString());
			 System.out.println(mess.toString());
			 
		 }
		 else{System.out.println("no messages");}
		 
	     }
    	catch(IOException e) {e.printStackTrace();}
	     finally
	       {
	         try
	         {
	           if(socketIn!=null) socketIn.close();
	           if(socketOut!=null) socketOut.close();
	           if(connection!=null) connection.close();
	         }
	         catch(IOException e)
	         {
	         System.err.println("Не може да се затвори сокета");
	         }
	       }
    	
	}
	
	public void getUser(int userIDD)
	{
		this.userIDin=userIDD;
	}
	public void run()
	{
		this.addPanel();
		this.getInfo();
		this.setVisible(true);
	}
}
