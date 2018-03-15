import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
public class Test extends JPanel implements ActionListener
{
	JTextField u_name=new JTextField(10);
	JPasswordField pwd=new JPasswordField(10);
	JTextField u_namess=new JTextField(10);
	JPasswordField pwdss=new JPasswordField(10);
	JTextField ipss=new JTextField(10);
	JLabel username=new JLabel("Enter Username: ");
	JLabel password=new JLabel("Enter Password: ");
	JLabel ipadd=new JLabel("Enter IP Address: ");
	JLabel username1=new JLabel("Enter Username: ");
	JLabel password1=new JLabel("Enter Password: ");
	static String un,pd,ip;
	static int action;
	static int x=1;
	JButton jb,jb2;
	JTabbedPane tabbedPane; 
	JPanel createPane(String s)
       	{
		JPanel p = new JPanel();
		jb=new JButton("Login");
		jb2=new JButton("Signup");
		//MouseEventListener mel = new MouseEventListener(this);
		//p.addMouseListener(mel);
		if(s.equals("Login"))
		{
			JLabel l1=new JLabel("Login\n");
			//u_name.setSize(100,50);
			//pwd.setSize(100,50);
	        	//p.add(l1);
			p.add(username1);
			p.add(u_name);
			p.add(password1);
			p.add(pwd);
			p.add(jb);
			jb.addActionListener(this);
		}
		if(s.equals("Sign-up"))
		{
			//u_namess.setSize(100,50);
			//pwdss.setSize(100,50);
			//ipss.setSize(100,50);
                	//p.add(new JLabel("Sign-up"));
			p.add(username);
			p.add(u_namess);
			p.add(password);
			p.add(pwdss);
			p.add(ipadd);
			p.add(ipss);
			p.add(jb2);
			jb2.addActionListener(this);
		}
		return p;
       	}
	public Test()
	{
		setLayout(new BorderLayout());
               UIManager.put("TabbedPane.selected", Color.green);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Login", createPane("Login"));
		tabbedPane.addTab("Sign-up", createPane("Sign-up"));
		tabbedPane.setSelectedIndex(0);
                add(tabbedPane,BorderLayout.CENTER);
	}
		 public void actionPerformed(ActionEvent ae)
        {
				un=u_name.getText();
				pd=pwd.getText();
				//action=2;
				System.out.println("Login");
		if(ae.getSource()==jb2)
		{
			un=u_namess.getText();
			pd=pwdss.getText();
			ip=ipss.getText();
			try
			{
			File f=new File("C:\\Users\\shada_000\\Desktop\\Aptech Presentation\\Socket-Develop\\ChatClient\\ip.txt");
			FileWriter fw=new FileWriter(f);
			PrintWriter pw=new PrintWriter(fw);
			System.out.println(ip);
			pw.println(ip);
			pw.close();
			}
			catch(Exception e){}
			action=1;
			System.out.println("Signup");
		}
		else
			action=2;
        }
}

















/*public static void main(String ... ss)
	{
		JFrame frame=new JFrame("Welcome To Client");	
		 frame.addWindowListener(new WindowAdapter()
                                        {
                                                public void windowClosing(WindowEvent we)
                                                {
                                                        System.exit(0);
                                                }
                                        });
       frame.getContentPane().add(new Test());
                frame.setSize(200, 100);

                //frame.pack();
                frame.setVisible(true);
        }*/
