package Server.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;

import Enum.ClassForSocketConnection;
import Enum.SocketComunicationInput;
import Enum.SocketComunicationInputFirtTask;
import Enum.SocketComunicationOutPut;
import Enum.SocketComunicationOutPutFirtTask;
import Server.Main;
import Server.Enum.NasvPara;
import Server.Enum.SocketParametr;
import Server.ZpracovaniTasku.UserConnection;

/** Trida zajistujici komunikaci s danym zarizenim*/
public class Connection {
	private final UserConnection Uzivatel;
	private final String Certifikat;

	/** UserConnection, Object daneho uzivatele*/
	public Connections (UserConnection Uzivatel,String Certifikat) {
		this.Uzivatel=Uzivatel;
		this.Certifikat=Certifikat;
		
		
	}
	
	/** Vytvori Spojeni a nastavy dany socket, podle typu socketu Fast/Slow, 
	 * V pripade ze je jiz socket vytvoren a jedna se obnovu spojeni, overi zdali bylo spojeni 
	 * preruseno, pokud ano uzavre stavajici socket v pameti, a nahradi ho novym spojenim
	 * pokud zadna z podminek nevyhovuje Socketu je odeslano ze spojeni jiz existuje.*/
	public void VytvorSpojeni(Socket socket,ClassForSocketConnection con) {
		ClassForSocketConnection odpoved=null;
		try {
		SocketComunicationInput task=con.getNextInput();
		if(task==SocketComunicationInput.CreateSocketFast) {
			if(this.Fast==null) {
				this.Fast=socket;
				this.outFast=new PrintWriter(socket.getOutputStream(),true);
				this.inputFast=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				odpoved=ClassForSocketConnection.getOutputClassForSocketConnection(SocketComunicationOutPutFirtTask.SpojeniVytvoreno, null, null, this.Certifikat,null);
				PoslechPortu.SendSocket(socket.getOutputStream(), con);
				new ListenSocket(SocketParametr.Fast,SpojeniFastZtraceno);
				return;
			}
			if(this.SpojeniFastZtraceno||this.Fast.isClosed()) {
				if(!this.Fast.isClosed()) {
					this.CloseSocket(outFast, inputFast, Fast);
				}
				this.Fast=socket;
				this.outFast=new PrintWriter(socket.getOutputStream(),true);
				this.inputFast=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				odpoved=ClassForSocketConnection.getOutputClassForSocketConnection(SocketComunicationOutPutFirtTask.SpojeniObnoveno, null, null, this.Certifikat,null);
				PoslechPortu.SendSocket(socket.getOutputStream(), con);
				new ListenSocket(SocketParametr.Fast,SpojeniFastZtraceno);
				return;
			}
			
		}
		if(task==SocketComunicationInput.CreateSocketSlow) {
			if(this.Slow==null) {
				this.Slow=socket;
				this.outSlow=new PrintWriter(socket.getOutputStream(),true);
				this.inputSlow=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				odpoved=ClassForSocketConnection.getOutputClassForSocketConnection(SocketComunicationOutPutFirtTask.SpojeniVytvoreno, null, null, this.Certifikat,null);
				PoslechPortu.SendSocket(socket.getOutputStream(), con);
				new ListenSocket(SocketParametr.Slow,SpojeniSlowZtraceno);

				return;
			}
			if(this.SpojeniSlowZtraceno||this.Slow.isClosed()) {
				if(!this.Slow.isClosed()) {
					this.CloseSocket(outSlow, inputSlow, Slow);
				}
				this.Slow=socket;
				this.outSlow=new PrintWriter(socket.getOutputStream(),true);
				this.inputSlow=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				odpoved=ClassForSocketConnection.getOutputClassForSocketConnection(SocketComunicationOutPutFirtTask.SpojeniObnoveno, null, null, this.Certifikat,null);
				PoslechPortu.SendSocket(socket.getOutputStream(), con);
				new ListenSocket(SocketParametr.Slow,SpojeniSlowZtraceno);
				return;
			}

		}
		odpoved=ClassForSocketConnection.getOutputClassForSocketConnection(SocketComunicationOutPutFirtTask.SpojeniJizExistuje, null, null, this.Certifikat,null);
		PoslechPortu.SendSocket(socket.getOutputStream(), con);
		}
		catch(Exception e) {}
	}
	
	private void CloseSocket(PrintWriter outStream,BufferedReader inputStream,Socket socket) {
		try {
		
			outStream.close();
			inputStream.close();
		}
		catch(Exception e) {}
		finally {
			try {
			socket.close();
			}
			catch(Exception e) {}
		}
		
	}
	
	private boolean SpojeniFastZtraceno=false;
	private boolean SpojeniSlowZtraceno=false;
	private boolean SpojeniZruseno=false;
	
	private Socket Fast,Slow=null;
	private PrintWriter outFast,outSlow;
	private BufferedReader inputFast,inputSlow;

	public String getCertifikat() {
		return this.Certifikat;
	}
	
	public void PosliTask(ClassForSocketConnection con) {
		
		//Podle kriterii urci kterej socket se bude pouzivat
		
		synchronized(this.poradiTaskuFastSocketu) {
			this.poradiTaskuFastSocketu.add(con);
		}
		
		synchronized(this.poradiTaskuSlowSocketu) {
			this.poradiTaskuSlowSocketu.add(con);
		}
		
	}
	
	
	private LinkedList<ClassForSocketConnection> poradiTaskuFastSocketu=new LinkedList<ClassForSocketConnection>();
	private LinkedList<ClassForSocketConnection> poradiTaskuSlowSocketu=new LinkedList<ClassForSocketConnection>();

	

	
	
	/**Trida urcena k vzajemnemu prenosu dat. Data urcena k odeslani prebira ze zasobniku.
	 * Prijmuta data pridava do cekajiciho zasobniku na zpracovani */
	class ListenSocket { 
		private static final SocketComunicationInputFirtTask [] seznamVyjimekBezOdpovediDoruceni=null;
		private BufferedReader input;
		private PrintWriter out;
		private Socket socket;
		private  boolean SpojeniZtraceno;
		private  LinkedList<ClassForSocketConnection> tasky;	
		private HashMap<String,ClassForSocketConnection> NedoruceneZpravy;
		

		private void OverSpojeni() {
		
		}
		
		/**Navrati prvni cekaji task k odeslani, zaroven task vlozi do fronty Nedorucenich tasku */
		public ClassForSocketConnection getFirstTask() {
			ClassForSocketConnection x;
			synchronized(tasky) {
				x=tasky.getFirst();

			}
			
			synchronized(NedoruceneZpravy) {
				this.NedoruceneZpravy.put(x.getUNIQUEKeyZpravy(), x);
			}
			return x;
		}
		
		private  boolean jeTaskDorucen(String UUID) {
			
			synchronized(this.NedoruceneZpravy) {
				return !this.NedoruceneZpravy.containsKey(UUID);
			}
		} 
		
		
		public ListenSocket(SocketParametr par,boolean SpojeniZtraceno) {
			this.SpojeniZtraceno=SpojeniZtraceno;
			if(par==SocketParametr.Fast) {
				this.input=inputFast;
				this.out=outFast;
			}
			if(par==SocketParametr.Slow) {
				this.input=inputSlow;
				this.out=outSlow;
			}
	
		}


		

		private class ListenInput extends Thread {
			
			
			@Override
			public void run() {
				while(!Main.isServerStopped()&&!socket.isClosed()) {
					try {
						try {
							if(!input.ready()) {
								Thread.sleep(10);
								continue;
							}
							SpojeniZtraceno=false;
						}
						catch(Exception e) {}
					
					this.readerMessage();
					}
					
					
					catch(Exception e) {
						
					}
				}	
		}
			
			private void readerMessage()throws Exception {
				
				String Readmessage =input.readLine();
				ClassForSocketConnection cls=null;
				if((cls=ClassForSocketConnection.getParametryInputTasku(Readmessage, Certifikat))==null) {
					//lze pridelat ochranu, v pripade ze by server zasilal pouze message bez certifikatu, a prilis casto
					return;
				};
				//pokud zprava neobsahuje UNIQUEKey navrati null
				if(cls.getUNIQUEKeyZpravy()==null) {
					return;
				}
				//prijmuty socket obsahuje potvrzeni o prijeti zpravy
				if(cls.getFirstInputTask()==SocketComunicationInputFirtTask.SocketDorucen) {
					synchronized(NedoruceneZpravy) {
						NedoruceneZpravy.remove(cls.getUNIQUEKeyZpravy());
					}
					return;
				}
				//odesla zpetnou vazbu clientovy o doruceni zpravy
				if(!this.TaskyBezOdeslaniVazbyDoruceno(cls.getFirstInputTask())) {
	
					ClassForSocketConnection e=ClassForSocketConnection.getOutputClassForSocketConnection(SocketComunicationOutPutFirtTask.ZpravaDorucena, null, null, cls.getVerifyCertification(), getCertifikat());
					synchronized(tasky) {
						tasky.add(e);
					}
				}
			
				
			}
			
			private boolean TaskyBezOdeslaniVazbyDoruceno(SocketComunicationInputFirtTask task) 
			{
				if(seznamVyjimekBezOdpovediDoruceni==null) {return false;}
				for(SocketComunicationInputFirtTask t:seznamVyjimekBezOdpovediDoruceni) {
					if(t==task) {
						return true;
					}
					continue;
				}
				
				return false;
			}
		}
		
		
		private class ListenOutPut extends Thread{			


			
			@Override
			public void run() {
				while(!Main.isServerStopped()&&SpojeniZtraceno) {
					if(!socket.isConnected()) {
						try {
							Thread.sleep(10);
							continue;
						}
						catch(Exception e) {};
					}
					else {
						try {
						this.WriteTask();
					
						}
						catch(Exception e) {
							Main.WriteLogConsole("Chyba v ListenOutput  "+e);
						}
					}
				}
			}
			
			
			
			private void WriteTask() throws Exception{
				ClassForSocketConnection con=getFirstTask();
				out.write(con.getSocketTaskMessage());
				
				
				// Timer pro kontrolu zdali byl task dorucen, pokud tak neni zavola metodu overSpojeni
				class Timer extends Thread{
					private final String UUID;
					public Timer(String UUID) {
						this.UUID=UUID;
						super.start();
					}
					@Override
					public void run() {
					
						try {
							Thread.sleep(NasvPara.Ping.getValue());
							
							if(!jeTaskDorucen(UUID)) {
								OverSpojeni();
							}
						}
						catch(Exception e) {
							
						}
						
					}
					
					
					
				}
			
				new Timer(con.getUNIQUEKeyZpravy());
			}
			
			
		}
		
	}
	
	
	
	
	
}




