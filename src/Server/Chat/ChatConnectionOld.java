package Server.Chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import Server.Main.Main;
import Server.User.ActiveUser;
import Server.User.UserConnection;
import TaskConstant.SocketComunication;

public class ChatConnectionOld {
	// 
	//kazdy chat connection ma list noveho objectu user-UUID kod hrace a odkaz na UserConnection.
	//V pripade ze je uzivatel neaktivni nastavy se hodnota UserConnection na null, zrusi se ze vsech aktivnich chatu, 
	//UUID stale zustava
	private final List<ActiveUser> UserUUID;
	
	
	public List<String> getMemberOfChat(){
		return this.UUIDHracuChatu;
	}
	//Vsechny zpravy ke zpracovani jsou 
	private LinkedList<SocketComunication> poradnikZpravKUlozeniDoDatabaze=new LinkedList<SocketComunication>();
	// Vlakno Obsluhujici Ukladani Zprav do Databaze
	private final PomocneTridy.Thread VlaknoProUkladaniDodatabaze;
	
	
	
	/** Trida, ukladajici tasky do databaze z poradniku
	 * Trida periodicky cte data z poradniku, v pripade ze je poradnik prazdny vlakno prejde do wainting modu
	 * a pred zahajenim dalsiho ukladani musi byt probuzeno*/
	private class UkladaniTaskuDoDatabaze extends  PomocneTridy.Thread{

		private final Database database;
		@Override
		public boolean VykonejCinnost() {
			// TODO Auto-generated method stub
			boolean navrat=false;
			SocketovaKomunikace task;
			do { 
				synchronized(poradnikZpravKUlozeniDoDatabaze) {
					task=poradnikZpravKUlozeniDoDatabaze.getFirst();
					poradnikZpravKUlozeniDoDatabaze.removeFirst();
				}
				
				if(task!=null) {
					this.ZpracujTask(task);
					navrat=true;
				}
			
				
			}while(task!=null);
			return navrat;
		}
		
		//Methoda ktera zpracuje task dle enum konstant a ulozi message do dane databaze
		private void ZpracujTask(SocketovaKomunikace task) {
			try {
				if(task.getNextChatTask()==ChatovaKomunikace.PosilamZpravu) {
					this.database.UlozMessage(task);
					
				}
				
				
			
			}
			
			catch(Exception e) {
				Main.WriteLogConsole("Chyba v ServerChatComunication.ChatConnection.UkladaniTaskuDodatabaze.ZpracujTask"+e);
			}
		}
		//Trida zajistujici ukladani zprav do databaze
			private class Database extends DatabazovaKomunikaceChat{
				private final String UUIDChatu;
				
				public Database(String UUIDChatu) {
					this.UUIDChatu=UUIDChatu;
				}
				/** Methoda ulozi message do databaze chatu a zaroven aktualizuje databaze prijatych 
				 * zprav hracu
				 * */
				public void UlozMessage(SocketovaKomunikace task) {
					
				}
			}
		
		
	}
		
	// promena Vyjadrujici zdali je tento Object Smazan
	private boolean ChatConnectionBudeVymazano=false;
	
	
	private boolean PridejDoPoradniku(SocketovaKomunikace socCom,boolean vymazatVlakno) {
		if(this.ChatConnectionBudeVymazano) {
			return false;
		}
		synchronized(this.poradnikZpravKUlozeniDoDatabaze) {
			this.poradnikZpravKUlozeniDoDatabaze.addLast(socCom);
		}
		this.VlaknoProUkladaniDodatabaze.notify();
		
		return true;
		
		
	}

	
	
	
	
	/** Methoda pro rozeslani zpravy uzivatelum Chatu, navrati int vyjadrujici poradi zpravy v chatu*/
	public int ZpracujMessage(SocketovaKomunikace socCom) {
		
	}
	
	
	public void ZpracujMessage(SocketovaKomunikace task) {
		
		
	}
	
}
