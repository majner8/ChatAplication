package Test;

public class TestSplitString {

	public static void main(String [] args) {
		String message="A/B/C";
		String [] x=message.split("/",1);
		System.out.println(x[0]);
		System.out.println(x[1]);
		System.out.println(x[2]);
		System.out.println(x[3]);
	}
}
