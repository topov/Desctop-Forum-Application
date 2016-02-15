import java.io.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



class SampleFrame extends JFrame implements Runnable
{
	JPanel ramka;
	JButton kopche;
  public SampleFrame()
  {
    setTitle("Topov's forum");
    setSize(800, 550);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  
  private void addJramka()
  {
	  this.ramka= new JPanel();
	  ramka.setLayout(null);
	  this.getContentPane().add(ramka);
  }
  private void addkopche(JPanel p)
  {
	    this.kopche = new JButton("exit");
	    this.kopche.setBounds(320, 460, 100, 50);
	 
	    
	    
	    kopche.addActionListener(
	    		new ActionListener() 
	    {
	    			@Override
	      public void actionPerformed(ActionEvent event) 
	      
	      {

	        setVisible(false);
	        
	      }
	    });
	    
	    
	    p.add(this.kopche);
	  }
  public void run() 
  {
	  this.addJramka();
	  this.addkopche(this.ramka);
      this.setVisible(true);
      
  }
}
