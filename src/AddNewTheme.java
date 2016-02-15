import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.*;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddNewTheme extends JFrame implements Runnable
{
	JPanel panel;
	JLabel title;
	JLabel content;
	JTextField titleField;
	JTextArea contentField;
	JButton add;
	int userIDin;
	
	public AddNewTheme()
	  {
		  	setTitle("NEW THUG THEME");
		    setSize(390, 550);
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
		label.setBounds(70,1, 400, 100);
		label.setIcon(thug);
		panel.add(label);
		
	}
	public void fields()
	{
		this.title=new JLabel();
		title.setBounds(10, 95, 110, 30);
		title.setText("title:  ");
		this.titleField = new JTextField();
		this.titleField.setBounds(10, 120, 350, 30);
		this.titleField.setText("");
		this.titleField.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(title);
		panel.add(titleField);
		
		contentField=new JTextArea(); 
		contentField.setBounds(10,170, 350,290);
		contentField.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(contentField);
		
		this.content=new JLabel();
		content.setBounds(10, 150, 110, 20);
		content.setText("Enter text here:  ");
		panel.add(content);
	}
	
	public void addButton()
	{
		
		this.add=new JButton("Add THEME");
		this.add.setBounds(10,470, 350, 30);
		this.add.addActionListener(
		new ActionListener() 
	    {
	    			@Override
	      public void actionPerformed(ActionEvent event) 
	      
	      {	
	   			
	    		getInfo();	
	    		setVisible(false);
	    				
	      }
	    });
		panel.add(this.add);		
	}
	
	public void getInfo()
	{
		Socket connection = null;
    	Scanner socketIn = null;
    	PrintWriter socketOut = null;
    	Scanner keyboardIn = new Scanner(System.in);
    	int port = 1234;
    	String host = "localhost";
    	
		String theme;
		String themeContent;
		theme=titleField.getText();
		themeContent=contentField.getText();
		if (theme.equals("")||themeContent.equals("")||theme.equals(" ")||themeContent.equals(" ")) {}
		else
		{

		try
	     {
	     try{connection = new Socket(host, port);}
	     catch(ConnectException e) {System.err.println("Не мога да осъществя връзка със сървъра");}
	     
	     socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(6);
		 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
		 socketIn.nextInt();
	     
		 socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(theme);
		 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
		 socketIn.nextInt();
	     
		 socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(themeContent);
		 
		 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
		 socketIn.nextInt();
		 socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(this.userIDin);	
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
		this.addButton();
		this.fields();
		this.setVisible(true);
		
	}
}
