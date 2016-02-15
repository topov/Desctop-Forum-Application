import java.io.*;
import java.util.*;
import java.sql.*;
import java.net.*;
public class ForumServer 
{
	
	public static void main(String args[]) 
	{
	    ServerSocket serverSocket = null;
	    Socket connection = null;
	    Scanner socketIn = null;
	    PrintWriter socketOut = null;
	    int port = 1234;
	    String username=null;
	    String password=null;
		String url= "jdbc:mysql://localhost:3306/forum";
		String user="emil";
		String pass="topsecret";
		String originalPassword=null;
		int userID=0;
		Statement stmt=null;
		ResultSet resultSet=null;
		PreparedStatement prepareStmt=null;

	    while(true)
	    {
	        try 
	        {
	          int value=0;
	          serverSocket = new ServerSocket(port);
	          System.out.println("Сървъра очаква свързване...");
	          connection = serverSocket.accept();
//======================================================================================
	          socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	          value= socketIn.nextInt();
	          System.out.println(value);
	          
	          switch(value)
	          {
	          case 0: break;
	          case 1:
//--------------------------------------------------------------
	        	Connection link= ForumServer.connect(url,user,pass);
	      		if(link==null)
	      		{
	      			System.out.println("Fucked up!");
	      		}
	      		else {System.out.println("Connected to DATABASE");}
	        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(1);
	        	  System.out.println("Сървъра очаква подаване на команда...");
		          socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
		          username=socketIn.nextLine();
		          socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(1);
	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
		          password=socketIn.nextLine();
		          try
		          { 
		            prepareStmt= link.prepareStatement("SELECT password FROM accounts WHERE username= ?");
		            prepareStmt.setString(1,username);
		            resultSet= prepareStmt.executeQuery();

		            	 while(resultSet.next())
				            {
				              
				              originalPassword=resultSet.getString("password");
				              System.out.println("Password: "+originalPassword);
				              if (password.equals(originalPassword))
				              {
				            	  System.out.println("passed");
				            	  socketOut = new PrintWriter(connection.getOutputStream(), true);
					    		  socketOut.println(1);
					    		  
					    		  break;
				              }
				              else 
				              {
				            	  System.out.println("Wrong password");
				            	  socketOut = new PrintWriter(connection.getOutputStream(), true);
					    		  socketOut.println(0);
					    		  break;
				              }
				            }
		            	 
		            	 prepareStmt= link.prepareStatement("SELECT id FROM accounts WHERE username= ?");
				          prepareStmt.setString(1,username);
				          resultSet= prepareStmt.executeQuery();
				          while(resultSet.next())
				            {
				        	  userID=resultSet.getInt("id");  
				        	  
				            }
		          }
		          catch(SQLException e)
		          {
		        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
				      socketOut.println(0);  
		            System.out.println("Error!");
		          }
		          finally
		          {
		            try
		            {
		              if (stmt!=null) stmt.close();
		              if (resultSet !=null) resultSet.close();
		              if(link!=null) link.close();
		            }
		            catch(SQLException e) {e.printStackTrace();}
		          }
		        break;
//--------------------------------------------------------------			                  	  
		          
	          case 2:
	        	  String newUsername=null;
	        	  String newPassword=null;
	        	  String firstname=null;
	        	  String lastname=null;
	        	  String town=null;
	        	  String mail=null;
	        	  
	        	  Connection reg= ForumServer.connect(url,user,pass);
		      		if(reg==null)
		      		{
		      			System.out.println("Fucked up!");
		      		}
		      		else {System.out.println("Connected to DATABASE");}
		      		try
		      		{
		      		socketOut = new PrintWriter(connection.getOutputStream(), true);
		    		socketOut.println(1);
		    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			        newUsername=socketIn.nextLine();
		      		
		      		socketOut = new PrintWriter(connection.getOutputStream(), true);
		    		socketOut.println(1);
		    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			        newPassword=socketIn.nextLine();
		      		
		      		socketOut = new PrintWriter(connection.getOutputStream(), true);
		    		socketOut.println(1);
		    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			        firstname=socketIn.nextLine();
		      		
		      		socketOut = new PrintWriter(connection.getOutputStream(), true);
		    		socketOut.println(1);
		    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			        lastname=socketIn.nextLine();
		      		
		      		socketOut = new PrintWriter(connection.getOutputStream(), true);
		    		socketOut.println(1);
		    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			        town=socketIn.nextLine();
		      		
		      		socketOut = new PrintWriter(connection.getOutputStream(), true);
		    		socketOut.println(1);
		    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			        mail=socketIn.nextLine();
		      		System.out.println(mail);
		      		}
		      		catch(NoSuchElementException e){System.out.println("няма подадени данни от клиента!");}
		      		
			        try
			          {
			        	prepareStmt= reg.prepareStatement("SELECT username FROM accounts WHERE username=?");
			        	prepareStmt.setString(1,newUsername);
			            resultSet= prepareStmt.executeQuery();
			            if(resultSet.next())
			            {
			            	System.out.println("NOPE");
			            	socketOut = new PrintWriter(connection.getOutputStream(), true);
						    socketOut.println(0);
			            }
			        	
			            else
			            {
			        	prepareStmt= reg.prepareStatement("CALL `doubleNames`(?, ?, ?, ?, ?, ?);");
			            prepareStmt.setString(1,newUsername);
			            prepareStmt.setString(2,newPassword);
			            prepareStmt.setString(3,firstname);
			            prepareStmt.setString(4,lastname);
			            prepareStmt.setString(5,town);
			            prepareStmt.setString(6,mail);
			            resultSet= prepareStmt.executeQuery();
			            System.out.println("Okey");
			            socketOut = new PrintWriter(connection.getOutputStream(), true);
					    socketOut.println(1);
			            }	
			          }
			          catch(SQLException e)
			          {		e.printStackTrace();
			        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
					      socketOut.println(0);  
					      System.out.println("Error!");
			          }
			          finally
			          {
			            try
			            {
			              if (stmt!=null) stmt.close();
			              if (resultSet !=null) resultSet.close();
			              if(reg!=null) reg.close();
			            }
			            catch(SQLException e) {e.printStackTrace();}
			          }
        	  	       break;  	  
//--------------------------------------------------------------
	          case 3:
	        	  ArrayList themes=new ArrayList() ;
	        	  String custom=null;
	        	  int listSize=0;
	        	  Connection getThemes= ForumServer.connect(url,user,pass);
		      		if(getThemes==null)
		      		{
		      			System.out.println("Fucked up!");
		      		}
		      		else {System.out.println("Connected to DATABASE");}  
	        	    try
			          {
	        	    	prepareStmt= getThemes.prepareStatement("SELECT theme FROM posts");
	        	    	resultSet= prepareStmt.executeQuery();
			            while(resultSet.next())
			            {	            	
			            	custom=resultSet.getString("theme");
			            	themes.add(custom); 	
			            }
			            
			            listSize=themes.size();
			            socketOut = new PrintWriter(connection.getOutputStream(), true);
			    		socketOut.println(listSize);
			    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
				        socketIn.nextInt();
	        	    	for(int i=0;i<listSize;i++)
	        	    	{
	        	    		socketOut = new PrintWriter(connection.getOutputStream(), true);
				    		socketOut.println(themes.get(i));
				    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
					        socketIn.nextInt();
	        	    	}
	        	    	          
			          }
	        	    catch(SQLException e)
			          {		e.printStackTrace();
			        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
					      socketOut.println(0);  
					      System.out.println("Error!");
			          }
			          finally
			          {
			            try
			            {
			              if (stmt!=null) stmt.close();
			              if (resultSet !=null) resultSet.close();
			              if(getThemes!=null) getThemes.close();
			            }
			            catch(SQLException e) {e.printStackTrace();}
			          }
	        	    
	          break;
//--------------------------------------------------------------  
	          case 4:
	        	  String resultTheme;
	        	  String customAnswer;
	        	  int numberAnsw;
	        	  int postID=0;
	        	  ArrayList answers=new ArrayList() ;
	        	  ArrayList customers=new ArrayList() ;
	        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(1);
	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			      resultTheme= socketIn.nextLine();
			      Connection getAnswers= ForumServer.connect(url,user,pass);
		      		if(getAnswers==null)
		      		{
		      			System.out.println("Fucked up!");
		      		}
		      		else {System.out.println("Connected to DATABASE");}  
		      		
		      		try
			          {	      			
		      			prepareStmt= getAnswers.prepareStatement("SELECT posts.theme,answers.content FROM answers "
		      					+ "JOIN posts ON posts.id=answers.post_id WHERE posts.theme=?");
		      			prepareStmt.setString(1,resultTheme);
	        	    	resultSet= prepareStmt.executeQuery();
	        	    	while(resultSet.next())
			            {	            	
			            	customAnswer=resultSet.getString("content");
			            	answers.add(customAnswer); 	
			            }
	        	    	  	numberAnsw=answers.size();
				            socketOut = new PrintWriter(connection.getOutputStream(), true);
				    		socketOut.println(numberAnsw);
				    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
					        socketIn.nextInt();
					        for(int i=0;i<numberAnsw;i++)
		        	    	{
		        	    		socketOut = new PrintWriter(connection.getOutputStream(), true);
					    		socketOut.println(answers.get(i));
					    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
						        socketIn.nextInt();
		        	    	}
	        	    	
					        prepareStmt= getAnswers.prepareStatement("SELECT posts.id FROM answers JOIN posts ON posts.id=answers.post_id WHERE"
					        		+ " posts.theme=?");
			      			prepareStmt.setString(1,resultTheme);
		        	    	resultSet= prepareStmt.executeQuery();
		        	    	
		        	    	while(resultSet.next())
				            {	            	
				            	postID=resultSet.getInt("id"); 	
				            }
		        	    	
		        	    	prepareStmt= getAnswers.prepareStatement("SELECT username from accounts JOIN answers "
		        	    			+ "ON accounts.id=answers.account_id WHERE answers.post_id=? ORDER BY answers.id");
			      			prepareStmt.setInt(1,postID);
		        	    	resultSet= prepareStmt.executeQuery();
		        	    	while(resultSet.next())
				            {	            	
				            	customAnswer=resultSet.getString("username");
				            	customers.add(customAnswer); 	
				            }
		        	    	
		        	    	for(int i=0;i<numberAnsw;i++)
		        	    	{
		        	    		socketOut = new PrintWriter(connection.getOutputStream(), true);
					    		socketOut.println(customers.get(i));
					    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
						        socketIn.nextInt();
		        	    	}
		        	    	socketOut = new PrintWriter(connection.getOutputStream(), true);
				    		socketOut.println(postID);
				    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
					        socketIn.nextInt();
	        	    	
			          }
		      		catch(SQLException e)
			          {		e.printStackTrace();
			        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
					      socketOut.println(0);  
					      System.out.println("Error!");
			          }
			          finally
			          {
			            try
			            {
			              if (stmt!=null) stmt.close();
			              if (resultSet !=null) resultSet.close();
			              if(getAnswers!=null) getAnswers.close();
			            }
			            catch(SQLException e) {e.printStackTrace();}
			          }
		      		
		      		
		      		
		      		
		      		
	        	  break;
//--------------------------------------------------------------  	        	  
	          case 5:
	        	  String currentAnsw;
	        	  int IDpost;
	        	  int currUserID;
	        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(1);	  
	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		  currentAnsw= socketIn.nextLine();
	        	  
	    		  socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(1);	  
	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		  IDpost= socketIn.nextInt();
	    		  
	    		  socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(1);	  
	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		  currUserID= socketIn.nextInt();
	        	  
	    		  Connection addAnswer= ForumServer.connect(url,user,pass);
		      		if(addAnswer==null)
		      		{
		      			System.out.println("Fucked up!");
		      		}
		      		else {System.out.println("Connected to DATABASE");} 
		      		try
			          {	
		      			prepareStmt= addAnswer.prepareStatement("INSERT INTO `forum`.`answers` (`content`, `answDate`, `id`, `account_id`, `post_id`) "
		      					+ "VALUES (?, NULL, NULL, ?, ?);");
		      			prepareStmt.setString(1,currentAnsw);
		      			prepareStmt.setInt(2,currUserID);
		      			prepareStmt.setInt(3,IDpost);
	        	    	prepareStmt.executeUpdate();
			          }
		      	    catch(SQLException e)
			          {		e.printStackTrace();
			        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
					      socketOut.println(0);  
					      System.out.println("Error!");
			          }
			          finally
			          {
			            try
			            {
			              if (stmt!=null) stmt.close();
			              if (resultSet !=null) resultSet.close();
			              if(addAnswer!=null) addAnswer.close();
			            }
			            catch(SQLException e) {e.printStackTrace();}
			          }
	        	  
	        	  
	        	 break;
//--------------------------------------------------------------  	        	  
	          case 6:
	        	  String newThemeTitle;
	        	  String newThemeContent;
	        	  int newPostID=0;
	        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(1);	  
	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		  newThemeTitle= socketIn.nextLine();	  
	        	  
	    		  socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(1);	  
	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		  newThemeContent= socketIn.nextLine();
	    		  
	    		  socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(1);	  
	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		  currUserID= socketIn.nextInt();
	        	  
	    		  Connection addTheme= ForumServer.connect(url,user,pass);
		      		if(addTheme==null)
		      		{
		      			System.out.println("Fucked up!");
		      		}
		      		else {System.out.println("Connected to DATABASE");} 
		      		try
			          {	
		      			prepareStmt= addTheme.prepareStatement("INSERT INTO `forum`.`posts` (`account_id`, `id`, `theme`) VALUES (?, NULL,?);");
		      			prepareStmt.setInt(1,currUserID);
		      			prepareStmt.setString(2,newThemeTitle);
		      			prepareStmt.executeUpdate();
		      			
		      			prepareStmt= addTheme.prepareStatement("SELECT id from posts WHERE posts.theme=?");
		      			prepareStmt.setString(1,newThemeTitle);
		      			resultSet= prepareStmt.executeQuery();
	        	    	while(resultSet.next())
			            {	            	
			            	newPostID= resultSet.getInt("id");
			            }
	        	    	
		      			
		      			prepareStmt= addTheme.prepareStatement("INSERT INTO `forum`.`answers` (`content`, `answDate`, `id`, `account_id`, `post_id`) "
		      					+ "VALUES (?, NULL, NULL, ?, ?);");
		      			prepareStmt.setString(1,newThemeContent);
		      			prepareStmt.setInt(2,userID);
		      			prepareStmt.setInt(3,newPostID);
	        	    	prepareStmt.executeUpdate();
		      			
			          }
		      		catch(SQLException e)
			          {		e.printStackTrace();
			        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
					      socketOut.println(0);  
					      System.out.println("Error!");
			          }
			          finally
			          {
			            try
			            {
			              if (stmt!=null) stmt.close();
			              if (resultSet !=null) resultSet.close();
			              if(addTheme!=null) addTheme.close();
			            }
			            catch(SQLException e) {e.printStackTrace();}
			          }
	    		 	
	        	  
	        	  break;
	          case 7:
	        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(userID);	  
	        	  break;
	        	  
//--------------------------------------------------------------  	        	  
	          case 8:
	        	  String searchedUser=null;
	        	  String nickname=null;
	        	  String name=null;
	        	  String secondName=null;
	        	  String city=null;
	        	  String email=null;
	        	  int searchedID=0;
	        	  
	        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(1);	  
	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		  searchedUser= socketIn.nextLine();

	    		  Connection search= ForumServer.connect(url,user,pass);
		      		if(search==null)
		      		{
		      			System.out.println("Fucked up!");
		      		}
		      		else {System.out.println("Connected to DATABASE");} 
		      		try
			          {	
		      			prepareStmt= search.prepareStatement("SELECT username,firstname,lastname,town,mail,id FROM accounts WHERE username=?");
		      			prepareStmt.setString(1,searchedUser);
		      			resultSet= prepareStmt.executeQuery();
		      			while(resultSet.next())
			            {	            	
			            	nickname= resultSet.getString("username");
			            	name= resultSet.getString("firstname");
			            	secondName= resultSet.getString("lastname");
			            	city= resultSet.getString("town");
			            	email= resultSet.getString("mail");
			            	searchedID= resultSet.getInt("id");

			            }
		      			 	
			            	
			            	if(nickname==null)
			            	{
			            		  socketOut = new PrintWriter(connection.getOutputStream(), true);
				  	    		  socketOut.println(0);	  
				  	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
				  	    		  socketIn.nextInt();
			            		System.out.println("No INFO");
			            	}
			            	else
			            	{
			            		  socketOut = new PrintWriter(connection.getOutputStream(), true);
				  	    		  socketOut.println(1);	  
				  	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
				  	    		  socketIn.nextInt();	
			            		
			            	  socketOut = new PrintWriter(connection.getOutputStream(), true);
			  	    		  socketOut.println(nickname);	  
			  	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			  	    		  socketIn.nextInt();
			  	    		  
			  	    		  socketOut = new PrintWriter(connection.getOutputStream(), true);
			  	    		  socketOut.println(name);	  
			  	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			  	    		  socketIn.nextInt();
			  	    		  
			  	    		  socketOut = new PrintWriter(connection.getOutputStream(), true);
			  	    		  socketOut.println(secondName);	  
			  	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			  	    		  socketIn.nextInt();
			  	    		  
			  	    		  socketOut = new PrintWriter(connection.getOutputStream(), true);
			  	    		  socketOut.println(city);	  
			  	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			  	    		  socketIn.nextInt();
			  	    		  
			  	    		  socketOut = new PrintWriter(connection.getOutputStream(), true);
			  	    		  socketOut.println(email);	  
			  	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			  	    		  socketIn.nextInt();
			  	    		  
			  	    		  socketOut = new PrintWriter(connection.getOutputStream(), true);
			  	    		  socketOut.println(searchedID);	  
			  	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			  	    		  socketIn.nextInt();
			            	}
		      			
			          }
		      		catch(SQLException e)
			          {		e.printStackTrace();
			        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
					      socketOut.println(0);  
					      System.out.println("Error!");
			          }
			          finally
			          {
			            try
			            {
			              if (stmt!=null) stmt.close();
			              if (resultSet !=null) resultSet.close();
			              if(search!=null) search.close();
			            }
			            catch(SQLException e) {e.printStackTrace();}
			          }
	        	  
	        	  break;
//--------------------------------------------------------------        	  
	          case 9:
	        	  String text;
	        	  int sender;
	        	  int receiver;
	        	  
	        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(1);	  
	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		  text= socketIn.nextLine();
	        	  
	    		  socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(1);	  
	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		  sender= socketIn.nextInt();
	        	  
	    		  socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(1);	  
	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		  receiver= socketIn.nextInt();
	        	  
	    		  Connection textMessage= ForumServer.connect(url,user,pass);
		      		if(textMessage==null)
		      		{
		      			System.out.println("Fucked up!");
		      		}
		      		else {System.out.println("Connected to DATABASE");} 
		      		try
			          {	
		      			prepareStmt= textMessage.prepareStatement("INSERT INTO `forum`.`messages` (`content`, `read`, `sender_id`, `receiver_id`) "
		      					+ "VALUES (?, '1', ?, ?)");
		      			prepareStmt.setString(1,text);
		      			prepareStmt.setInt(2,sender);
		      			prepareStmt.setInt(3,receiver);
	        	    	prepareStmt.executeUpdate();
			          }
		      		catch(SQLException e)
			          {		e.printStackTrace();
			        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
					      socketOut.println(0);  
					      System.out.println("Error!");
			          }
			          finally
			          {
			            try
			            {
			              if (stmt!=null) stmt.close();
			              if (resultSet !=null) resultSet.close();
			              if(textMessage!=null) textMessage.close();
			            }
			            catch(SQLException e) {e.printStackTrace();}
			          }
	        	  
	        	  
	        	  break;
//--------------------------------------------------------------	        	  
	          case 10:
	        	  int userIDin;
	        	  ArrayList messages=new ArrayList();
	        	  ArrayList senders=new ArrayList();
	        	  int counter=0;
	        	  String mess=null;
	        	  String currSender=null;
	        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
	    		  socketOut.println(1);	  
	    		  socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    		  userIDin= socketIn.nextInt();
	    		  
	    		  Connection getMessage= ForumServer.connect(url,user,pass);
		      		if(getMessage==null)
		      		{
		      			System.out.println("Fucked up!");
		      		}
		      		else {System.out.println("Connected to DATABASE");} 
		      		try
			          {	
		      			prepareStmt= getMessage.prepareStatement("SELECT accounts.username, messages.content FROM messages "
		      					+ "JOIN accounts ON messages.sender_id= accounts.id WHERE messages.receiver_id=? ORDER BY messages.id");
		      			prepareStmt.setInt(1,userIDin);
		      			resultSet= prepareStmt.executeQuery();
		      			while(resultSet.next())
			            {	            	
		      				currSender=resultSet.getString("username");
			            	senders.add(currSender); 
			            	mess=resultSet.getString("content");
			            	messages.add(mess);
			            	counter++;
			            }
		      			socketOut = new PrintWriter(connection.getOutputStream(), true);
			    		socketOut.println(counter);	
			    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
			    		socketIn.nextInt();
		      		
			    		if (counter>0)
		      			{
			    			for(int i=0;i<counter;i++)
		        	    	{
			    				
		        	    		socketOut = new PrintWriter(connection.getOutputStream(), true);
					    		socketOut.println(senders.get(i));
					    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
						        socketIn.nextInt();
			    				
						        socketOut = new PrintWriter(connection.getOutputStream(), true);
					    		socketOut.println(messages.get(i));
					    		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
						        socketIn.nextInt();
		        	    	}
			    			System.out.println(senders.toString());
			    			System.out.println(messages.toString());
			    			
			    			
		      			}
		      			else{}
		      			
			          }
		      		
		      		catch(SQLException e)
			          {		e.printStackTrace();
			        	  socketOut = new PrintWriter(connection.getOutputStream(), true);
					      socketOut.println(0);  
					      System.out.println("Error!");
			          }
			          finally
			          {
			            try
			            {
			              if (stmt!=null) stmt.close();
			              if (resultSet !=null) resultSet.close();
			              if(getMessage!=null) getMessage.close();
			            }
			            catch(SQLException e) {e.printStackTrace();}
			          }
	    		  
	    		  
	        	  break;
//--------------------------------------------------------------     	  
		          default: System.out.println("UNKNOWN operator"); break;
	          } 
	          
	          
//======================================================================================	          
	        }
	        catch(IOException e) {
	          e.printStackTrace();
	        }
	        finally{
	          try{
	            if (socketIn!=null) socketIn.close();
	            if (socketOut!=null) socketOut.close();
	            if (connection!=null) connection.close();
	            if (serverSocket!=null) serverSocket.close();
	          }
	          catch(IOException e){
	            System.err.println("Не може да бъде затворен сокет");
	          }
	        }
	   }
	      
	  }
	
	private static Connection connect (String url, String user, String password)
	{
		Connection result =null;
		try
		{
			result=DriverManager.getConnection(url,user,password);
		}
		catch(SQLException e)
		{
			System.out.println("Something get fucked");
		}
		return result;
	}
}
