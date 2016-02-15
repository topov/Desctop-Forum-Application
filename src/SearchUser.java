import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.*;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SearchUser extends JFrame implements Runnable
{
	JPanel panel;
	JTextField search;
	JTextArea message;
	JTextArea prof;
	JButton searchButt;
	JButton sendButton;
	int userIDin;
	int searchedID=0;
	
	public SearchUser() 
	  {
		  	setTitle("NEW THUG THEME");
		    setSize(600, 400);
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
		label.setBounds(1,1, 400, 100);
		label.setIcon(thug);
		panel.add(label);
		
	}

	public void fields()
	{
		this.search = new JTextField();
		this.search.setBounds(10, 90, 490, 30);
		this.search.setText("");
		this.search.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(search);
		this.message = new JTextArea();
		this.message.setBounds(260, 130, 240, 210);
		this.message.setText("");
		this.message.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(message);
		
		this.prof = new JTextArea();
		this.prof.setBounds(10, 130, 240, 210);
		this.prof.setEditable(false);
		this.prof.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(prof);
	}
	
	public void searchButt()
	{
		
		this.searchButt=new JButton("SEARCH");
		this.searchButt.setBounds(500,90, 85, 30);
		this.searchButt.addActionListener(
		new ActionListener() 
	    {
	    			@Override
	      public void actionPerformed(ActionEvent event) 
	      
	      {	
	   			
	    	getNickname();				
	    				
	      }
	    });
		panel.add(this.searchButt);		
	}
	
	public void sendMessage()
	{
		
		this.sendButton=new JButton("SEND");
		this.sendButton.setBounds(500,310, 85, 30);
		this.sendButton.addActionListener(
		new ActionListener() 
	    {
	    			@Override
	      public void actionPerformed(ActionEvent event) 
	      
	      {	
	   			
	    				sendingMessage();				
	    				
	      }
	    });
		panel.add(this.sendButton);		
	}
	
	public void getNickname()
	{
		String tipedName;
		String searchedUser=null;
		String nickname=null;
  	  	String name=null;
  	  	String secondName=null;
  	  	String city=null;
  	  	String email=null;
  	  	
  	  	int check=0;
  	  	
		Socket connection = null;
    	Scanner socketIn = null;
    	PrintWriter socketOut = null;
    	Scanner keyboardIn = new Scanner(System.in);
    	int port = 1234;
    	String host = "localhost";
    	prof.setText("");
    	
		tipedName=search.getText();
		if (tipedName.equals("")||tipedName.equals(" ")){}
		else 
		{
			try
		     {
		     try{connection = new Socket(host, port);}
		     catch(ConnectException e) {System.err.println("Не мога да осъществя връзка със сървъра");}
		     
		     socketOut = new PrintWriter(connection.getOutputStream(), true);
			 socketOut.println(8);
			 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			 socketIn.nextInt();
			 socketOut = new PrintWriter(connection.getOutputStream(), true);
			 socketOut.println(tipedName);
			 
			 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
   		     check= socketIn.nextInt();
			 socketOut = new PrintWriter(connection.getOutputStream(), true);
		     socketOut.println(1);
			 if(check>0)
			 {
			 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			 nickname= socketIn.nextLine();
			 socketOut = new PrintWriter(connection.getOutputStream(), true);
   		     socketOut.println(1);
   		     
   		     socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			 name= socketIn.nextLine();
			 socketOut = new PrintWriter(connection.getOutputStream(), true);
		     socketOut.println(1);
		     
		     socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			 secondName= socketIn.nextLine();
			 socketOut = new PrintWriter(connection.getOutputStream(), true);
   		     socketOut.println(1);
   		     
   		     socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			 city= socketIn.nextLine();
			 socketOut = new PrintWriter(connection.getOutputStream(), true);
		     socketOut.println(1);
		     
		     socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			 email= socketIn.nextLine();
			 socketOut = new PrintWriter(connection.getOutputStream(), true);
   		     socketOut.println(1);
   		     
   		     socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
   		     searchedID= socketIn.nextInt();
			 socketOut = new PrintWriter(connection.getOutputStream(), true);
		     socketOut.println(1);
		     
		     prof.append("Username: "+nickname);
		     prof.append("\n");
		     prof.append("Firstname: "+name);
		     prof.append("\n");
		     prof.append("Lastname: "+secondName);
		     prof.append("\n");
		     prof.append("Town: "+city);
		     prof.append("\n");
		     prof.append("Meil: "+email);
		     prof.append("\n");
		     this.sendMessage();
		     sendButton.setVisible(true);
		     panel.updateUI();
			 }
			 else 
			 {
				 prof.append("NO INFORMATION !!!");
				 sendButton.setVisible(false); 
				 panel.updateUI();
			 }
			 
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
	}
	
	public void sendingMessage()
	{
		Socket connection = null;
    	Scanner socketIn = null;
    	PrintWriter socketOut = null;
    	Scanner keyboardIn = new Scanner(System.in);
    	int port = 1234;
    	String host = "localhost";
		
		String text;
		text=message.getText();
		if (text.equals("")||text.equals(" ")){}
		else 
		{
			try
		     {
		     try{connection = new Socket(host, port);}
		     catch(ConnectException e) {System.err.println("Не мога да осъществя връзка със сървъра");}
		     
		     socketOut = new PrintWriter(connection.getOutputStream(), true);
			 socketOut.println(9);
			 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			 socketIn.nextInt();
			 
			 socketOut = new PrintWriter(connection.getOutputStream(), true);
			 socketOut.println(text);
			 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			 socketIn.nextInt();
			 
			 socketOut = new PrintWriter(connection.getOutputStream(), true);
			 socketOut.println(userIDin);
			 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			 socketIn.nextInt();
			 
			 socketOut = new PrintWriter(connection.getOutputStream(), true);
			 socketOut.println(searchedID);
			// socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			// socketIn.nextInt();
			 
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
	}
	
	
	
	
	public void getUser(int userIDD)
	{
		this.userIDin=userIDD;
	}
	public void run()
	{
		this.addPanel();
		this.fields();
		this.searchButt();
		this.setVisible(true);
	}
}
