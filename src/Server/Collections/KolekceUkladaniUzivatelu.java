package Server.Collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Server.Chat.ChatConnection;
import Server.User.Connection;
import Server.User.UserConnection;

public abstract class KolekceUkladaniUzivatelu {

	//Mapa vsech Aktivnich uzivatelu
	private static Map<String,UserConnection> MapAktivnichUserConnections=Collections.synchronizedMap(new HashMap<String,UserConnection>());
	//mapa vsech aktivnich chatu
	private static Map<String,ChatConnection>MapAktivnichChatu=Collections.synchronizedMap(new HashMap<String,ChatConnection>());


	
	/**Methoda pro ziskani OBjectu uzivatele, dle jeho UUID  
	 * @param UUIDUzivatele UUID daneho uzivatele, pokud dany uzivatel neni aktivni, methoda vytvori noveho*/
 	public final static UserConnection getAktivnihoUzivatele(String UUIDUzivatele) {
		
		UserConnection uzivatel=KolekceUkladaniUzivatelu.MapAktivnichUserConnections.get(UUIDUzivatele);
			//Overeni zdali je uzivatel aktivni
		if(uzivatel==null) {
			//Uzivatel neni aktivni, methoda vytvori novy Object Uzivatele, ulozi ho do listu aktivnich uzivatelu, a navrati
			
			//Vytvoreni noveho Objectu
			UserConnection user=new UserConnection();
			
			//Probehne kontrola zdali nebyl uzivatel v mezicase vytvareni pridan do kolekce.
			synchronized(KolekceUkladaniUzivatelu.MapAktivnichUserConnections) {
				if(!KolekceUkladaniUzivatelu.MapAktivnichUserConnections.containsKey(UUIDUzivatele)) {
					KolekceUkladaniUzivatelu.MapAktivnichUserConnections.put(UUIDUzivatele, user);
					return user;
				}
				return KolekceUkladaniUzivatelu.MapAktivnichUserConnections.get(UUIDUzivatele);
			}
			
		}
		else {
			return uzivatel;
		}
		
	}

 	/**Methoda pro ziskani Objectu chatu dle jeho UUID
 	 * @param UUIDChatu UUID chatu, pokud chat neni aktivni, methoda vytvori novy chat, vlozi do kolekce a navrati jeho instanci */
 	public final static ChatConnection getAktivniChat(String UUIDChatu) {
 		ChatConnection chat=KolekceUkladaniUzivatelu.MapAktivnichChatu.get(UUIDChatu);
 		
 		if(chat==null) {
 			chat=new ChatConnection();
 			
 			synchronized(KolekceUkladaniUzivatelu.MapAktivnichChatu) {
 				ChatConnection x=KolekceUkladaniUzivatelu.MapAktivnichChatu.get(UUIDChatu);
 				if(x==null) {
 					KolekceUkladaniUzivatelu.MapAktivnichChatu.put(UUIDChatu, chat);
 					return chat;
 				}
 				return x;
 			}
 			
 		}
 		return chat;
 	}
	
 
 	
	
	
}
