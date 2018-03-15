import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Date;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;
import java.util.*;

public class Handler extends Thread 
{
    	private String name,pwd;
    	private Socket socket;
	HashSet<String> block;
    	private BufferedReader in;
    	private PrintWriter out;
	static JCheckBox[] jb;
	int ch,done=0;
	ChatServer cs;
	static HashSet<String> names = new HashSet<String>();
	private static HashMap<String,PrintWriter> writers = new HashMap<String,PrintWriter>();
	private static HashSet words = new HashSet();
	
	BufferedWriter pw;
	BufferedReader br;
	PrintWriter log,bw3;
    	public Handler(Socket socket,ChatServer cs) 
	{	
        	this.socket = socket;
		this.cs= cs;
		cs.frame.setVisible(true);
		/*Thread1 th1=new Thread1(socket);
		Thread t=new Thread(th1);
		t.start();*/
   	 }
    	public void run() 
	{
		try
		{
//Words
			File file4=new File("words.txt");
			FileReader fr=new FileReader(file4);
			br=new BufferedReader(fr);
			String n=null;
			while((n=br.readLine())!=null)
			{
				words.add(n);
			}
			//System.out.println(words);
//Chat History
			File file=new File("server.txt");
			FileWriter fw=new FileWriter(file.getName(),true);
			pw=new BufferedWriter(fw);
//Objectionable words
			File file3=new File("obj.txt");
			FileWriter fw3=new FileWriter(file3.getName(),true);
			bw3=new PrintWriter(fw3);
//Serverlog
			File file2=new File("serverlog.txt");
			FileWriter logfile=new FileWriter(file2.getName(),true);
			log=new PrintWriter(logfile);
			// Create character streams for the socket.
			in = new BufferedReader(new InputStreamReader(
            		socket.getInputStream()));
            		out = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("1");
			String action=in.readLine();
			while (true) 
			{
				if(action.equals("Signup"))
				{
					out.println("Signup");
					System.out.println("Signup");
					name=in.readLine();
					pwd=in.readLine();
					int sign=signup(name,pwd);
					if(sign==1)
					{
						out.println("Nosignup");
					}
					else if(sign==0)
					{
						out.println("Signupacc");
						action="Login";
					}
				}	
				if(action.equals("Login"))
				{
						System.out.println("LOGIN");
						int flag=0;
						System.out.println("7");
                    				out.println("NAMEANDPWD");
                    				name = in.readLine();
						pwd=in.readLine();
						System.out.println(name+pwd);
						ch=check(name,pwd);
						System.out.println(ch);
						if(ch==1)
						{
							out.println("Accepted");
							flag=1;
							if (name == null) 
							{
                       						return;
                 					}		
							synchronized (names) 
							{
                       						if (!names.contains(name)) 
								{	
                            						names.add(name);
									jb=cs.showActiveUsers();
									Toolkit.getDefaultToolkit().beep();
									String answer=new Date().toString();
									cs.messageArea.append(name+" connected at "+answer+"\n");
									System.out.println("HI");
									log.println(name+" connected at "+answer+"\n");
									//JOptionPane.showMessageDialog(cs.frame,name+" logged in");
									//System.out.println(name+" Logged in at "+ answer);
									break;
                       			 			}
								else
								{
									out.println("Not Accepted");
									out.println("2");
								}
              						}
						}
						else if(ch==0)
						{
							out.println("Not Accepted");	
							out.println("1");
							flag=0;
						}
					
				}
			}
			if(action.equals("Login"))
			{
				
			Thread1 th1=new Thread1(socket);
			Thread t=new Thread(th1);
			t.start();
			writers.put(name,out);
			while (true)
			{
				block = new HashSet<String>();
				try{
				
				Iterator<String> itr = names.iterator();
				String blo[]=new String[names.size()];
				for(int k=0;k<names.size();k++)
				{
					blo[k]=itr.next();
				}
				System.out.println(blo);
				for(int k=0;k<jb.length;k++)
				{
					if(!(jb[k].isSelected()))
					{
						System.out.println("Selected");
						block.add(blo[k]);
					}
					
				}
				}
				catch(Exception e){}
				System.out.println(block.size());


		
					int flag1=0;
					Set s1 = writers.entrySet();
                			Iterator i1 = s1.iterator();
                			while(i1.hasNext())
                			{
                        			Map.Entry e1 = (Map.Entry)i1.next();
						String ch1=(String)e1.getKey();
						if(!(block.contains(ch1)))
						{
							PrintWriter writer=(PrintWriter)e1.getValue();
							writer.println("ME"+names);
						}
                			}
					
				//}
				String input = in.readLine();
				int o=wordobj(input);
				if(o==1)
				{
					bw3.println("MESSAGE " +name + ": " + input+"         "+new Date().toString());
				}
				//System.out.println(input);
				if (input == null) 
				{
                  			return;
				}
				if(!(block.contains(name)))
				{
					System.out.println("Printed");
					pw.write(("MESSAGE " +name + ": " + input+"         "+new Date().toString())+"\n");
		
					
					int flag2=0;
					Set s2 = writers.entrySet();
                			Iterator i2 = s2.iterator();
                			while(i2.hasNext())
                			{
                        			Map.Entry e2 = (Map.Entry)i2.next();
						String ch2=(String)e2.getKey();
						if(!(block.contains(ch2)))
						{
                   					PrintWriter writer=(PrintWriter)e2.getValue();
							writer.println("MESSAGE " +name + ": " + input+"         "+new Date().toString());
						}
                			}
					
				}
                	}
			}
		}
		catch (IOException e)
		{
			System.out.println(e);
		} 
		finally 
		{
			if(names.contains(name))
			{
			if (name != null) 
			{
				Toolkit.getDefaultToolkit().beep();
				cs.messageArea.append(name+" disconnected at  "+new Date().toString()+"\n");
				log.println(name+" disconnected at  "+new Date().toString()+"\n");
				//System.out.println(name+" disconnected at..."+ new Date().toString());
				names.remove(name);
				/*for (PrintWriter writer : writers) 
				{
					writer.println("NULL" +names);
				}*/
				int flag3=0;
				Set s3 = writers.entrySet();
                		Iterator i3 = s3.iterator();
				while(i3.hasNext())
				{
					Map.Entry e3 = (Map.Entry)i3.next();
					String ch3=(String)e3.getKey();
					if(!(block.contains(ch3)))
					{
						PrintWriter writer=(PrintWriter)e3.getValue();
						writer.println("NULL"+names);
					}
				}
			}
			if (out != null) 
			{
				writers.remove(out);
			}
			}
		}
		try 
		{
			log.close();
			pw.close();
			br.close();
			socket.close();
			bw3.close();
		} 
		catch (IOException e) 
		{
		}
	}
	int wordobj(String input)
	{
		int flag=0;
		StringTokenizer st = new StringTokenizer(input ," ");
		while(st.hasMoreTokens())
		{
			if(words.contains(st.nextToken()))
			{
				return 1;
			}
		}
		return 0;
	}
	int check(String n, String p)
	{
		try
		{
			int flag=0;

			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); //STEP 1
			Connection c = DriverManager.getConnection("jdbc:odbc:mydsn2","system","javaprj");
			Statement s = c.createStatement();
			ResultSet result1 = s.executeQuery("select * from chat where username='"+n+"'and password='"+p+"'");
			System.out.println("In check");
			while(result1.next())
			{
	               		System.out.println("username : "+result1.getString(1));
		        	System.out.println("password : "+result1.getString(2));
				flag=1;
				return flag;
			}
			c.close();
			s.close();
			//result1.close();

			if(flag==0)
			{
				System.out.println("username and password doesnt exist");
				return flag;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();	
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return 0;
	}
	public int signup(String t1,String t2)
	{
		try
		{ 
			int flag=0;

			DriverManager.registerDriver(new sun.jdbc.odbc.JdbcOdbcDriver());
               		Connection c = DriverManager.getConnection("jdbc:Odbc:mydsn2");
			Statement s = c.createStatement();

			ResultSet result1 = s.executeQuery("select * from chat where username='"+t1+"'"); 
			while(result1.next())
			{
				System.out.println("username : "+result1.getString(1));		        
				System.out.println("password : "+result1.getString(2));
				flag=1;
			}

			if(flag==1)
			{
				System.out.println("username and password already exist");
				return 1;
			}

			if(flag==0)
			{
				int x = s.executeUpdate("insert into chat values('"+t1+"','"+t2+"')");
				ResultSet result2 = s.executeQuery("select * from chat");
				while(result2.next())
				{
						System.out.println("username : "+result2.getString(1));		
						System.out.println("password : "+result2.getString(2));
				}
			}

			c.close();
			s.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();	
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return 0;
	}
	
}