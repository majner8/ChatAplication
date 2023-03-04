package Server.User;




public class UserConnection extends DatabazovaKomunikaceUser{


	private HashMap<String,Connection> MapaAktivnichConnectionUzivatele=new HashMap<String,Connection>();

	
	public Connection getUlozeneConnection(String UUID) {
		return this.MapaAktivnichConnectionUzivatele.get(UUID);
	}
	
	/** Methoda pro pridani aktivniho Connection do listu uzivatele, navrati true, pokud je dany object 
	 * uzivatele aktivni, navrati false pokud se dany object uzavira, a je treba vytvorit novy
	 * @param UUID UUID */
	public boolean PridejConnection(String UUID,Connection con) {
	
		if(!this.ConnectionBudeVymazano(false)) {
			synchronized(this.MapaAktivnichConnectionUzivatele) {
				this.MapaAktivnichConnectionUzivatele.put(UUID, con);
			}
			if(this.ConnectionBudeVymazano) {
				synchronized(this.MapaAktivnichConnectionUzivatele) {
					this.MapaAktivnichConnectionUzivatele.clear();
					return false;
				}
			}
			return true;
			
		}
		return false;
		
	}
	
	
	//V pripade ze je hodnota true, UserConnection neprijima nove Connection, a vytvori se nove.
	private boolean ConnectionBudeVymazano=false;
	//Unikantni UUID Uzivatele
	private	final String UUIDUzivatele;
	
	
	
	@Override
	public String toString() {
	    // implementation
	}
	@Override
	public String toString() {
		return this.UUIDUzivatele;
	}
	
	
	
	/**Methoda pro ziskani/nastaveni promene, zdali se UserConnection ukoncuje
	 * @param UkoncitConnection Pro zastaveni prijimani novych Connection zadej true(pouze pro overeni zadej false)methoda zkontroluje
	 * zdali neni soucasti uzivatele jine connection
	 *Pokud je, Zastaveni prijimani connection nebude provedeno, a methoda navrati false,*/
	public synchronized boolean ConnectionBudeVymazano(boolean UkoncitConnection) {
		
		if(UkoncitConnection) {
			synchronized(this.MapaAktivnichConnectionUzivatele) {
				if(this.MapaAktivnichConnectionUzivatele.isEmpty()) {
					this.ConnectionBudeVymazano=true;
				}
			}
		}
		return this.ConnectionBudeVymazano;
	}

	
	
	
	
	/**Methoda pro zpracovani zpravy, dle zaslanych tasku 
	 * @param tasky socketem zaslaná zpráva, roztřízená na kriteria*/
	public void ZpracovaniTasku(SocketovaKomunikace task) {
	
	if(task.getPrvniTask()==MainEnumSocketComunication.ChatovaKomunikace) {
		
	}
	if(task.getPrvniTask()==MainEnumSocketComunication.ServerovaKomunikace) {
		
	}
		
		
	}
	
	

	

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserConnection) {
			return this.toString().equals(((UserConnection)obj).toString());
		}
		return false;
	}
		
}
