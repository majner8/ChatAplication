package Test;

public class Equals {

	public static void main(String []args) {
		String porovnat=	"/* */public     static class      ChatComunication {";
		//	String pat="public\\s+static\\s+class\\s+[A-z]*\\s+\\{";
		//String pa="/\\*.*\\*/s*public\\s+static\\s+class\\s+[A-z]*\\s+\\{";
		String pp=".[^x]*x.[^x|c]*";
		
		System.out.println("dxasdsajhjcs".matches(pp));
		
		String p="(?:\\s*/\\*.\\*/)*\\s*public\\s+static\\s+class\\s+[A-z]*\\s+\\{\\s*(?:/\\*.\\*/)*(?:/\\*|//)?";

		System.out.println(porovnat.matches(p));
	/*	String[] x=porovnat.replaceAll("\\s+", " ").split("\\s");

		for(String zz:x) {
				if(zz.matches("\\s")||zz.equals("")) {
					continue;
				}
			
				
				System.out.print(zz);
				System.out.print(" ");

			
			
		}
*/
	}
}
