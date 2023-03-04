package Server.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class PoslechPortu extends Thread {
	
	private final int portNumber;
	

	//Metoda posle zprávu socketu zadaném v Argumentu.
	public static void SendSocket(OutputStream socketOutputWriter,Constanty.ClassForSocketConnection con) throws IOException{		
		
		new PrintWriter(socketOutputWriter,true).println(con.getMessage());
		
		
	}
	
	public static void  closeSocket(Socket socket,boolean UzavrenoServerem) {
		try {
			if(!UzavrenoServerem) {
				
				socket.close();
				Main.WriteLogConsole(String.format("%s was loosest",socket.getInetAddress()));

			}
			
			

		} catch (IOException e1) {
			// TODO Auto-generated catch block

			Main.WriteLogConsole("Chyba v connection, nelze zavrit socket po vyprseni TImeout" +e1.toString());
		}
	}
	
	
	

	public static String AcceptSocket(Socket socket) throws Exception  {
		if(socket.getSoTimeout()==0) {
			socket.setSoTimeout(Constanty.Parametry.SocketTimeOut.getValue());
		}
		
		BufferedReader Reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));

		 int WaintingCicle=0;
		 while(!Main.isServerStopped()) {
				
			
			 try {
				String mes= Reader.readLine();
				return mes;
			 }
			 catch(java.net.SocketTimeoutException e) {
				 if(WaintingCicle<Constanty.Parametry.WaintingCicle.getValue()) {
					 WaintingCicle++;
				 }
				 else {
					 throw e;
				 }
			 }
		 
		 }
			return null;
	}
	
	public PoslechPortu(int port) {
		this.portNumber=port;
		super.start();
	}
	
	@Override
	public void run() {
		this.startListen();
	}
	public void startListen() {
		
		ServerSocket servsocket=null;
		try {
			
			servsocket=new ServerSocket(this.portNumber);
			
			while(!Main.isServerStopped()) {
				Socket pripojeni=null;
				try {
				servsocket.setSoTimeout(1000);
				this.VerifyAdres((pripojeni=servsocket.accept()).getInetAddress());
				
				
				new Loggin(pripojeni,this.parametr);
				}
				catch(java.net.SocketTimeoutException e) {
					if(e.getMessage().equals("Accept timed out"));
					else {
						throw e;
					}
				}
			}
			
			servsocket.close();
			
			
		}

		catch(Exception e) {
			Main.WriteLogConsole("Chyba v PoslechPortu, Start Listen:  "+e.toString());
		}
	}


	private boolean VerifyAdres(InetAddress adres) {
	
		return true;
	}
	
		

}

