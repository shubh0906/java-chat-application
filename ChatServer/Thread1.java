import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Date;
import java.awt.*;
import java.io.*;
import javax.swing.*;
public class Thread1 implements Runnable
{
		Socket socket;
		Thread1(Socket socket)
		{
			this.socket=socket;
		}
		public void run()
		{
		try
		{
			FileInputStream fstream=new FileInputStream("server.txt");
 		  	BufferedReader br=new BufferedReader(new InputStreamReader(fstream));
   		  	PrintWriter p = new PrintWriter(socket.getOutputStream(),true);	
			String l=null;
			while((l=br.readLine())!=null)
			{
				p.println(l);	  
			}
			br.close();
			fstream.close();
		}
		catch(Exception e){}
	}
}