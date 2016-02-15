import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements Runnable
{
	JPanel panel;
	JList themeList;
	JTextArea answ;
	String[] themes;
	int listSize;
	String customResult;
	public JButton answerButt;
	public JButton newThemeButt;
	public JButton search;
	public JButton privateMessage;
	public JButton refreshButt;
	JTextArea addAnsw;
	int postID;
	int userIDin;
	ArrayList theme=new ArrayList();
	public MainWindow()
	{
		setTitle("OFFICIAL THUG'S FORUM");
	    setSize(950, 650);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation (EXIT_ON_CLOSE);
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
		
		ImageIcon skull=new ImageIcon("qko1.png");
		JLabel skulls=new JLabel();
		skulls.setBounds(0,70, 220, 150);
		skulls.setIcon(skull);
		panel.add(skulls);
	}
	

	private void getID()
	{
		 Socket connection = null;
	     Scanner socketIn = null;
	     PrintWriter socketOut = null;
	     Scanner keyboardIn = new Scanner(System.in);
	     int port = 1234;
	     String host = "localhost";
	     try
	     {
	     try{connection = new Socket(host, port);}
         catch(ConnectException e) {System.err.println("Не мога да осъществя връзка със сървъра");}
	     
	     socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(7);
		 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
		 this.userIDin=socketIn.nextInt();

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
	
	
	
	private void showThemes()
	{
		getThemes();	
		this.themes = new String[listSize];	
		this.themeList= new JList(theme.toArray());
		this.themeList.setBounds(220,70, 300,530);
		this.themeList.setBorder(BorderFactory.createLineBorder(Color.black));
		
		answ=new JTextArea(); 
		answ.setBounds(540,70, 300,290);
		answ.setBorder(BorderFactory.createLineBorder(Color.black));
		answ.setEditable(false);
		panel.add(answ);
		
		JLabel message=new JLabel();
		message.setBounds(540,300, 300,160);
		message.setText("Enter your answer here:");
		message.setForeground(Color.RED);
		panel.add(message);
		answerButton();
		addAnsw=new JTextArea(); 
		addAnsw.setBounds(540,390, 300,160);
		addAnsw.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(addAnsw);
		
		themeList.addListSelectionListener(
				new ListSelectionListener() 
				{
					@Override
					public void valueChanged(ListSelectionEvent e) 
					{
						if(e.getValueIsAdjusting())
						{
							JList result=(JList) e.getSource();

							getAnswers((String) result.getSelectedValue());
						}
					}
			    });
		panel.add(themeList);	
	}
	
	public void getThemes()
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
	     socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(3);
		 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
 		 this.listSize=socketIn.nextInt();
 		 socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(1);
 		 for(int i=0;i<listSize;i++)
 		 {
 			socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
 	 		theme.add(socketIn.nextLine());
 	 		socketOut = new PrintWriter(connection.getOutputStream(), true);
 			socketOut.println(1);
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


	public void getAnswers(String selectedTheme)
	{
		 Socket connection = null;
	     Scanner socketIn = null;
	     PrintWriter socketOut = null;
	     Scanner keyboardIn = new Scanner(System.in);
	     int port = 1234;
	     String host = "localhost";
	     int numberAnsw;
	     ArrayList answers=new ArrayList();
	     ArrayList customers=new ArrayList();
	     String container[]=null;
	     String custcont[]=null;
	     
	     
	     try
	     {
	     try{connection = new Socket(host, port);}
         catch(ConnectException e) {System.err.println("Не мога да осъществя връзка със сървъра");}
	     socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(4);
		 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
		 socketIn.nextInt();
		 socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(selectedTheme);
		 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
		 numberAnsw=socketIn.nextInt();
		 socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(1);
		 
		 for(int i=0;i<numberAnsw;i++)
 		 {
 			socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
 	 		answers.add(socketIn.nextLine());
 	 		socketOut = new PrintWriter(connection.getOutputStream(), true);
 			socketOut.println(1);
 		 }
		 
		 for(int i=0;i<numberAnsw;i++)
 		 {
 			socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
 	 		customers.add(socketIn.nextLine());
 	 		socketOut = new PrintWriter(connection.getOutputStream(), true);
 			socketOut.println(1);
 		 }
		 	socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	 		this.postID=socketIn.nextInt();
	 		socketOut = new PrintWriter(connection.getOutputStream(), true);
			socketOut.println(1);

		 
		 container=new String[numberAnsw];
		 custcont=new String[numberAnsw];
		 for(int i=0;i<numberAnsw;i++)
		 {
			 container[i]=(String) answers.get(i);
			 custcont[i]=(String) customers.get(i);
		 }
		 
		

		 answ.setText("");
		 for(int i = 0; i < numberAnsw; i++) 
		 {
			 answ.append(custcont[i]);
			 answ.append("  says: ");
			 answ.append(container[i]);
			 answ.append("\n");
		 }		
		 panel.updateUI();
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
	public void answerButton()
	{
		
		this.answerButt=new JButton("REPLY");
		this.answerButt.setBounds(540,570, 300, 30);
		this.answerButt.addActionListener(
		new ActionListener() 
	    {
	    			@Override
	      public void actionPerformed(ActionEvent event) 
	      
	      {	
	   			
	    				getAnsw();	
	    				
	      }
	    });
		panel.add(this.answerButt);		
	}
	public void getAnsw()
	{  
		Socket connection = null;
    	Scanner socketIn = null;
    	PrintWriter socketOut = null;
    	Scanner keyboardIn = new Scanner(System.in);
    	int port = 1234;
    	String host = "localhost";
		String currentAnsw=null;
		try
	     {
	     try{connection = new Socket(host, port);}
	     catch(ConnectException e) {System.err.println("Не мога да осъществя връзка със сървъра");}
		
	     currentAnsw=addAnsw.getText();
	     System.out.println(currentAnsw);
	     if (currentAnsw==null){System.out.println("FUCK YOU PAY ME !");}
	     else
		{
	     addAnsw.setText("");
	     socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(5);
		 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
		 socketIn.nextInt();
		 
		 socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(currentAnsw);
		 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
		 socketIn.nextInt();
		 
		 socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(postID);
		 socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
		 socketIn.nextInt();
		 
		 socketOut = new PrintWriter(connection.getOutputStream(), true);
		 socketOut.println(this.userIDin);	
			
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
	public void newThemeButton()
	{
		this.newThemeButt=new JButton("NEW THEME");
		this.newThemeButt.setBounds(5,300, 210, 30);
		this.newThemeButt.addActionListener(
		new ActionListener() 
	    {
	    			@Override
	      public void actionPerformed(ActionEvent event) 
	      
	      {	
	    				
	    				AddNewTheme newth= new AddNewTheme();
	    				newth.getUser(userIDin);
	    				newth.run();
	    				
	    				
	      }
	    });
		panel.add(this.newThemeButt);		
	}
	
	public void messages()
	{
		this.privateMessage=new JButton("MESSAGES");
		this.privateMessage.setBounds(5,380, 210, 30);
		this.privateMessage.addActionListener(
		new ActionListener() 
	    {
	    			@Override
	      public void actionPerformed(ActionEvent event) 
	      
	      {					
	    				Messages textMess= new Messages();
	    				textMess.getUser(userIDin);
	    				textMess.run();	 								
	      }
	    });
		panel.add(this.privateMessage);
	}
	
	public void searchButton()
	{
		
		this.search=new JButton("SEARCH");
		this.search.setBounds(5,340, 210, 30);
		this.search.addActionListener(
		new ActionListener() 
	    {
	    			@Override
	      public void actionPerformed(ActionEvent event) 
	      
	      {		
	    				SearchUser searching= new SearchUser();
	    				searching.getUser(userIDin);
	    				searching.run();					
	      }
	    });
		panel.add(this.search);		
	}
	
	public void refresh()
	{
		
		this.refreshButt=new JButton("REFRESH");
		this.refreshButt.setBounds(5,420, 210, 30);
		this.refreshButt.addActionListener(
		new ActionListener() 
	    {
	    			@Override
	      public void actionPerformed(ActionEvent event) 
	      
	      {		
	    	
	      }
	    });
		panel.add(this.refreshButt);		
	}
	public void run()
	{
		this.addPanel();
		this.showThemes();
		this.newThemeButton();
		this.messages();
		this.searchButton();
		this.refresh();
		this.getID();
		this.setVisible(true);
	
	}
}
