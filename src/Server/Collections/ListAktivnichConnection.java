package Server.Collections;

package Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Server.Enum.User;
import Server.Listener.Connections;
import Server.ZpracovaniTasku.UserConnection;

public abstract class ListAktivnichConnection {

	//String certifikat
	private static Map  <String,Connections > ListSpojeni=Collections.synchronizedMap( new HashMap<>());
	//string toString ofUser
	private static Map<String ,UserConnection> ListPripojenychUzivatelu=Collections.synchronizedMap( new HashMap<>());

	
	 public static void addConnection(String certifikat, User user) {
			UserConnection uzivatel;
		 uzivatel=ListPripojenychUzivatelu.get(user.toString());
		 if(uzivatel==null) {
			 uzivatel=new UserConnection();
			 ListPripojenychUzivatelu.put(uzivatel.toString(), uzivatel);
		 }
		uzivatel.PridejZarizeni(certifikat, user, uzivatel);
		
		
	}
	
	public static Connections getConnections(String certifikat) {
		
		synchronized(ListSpojeni) {
			return ListSpojeni.get(certifikat);
		}
	}
	
	protected static void removeConnections(String certifikat) {
		synchronized(ListSpojeni) {
			ListSpojeni.remove(certifikat);
		}
	}
	
	/**Prohleda ArrayListAktivnich Uzivatelu, pokud uzivatel neni aktivni navrati null*/
	public UserConnection getAktivnihoUzivatele(String USRIID) {
	
		return	this.ListPripojenychUzivatelu.get(USRIID);	
	
	}
	
	public static void SpojeniPreruseno(String certifikat)
	{
		
	}
	private List  <Connections > ListPripojenychZarizeni=Collections.synchronizedList(new ArrayList<>());
	/***/
	public void PridejZarizeni(String certifikat,User user,UserConnection UserCon) {
		Connections con=new Connections(UserCon,certifikat);
			this.ListPripojenychZarizeni.add(con);
			this.ListSpojeni.put(certifikat, con);
		
	}

	protected List<Connections> getPripojeneZarizeni() {
		return this.ListPripojenychZarizeni;
	}
	
	class Timer extends Thread{
		private final float Timeout;
		private final int KontrolaObnoveni=10;
		private boolean SpojeniObnoveńo=false;
		private final String Certifikat;
		public Timer(float Timeout,String certifikat) {
			this.Certifikat=certifikat;
			this.Timeout=Timeout;
			super.start();
			
		}
		
		public synchronized boolean getSpojeniObnoveno() {
			return this.SpojeniObnoveńo;
		}
		
		public synchronized void SpojeniObnoveno() {
			this.SpojeniObnoveńo=true;
			
		}
		
		@Override
		public void run() {
			try {
				int Interval=(int)Math.ceil(Timeout/this.KontrolaObnoveni);
				int x=0;
				
				while(x<=Interval&&!this.getSpojeniObnoveno()) {
					Thread.sleep(this.KontrolaObnoveni);
				}
				if(this.getSpojeniObnoveno()) {
					return;
				}
				
				removeConnections(this.Certifikat);
				
			}
			catch(Exception e) {
				
			}
			
		}
	}
}
