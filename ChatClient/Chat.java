import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.event.ChangeEvent;
import javax.swing.text.DefaultCaret;
public class Chat
{
	BufferedReader in;
    	PrintWriter out;
	Socket socket;
	Date d1;
	int i=0,attemp=0;
	String s;
	JFrame frame=new JFrame("Welcome To Client");
	JTextField textField = new JTextField("Enter Your Message Here",50);
    	JTextArea messageArea = new JTextArea(8, 40);
	JTextArea message= new JTextArea(2,40);
	JFrame frame2=new JFrame("Welcome To Client");
	JPanel jp=new JPanel();
	//JLabel online=new JLabel("Online");
	public static void main(String[] args) throws Exception 
	{
        	Chat client = new Chat();
        	client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.setVisible(true);
        	client.running(client);
   	}
	public Chat()
	{
		textField.setEditable(false);
		MouseEventListener wel = new MouseEventListener(this);    
		textField.addMouseListener(wel);
		frame.getContentPane().add(textField, "South");
        	messageArea.setEditable(false);
		message.setEditable(false);
		message.append("Online:\n");
        	frame.getContentPane().add(new JScrollPane(messageArea), "Center");
		//frame.getContentPane().add(jp, "North");
		//jp.setSize(1000,100);
		//jp.add(online);
		frame.getContentPane().add(new JScrollPane(message), "North");
		frame.pack();      
		DefaultCaret caret = (DefaultCaret) messageArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        	textField.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String mess=textField.getText();
				if(mess.length()!=0)
				{
					out.println(mess);
					textField.setText("");
				}
			}
		});
	}
	private void running(Chat a) throws IOException 
	{
		i=0;
		frame2.getContentPane().add(new Test());
		frame2.setSize(300, 200);
		frame2.setVisible(true);
		try
		{
			File f=new File("ip.txt");
			if(f.exists())
			{
				FileReader fr=new FileReader(f);
				BufferedReader br=new BufferedReader(fr);
				socket=new Socket(br.readLine(),9001);
				br.close();
				System.out.println("File");
			}
			else
			{
				/*String ip=JOptionPane.showInputDialog(
            			frame,
            			"Enter IP Address of the Server:",
            			"IP Address Required!",
            			JOptionPane.QUESTION_MESSAGE);
				socket=new Socket(ip,9001);*/
				System.out.println("NO File");
				while(Test.ip==null)
				{
					System.out.print("");
				}
				FileWriter fw=new FileWriter(f);
				PrintWriter bw=new PrintWriter(fw);
				bw.println(Test.ip);
				bw.close();
				socket=new Socket(Test.ip,9001);
			}
		}
		catch(Exception h)
		{
		}
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	out = new PrintWriter(socket.getOutputStream(), true);
		while(Test.action==0)
		{
			System.out.print("");
		}
		System.out.println("2");
		if(Test.action==1)
		{
			out.println("Signup");
			System.out.println("Signup");
		}
		else if(Test.action==2)
		{
			out.println("Login");
			//System.out.println("Login");
			frame.setTitle(Test.un+" logged in...");
		}	

        	while (true) 
		{
			int x=0;
            		String line = in.readLine();
			if (line.startsWith("Nosignup")) 
			{
				JOptionPane.showMessageDialog(frame2,"Username already exists!!");	
				Test.un=JOptionPane.showInputDialog(
            			frame,
            			"Enter username:",
            			"Username",
            			JOptionPane.PLAIN_MESSAGE);
				Test.pd=JOptionPane.showInputDialog(
            			frame,
            			"Enter password:",
            			"Password",
            			JOptionPane.PLAIN_MESSAGE);
			}
				
			else if (line.startsWith("Signup")) 
			{
				out.println(Test.un);
				out.println(Test.pd);
				out.flush();
			}

				
			else if (line.startsWith("Signupacc")) 
			{
				JOptionPane.showMessageDialog(frame2,"Signup Done Succesfully! You will now be logged in");	
			}


           		else if (line.startsWith("NAMEANDPWD")) 
			{
				System.out.println("Enter");
				/*while(Test.un==null)
				{
					System.out.print("");
				}*/
				out.println(Test.un);
				out.println(Test.pd);
				out.flush();
			} 	

			else if (line.startsWith("Accepted")) 
			{
				textField.setEditable(true);
				frame2.dispose();
	           	}


			else if (line.startsWith("Alreadylog")) 
			{
				JOptionPane.showMessageDialog(frame2,"This username is already logged in from same/other machine!!");		
				socket.close();
				System.exit(0);
			}

			else if (line.startsWith("Not Accepted")) 
			{
				if(i!=3)
				{
				i++;
				line=in.readLine();
				if(line.equals("1"))
				{
				JOptionPane.showMessageDialog(frame2,"Wrong username or password: Attempt "+i);
				}
				else
				{
					JOptionPane.showMessageDialog(frame2,"User already logged in: Attempt "+i);	
				}
				Test.un=JOptionPane.showInputDialog(
            			frame,
            			"Enter username:",
            			"Username",
            			JOptionPane.PLAIN_MESSAGE);
				Test.pd=JOptionPane.showInputDialog(
            			frame,
            			"Enter password:",
            			"Password",
            			JOptionPane.PLAIN_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(frame2,"Wrong username or password, Application will now exit");
					System.exit(0);
				}
				//System.exit(0);
	           	}

				 
			else if (line.startsWith("MESSAGE")) 
			{	
				int j=0;
				Toolkit.getDefaultToolkit().beep();
				StringTokenizer st = new StringTokenizer(line , " :");
				int flag=0;
                			while(st.hasMoreTokens())
                			{        
						String s=st.nextToken();
						if(s.equals(Test.un))
						{
							System.out.println("Me");
							int i=9+Test.un.length();
							messageArea.append("Me: "+line.substring(i) + "\n");
							flag=1;
                        				break;
						}
					}
					
				if(flag==0)
				{
					messageArea.append(line.substring(8) + "\n");
				}
            		}
			else if(line.startsWith("ME"))
			{	
				JRadioButton jr[]=new JRadioButton [10];
				int i=0;
				message.setText("Online:\n");
				//message.append(line.substring(2)+",");
				
				StringTokenizer st = new StringTokenizer(line , ", [ ]");
				int flag=0;
				st.nextToken();
                		while(st.hasMoreTokens())
                		{        
					String s=st.nextToken();
					if(!(s.equals(Test.un)))
					{
						message.append(s+",");		
					}
					else
						message.append("Me, ");			
                		}
				
				//Trying private chattin--Checkbox
				/*StringTokenizer st1 = new StringTokenizer(line , ", [ ]");
				st1.nextToken();
                		while(st1.hasMoreTokens())
                		{        
					jr[i]=new JRadioButton(st1.nextToken());	
					jp.add(jr[i]);
					//message.append(st1.nextToken()+",");
					i++;
                		}*/

				//System.out.println("ME for Online users");
			}
			else if(line.startsWith("NULL"))
			{
				message.setText("Online:\n");
				//message.append(line.substring(4));
				StringTokenizer st = new StringTokenizer(line , ", [ ]");
				int flag=0;
				st.nextToken();
                		while(st.hasMoreTokens())
                		{        
					String s=st.nextToken();
					if(!(s.equals(Test.un)))
					{
						message.append(s+", ");		
					}
					else
						message.append("Me, ");			
                		}
				System.out.println("NUll for Online users");
			}
			else if(line.equals("Exit"))
			{
				System.exit(0);
			}
        }
    }
}
class MouseEventListener extends MouseAdapter
{
        Chat f;

        MouseEventListener(Chat f)
        {
                this.f = f;
        }

        public void mouseClicked(MouseEvent me)
        {
                f.textField.setText("");
        }
}