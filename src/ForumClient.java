import java.io.*;
import java.util.*;
import java.sql.*;
import java.net.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForumClient 
{
	public static void main(String args[]){
	     Socket connection = null;
	     Scanner socketIn = null;
	     PrintWriter socketOut = null;
	     Scanner keyboardIn = new Scanner(System.in);
	     int port = 1234;
	     String host = "localhost";

	     try
	     {
	         try
	         {
	           connection = new Socket(host, port);
	         }
	         catch(ConnectException e)
	         {
	           System.err.println("Не мога да осъществя връзка със сървъра");
	           return;
	         }
	         

	         LogIn user=new LogIn();
	         user.run(); 
	         String[] top=new String [2];
	        	 
	         while (true)
	         {
	        	 
	        	
	        	 socketOut = new PrintWriter(connection.getOutputStream(), true);
	             socketOut.println("Възможните команди са \"hi\" и \"exit\"");
	        	 try
	        	 {
	        	 if (top[0].equals(top[1]))
	        	 {
	        		 user.hide();
	        		 break;
	        	 }
	        	 else 
	        	 {
	        		 continue; 
	        	 }
	        	 }   
	        	 
	        	 catch(NullPointerException e)
	        	 {
	        		 continue;
	        	 }
	        	 
	         }
	         System.out.println("LOGGED IN!!!!");
	         System.out.println(top[0] + "  " + top[1]);
	        
	         
	         
	         
	         
	         
	         
	         
	         
	         //
	         // TO DO 
	         //
	         

	         
	         
	         
	         
	         
	         
	         
	         
	         
	         
	         
	         
	         
	         
	         
	       }
	       catch(IOException e) {
	         e.printStackTrace();
	       }
	       finally{
	         try{
	           if(socketIn!=null) socketIn.close();
	           if(socketOut!=null) socketOut.close();
	           if(connection!=null) connection.close();
	         }
	         catch(IOException e){
	           System.err.println("Не може да се затвори сокета");
	         }
	       }
	   }
}
