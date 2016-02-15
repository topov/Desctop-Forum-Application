import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.*;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn extends JFrame implements Runnable
{
	
	JPanel panel;
	public JTextField username;
	private JPasswordField password;
	public JButton submitbutt;
	public JButton regbutt;
	String[] value=new String[2];
	Register newUser;
	
	public LogIn()
	  {
		  	setTitle("Topov's forum");
		    setSize(450, 450);
		    setLocationRelativeTo(null);
		    setDefaultCloseOperation(EXIT_ON_CLOSE);
	  }
//*****************************************************************//
	private void addPanel()
	{
		this.panel=new JPanel();
		panel.setLayout(null);
		this.getContentPane().add(panel);
		panel.setBackground(Color.WHITE);
		
		ImageIcon thug=new ImageIcon("thug1.png");
		JLabel label=new JLabel();
		label.setBounds(115,60, 400, 100);
		label.setIcon(thug);
		panel.add(label);
	}
//*****************************************************************//	
	public void usernameField(JPanel p)
	{
		JLabel text=new JLabel();
		text.setBounds(40, 150, 110, 30);
		text.setText("Enter Username: ");
		this.username = new JTextField();
		this.username.setBounds(150, 150, 160, 30);
		this.username.setText("eclipse");
		p.add(this.username);
		p.add(text);
	}
	public void passwordField (JPanel p)
	{
		JLabel text=new JLabel();
		text.setBounds(40, 190, 110, 30);
		text.setText("Enter Password: ");
		this.password= new JPasswordField();
		this.password.setBounds(150, 190, 160, 30);
		this.password.setText("check");
		p.add(this.password);
		p.add(text);
	}
//*****************************************************************//		
	public void getValuee()
	{
		this.value[0]=username.getText();
		this.value[1]=password.getText();
	}
	

	public void submitButton()
	{
		
		this.submitbutt=new JButton("Submit");
		this.submitbutt.setBounds(150,230, 159, 40);
		this.submitbutt.addActionListener(
		new ActionListener() 
	    {
	    			@Override
	      public void actionPerformed(ActionEvent event) 
	      
	      {	
	    			sendInfo();
	      }
	    });
		panel.add(this.submitbutt);
		
		
	}

	
	public void sendInfo()
	{
		 Socket connection = null;
	     Scanner socketIn = null;
	     PrintWriter socketOut = null;
	     Scanner keyboardIn = new Scanner(System.in);
	     int port = 1234;
	     String host = "localhost";
	     int check=0;
	     try
	     {
	         try{connection = new Socket(host, port);}
	         catch(ConnectException e) {System.err.println("Не мога да осъществя връзка със сървъра");}
	        
	        //=================================================================================//
	       	
	 	    			getValuee();		
	 	    			System.out.println("Hello "+ value[0]);
	 	    			System.out.println("Your password is:"+value[1]);
	 	    			socketOut = new PrintWriter(connection.getOutputStream(), true);
	 	    			socketOut.println(1);
	 	    			socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	 	    			socketIn.nextLine();
	 	    			try 
	 	    			{
							socketOut = new PrintWriter(connection.getOutputStream(), true);
							socketOut.println(value[0]);
						} 
	 	    			catch (IOException e)  {e.printStackTrace();}
	 	    			socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	 	    			socketIn.nextLine();
	 	    			try 
	 	    			{
							socketOut = new PrintWriter(connection.getOutputStream(), true);
							socketOut.println(value[1]);
						} 
	 	    			catch (IOException e)  {e.printStackTrace();}
	 	    			try
	 	    			{
	 	    			socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	 	    			check=socketIn.nextInt();
	 	    			}
	 	    			catch(NoSuchElementException e){System.out.println("ERR");}
	 	    			System.out.println(check);
	 	    			if (check==1)
	 	    			{
	 	    				setVisible(false);
	 	    				SwingUtilities.invokeLater(new MainWindow());		
	 	    			}
	 	    			else 
	 	    			{
	 	    				JLabel text=new JLabel();
	 	    				text.setBounds(130, 260, 210, 30);
	 	    				text.setText("WRONG USERNAME OR PASSWORD !");
	 	    				text.setForeground(Color.RED);
	 	    				panel.add(text);
	 	    				panel.updateUI();
	 	    			}
	 		 //=================================================================================// 
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
	
	public void register()
	{
		
		this.regbutt=new JButton("REGISTER");
		this.regbutt.setBounds(150,280, 159, 40);
		this.regbutt.addActionListener(
		new ActionListener() 
	    {
	    			@Override
	      public void actionPerformed(ActionEvent event) 
	      
	      {	
	    	Registration();		
	      }
	    });
		panel.add(this.regbutt);	
	}
	public void Registration()
	{
		this.newUser= new Register();
		this.newUser.run();
	}
		
	
	public void run()
	{
		this.addPanel();
		this.usernameField(panel);
		this.passwordField(panel);
		this.submitButton();
		register();
		this.setVisible(true);
	
	}
}
