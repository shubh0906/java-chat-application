//package Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
public class ChatServer
{
	Handler r;
	JCheckBox jb[];
	int flag=0;
	private static final int PORT = 9001;
	JFrame frame = new JFrame("Login Details");
	JPanel jp1=new JPanel();
	JPanel jp2=new JPanel();
	JTextArea messageArea = new JTextArea(8,40);
//
	public static void main(String[] args) throws Exception
	{
		ChatServer cs=new ChatServer();
		cs.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	System.out.println("The chat server is running.");
        	ServerSocket listener = new ServerSocket(PORT);
        	try
		{
            		while (true) 
			{
                		new Handler(listener.accept(),cs).start();
            		}
		}
		finally 
		{
            		listener.close();
        	}
    	}
	public ChatServer()
	{
		frame.setLayout(new GridLayout(1,2));
		frame.setSize(400,400);
		frame.add(jp1);
		frame.add(jp2);

		jp1.setLayout(new BorderLayout());
		jp1.setBorder(BorderFactory.createTitledBorder("Server Log"));
		jp1.setSize(200, 400);

		jp1.add(messageArea);

		jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
	   	jp2.setBorder(BorderFactory.createTitledBorder("ACTIVE USERS...(Uncheck to block user)"));
	    	jp2.setSize(200, 400);

		SwingUtilities.invokeLater(new Runnable() 
	    {
	        @Override public void run() {
	            frame.setVisible(true);
	        }
	    });
		
		messageArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret) messageArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        	//frame.getContentPane().add(new JScrollPane(messageArea));
		frame.pack();
	}
	
	public JCheckBox[] showActiveUsers()
	{
        /*SwingUtilities.invokeLater(new Runnable() 
		{
            @Override
		
            public void run() 
            {	*/
		jp2.removeAll();
		jp2.repaint();
            	int totalActiveUsers = r.names.size();        				
                JCheckBox jcheckb[] = new JCheckBox[totalActiveUsers];
                Iterator<String> itr = r.names.iterator();
                String names []=new String [r.names.size()];        
                for(int i=0; i<jcheckb.length; i++)
                { 
                	names[i]=itr.next();
                        jcheckb[i] = new JCheckBox(names[i]+" ");
                        jp2.add(jcheckb[i]);
			jcheckb[i].setSelected(true);
                }                
                jp2.validate();
                jp2.repaint();
		jb=jcheckb;

           /* }
		
		});*/
		return jb;
	}	
}