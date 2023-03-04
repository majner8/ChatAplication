package Server.Prihlasovani;

import java.net.Socket;
import java.util.HashMap;

import Constanty.ClassForSocketConnection;
import Constanty.Parametry;
import Constanty.SocketComunicationFirstTask;
import Server.Main.Main;
import Server.Main.PoslechPortu;


public class Loggin extends Thread{

	
	public static String generateCertifikat() {
		return "xxxx";
	}
	
	
	
	

	
	public Loggin(Socket socket,Parametry parametr) {
		this.socket=socket;
		super.start();
			
	
	}
	
	
	
	@Override

	public void run() {
		try {
			 boolean ZaslalanaZpravaOPrihlaseni=false;
			 while(!Main.isServerStopped()&&!this.isUserLoggin()) {
				 Connections con=null;
				 String inputMessage=PoslechPortu.AcceptSocket(socket);
				 ClassForSocketConnection clsForSock=null;
				 if((clsForSock= this.ZpracovaniMessage(inputMessage))==null) {
					 return;
				 }
				 
			
					if(clsForSock.getVerifyCertification()!=null) {
						if((con=ListPrihlaseniUzivatelu.getConnections(clsForSock.getVerifyCertification()))==null){
							PoslechPortu.SendSocket(socket.getOutputStream(), ClassForSocketConnection.getOutputClassForSocketConnection(SocketComunicationOutPutFirtTask.CertifikatNenalezen, null, null, null));
							return;
						}
						con.ZpracujMessage(clsForSock, socket);
						return;
					}
					if(!ZaslalanaZpravaOPrihlaseni) {
						Main.WriteLogConsole(String.format("%s is trying to loggin",socket.getRemoteSocketAddress().toString()));
						ZaslalanaZpravaOPrihlaseni=true;
					}
					this.LogginUser(clsForSock);
					
				
					
			 }
			 
			 
			 


		
		}
		//
		
		catch(java.net.SocketException e) {

			PoslechPortu.closeSocket(socket,SocketUzavrenej);
			SocketUzavrenej=true;

		}
		catch(Exception e) {
			Main.WriteLogConsole("Chyba v Connect socket:  "+e.toString());
		}
		if(this.isUserLoggin()) {
			SocketUzavrenej=true;
			Main.WriteLogConsole(String.format("%s uspesne prihlasil, user %s",socket.getRemoteSocketAddress().toString(),this.User.getEmailAdress()));

		}
		PoslechPortu.closeSocket(socket,SocketUzavrenej);
		SocketUzavrenej=true;
	
	
	}
	
	
	private ClassForSocketConnection ZpracovaniMessage(String message) throws Exception {
		
		if(Main.isServerStopped()) {
			return null;
		}
		ClassForSocketConnection clsForSock=null;
		if((clsForSock=ClassForSocketConnection.getParametryTasku(message, null))==null) {
			PoslechPortu.closeSocket(socket, SocketUzavrenej);
			SocketUzavrenej=true;
			return null;
		}		
		return clsForSock;
	}
	
	
	
	private void LogginUser(ClassForSocketConnection tasks) throws Exception{
		boolean ZaslatOdpovedNaSocket=false;
		String OutPutMessage=null;
		if(tasks.getFirstTask()==SocketComunicationFirstTask.Loggin&&!isKnownUser()) {
			ZaslatOdpovedNaSocket=true;
		}
		
		else if(tasks.getFirstTask()==SocketComunicationFirstTask.LogginUserName&&!isKnownUser()) {
			ZaslatOdpovedNaSocket=setUser(tasks.getMessage()); 
		}
		else if(tasks.getFirstTask()==SocketComunicationFirstTask.LogginPassword&&isKnownUser()) {

			if(ZaslatOdpovedNaSocket=getUser().PrihlasSe(tasks.getMessage())==true) {
				OutPutMessage=this.generateCertifikat();
				getUser().setPrihlasenej(true);
				ListPrihlaseniUzivatelu.addConnection(OutPutMessage, new Connections());
			}
		}
		else {
			return;
		}
		if(ZaslatOdpovedNaSocket) {
			PoslechPortu.SendSocket(socket.getOutputStream(), ClassForSocketConnection.getOutputClassForSocketConnection(SocketComunicationOutPutFirtTask.SpravneZadaneUdaje, null, OutPutMessage, null));

		}
		else {
			PoslechPortu.SendSocket(socket.getOutputStream(), ClassForSocketConnection.getOutputClassForSocketConnection(SocketComunicationOutPutFirtTask.SpatneZadaneUdaje, null, OutPutMessage, null));


		}
		
	}
	


	
	private User User;
	
	private boolean SocketUzavrenej=false;
	

	
	private final Socket socket;
	
	
	public User getUser() {
		return this.User;
	}
	private boolean setUser(String PrihlasovaciNick) {
		
		
		User usr=null;
		if((usr=User.setPrihlasovaciNick(PrihlasovaciNick))!=null) {
			this.User=usr;
			return true;
		}
		return false;
	}
	
	private boolean isKnownUser() {
		return getUser()!=null;
	}
	
	private boolean isUserLoggin() {
		if(this.isKnownUser()) {
			return this.getUser().isPrihlasenej();
		}
		return false;
	}
	
}
	
	
