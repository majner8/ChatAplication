package Constanty;
/**Seznam parametrů a stálých proměnných**/
public enum Parametry {

	
	RozdelovaciZnamenko("/"),RozdelovaciZnamenkoVParametru(" "),
	SocketTimeOut("500"),WaintingCicle("4"),Ping("");

	



	private String hodnota;
	
	 Parametry(String hodnota) {
		this.hodnota=hodnota;
		
	}
	
	 public String getRozdelovac() {
		 return this.hodnota;
	 }
	 public int getValue() {
		 return Integer.parseInt(hodnota);
	 }
	
}
