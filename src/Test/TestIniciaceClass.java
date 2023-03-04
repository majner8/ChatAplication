package Test;

public class TestIniciaceClass {

	public static void main(String[] args) {
		System.out.println("A");
		new TestIniciaceClass().test();
		System.out.println("B");
		new testInit().gethodnota();
		System.out.println("C");
	}
	
	public void test() {
		testInit x=new testInit();
		x.gethodnota();
		x.setValue(7);
		x=null;
		
	}
}


 class testInit {
	

	private volatile static hodnota value=new hodnota(4);
	
	public testInit() {
		System.out.println("I am writing from testInit");
	}
	
	public void gethodnota() {
		System.out.println(value.getValue());
	}
	public void setValue(int x) {
		this.value.setInt(x);
	}
	
}

class hodnota{
	
	private int value;
	public hodnota(int value) {
		System.out.println("I am writing from hodnota class");
		this.value=value;
	}
	
	public void setInt(int hodnota) {
		this.value=hodnota;
	}
	
	public int getValue() {
		return this.value;
	}
	
}

