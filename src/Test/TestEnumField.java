package Test;

public class TestEnumField {

	
	public static void main(String[]args) {
		A[] x= {A.Ahoj,A.B,A.C};
		
		for(Enum A:x) {
			System.out.println(A.toString());
		}
		for(Object y:x) {
			System.out.println(y.toString());
		}
		
	}
	
	enum A{
		Ahoj,B,C;
		/*	
		@Override
		public String toString() {
			System.out.println("ahoj");
			return this.name();
		} */
	}
	
	enum B{
		Ahoj,B,C
	}
	
	enum c{
		Ahoj,B,C
	}
}
