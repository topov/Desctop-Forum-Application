import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame implements Runnable
{
	JPanel panel;
	public JTextField username;
	private JPasswordField password;
	private JPasswordField password1;
	private JTextField firstname;
	private JTextField lastname;
	private JTextField town;
	private JTextField mail;
	public JButton submitbutt;
	String[] value=new String[7];
	
	public Register()
	  {
		  	setTitle("NEW THUG");
		    setSize(450, 450);
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
		label.setBounds(115,10, 400, 100);
		label.setIcon(thug);
		panel.add(label);
	}
	
	public void fields(JPanel p)
	{
		JLabel name=new JLabel();
		name.setBounds(40, 100, 110, 30);
		name.setText("Enter Username:* ");
		this.username = new JTextField();
		this.username.setBounds(150, 100, 160, 30);
		this.username.setText("Username");
		
		JLabel pass=new JLabel();
		pass.setBounds(40, 140, 110, 30);
		pass.setText("Enter Password:* ");
		this.password= new JPasswordField();
		this.password.setBounds(150, 140, 160, 30);
		this.password.setText("Password");
		
		JLabel pass1=new JLabel();
		pass1.setBounds(40, 180, 110, 30);
		pass1.setText("Password Again:* ");
		this.password1= new JPasswordField();
		this.password1.setBounds(150, 180, 160, 30);
		this.password1.setText("REpassword");
		
		JLabel fname=new JLabel();
		fname.setBounds(40, 220, 110, 30);
		fname.setText("Your first name:* ");
		this.firstname=new JTextField();
		this.firstname.setBounds(150, 220, 160, 30);
		
		JLabel lname=new JLabel();
		lname.setBounds(40, 260, 110, 30);
		lname.setText("Your last name:* ");
		this.lastname=new JTextField();
		this.lastname.setBounds(150, 260, 160, 30);
		
		JLabel townT=new JLabel();
		townT.setBounds(40, 300, 110, 30);
		townT.setText("Your town:* ");
		this.town=new JTextField();
		this.town.setBounds(150, 300, 160, 30);
		
		JLabel mailT=new JLabel();
		mailT.setBounds(40, 340, 110, 30);
		mailT.setText("Your Email:* ");
		this.mail=new JTextField();
		this.mail.setBounds(150, 340, 160, 30);
		
	
		p.add(this.mail);
		p.add(mailT);
		p.add(this.town);
		p.add(townT);
		p.add(this.lastname);
		p.add(lname);
		p.add(this.firstname);
		p.add(fname);
		p.add(this.password);
		p.add(pass);
		p.add(this.username);
		p.add(name);
		p.add(this.password1);
		p.add(pass1);
	}
	
	public void submitButton()
	{
		
		this.submitbutt=new JButton("REGISTER");
		this.submitbutt.setBounds(150,375, 159, 30);
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
	         getFields();
	         socketOut = new PrintWriter(connection.getOutputStream(), true);
  			 socketOut.println(2);
  			 
  			 if(value[1].equals(value[2])&&value[0].length()>5&&value[1].length()>1&&
  				value[3].length()>1&&value[4].length()>1&&value[5].length()>1&&value[6].length()>1)
  			 {
  				socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		socketIn.nextLine(); 
	    		socketOut = new PrintWriter(connection.getOutputStream(), true);
	  			socketOut.println(value[0]);
  				
	  			socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		socketIn.nextLine(); 
	    		socketOut = new PrintWriter(connection.getOutputStream(), true);
	  			socketOut.println(value[1]);
  				 
	  			socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		socketIn.nextLine(); 
	    		socketOut = new PrintWriter(connection.getOutputStream(), true);
	  			socketOut.println(value[3]); 
  				 
	  			socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		socketIn.nextLine(); 
	    		socketOut = new PrintWriter(connection.getOutputStream(), true);
	  			socketOut.println(value[4]);
	  			
	  			socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		socketIn.nextLine(); 
	    		socketOut = new PrintWriter(connection.getOutputStream(), true);
	  			socketOut.println(value[5]);
  				
	  			socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		socketIn.nextLine(); 
	    		socketOut = new PrintWriter(connection.getOutputStream(), true);
	  			socketOut.println(value[6]);
	  			
	  			try
	    			{
	    			socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    			check=socketIn.nextInt();
	    			}
	    			catch(NoSuchElementException e){System.out.println("ERR");}
  				 if(check>0)
  				 {
  					 setVisible(false);
  				 }
  				 else
  				 {
  					JLabel message=new JLabel();
  	 				message.setBounds(40,375, 150, 30);
  	 				message.setText("WRONG DATAA !");
  	 				message.setForeground(Color.RED);
  	 				panel.add(message);
  	 				panel.updateUI();
  				 }
  				 
  			 }
  			 else
  			 {
  				JLabel message=new JLabel();
 				message.setBounds(40,375, 150, 30);
 				message.setText("WRONG DATA !");
 				message.setForeground(Color.RED);
 				panel.add(message);
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
	
	public void getFields()
	{
		this.value[0]=username.getText();
		this.value[1]=password.getText();
		this.value[2]=password1.getText();
		this.value[3]=firstname.getText();
		this.value[4]=lastname.getText();
		this.value[5]=town.getText();
		this.value[6]=mail.getText();
	}
	
	
	
	public void run()
	{
		this.addPanel();
		this.submitButton();
		this.fields(panel);
		this.setVisible(true);
	}
}
