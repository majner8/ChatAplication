package Test;

public class TestOveride {

	public static void main(String[] args) {
		ahoj x=new ahoj("x");
		ahoj y=new ahoj("x");
		System.out.println(x.equals(y));
	}
	
	
}


class ahoj{
	String uuid;
	
	public ahoj(String x) {
		this.uuid=x;
	}
	@Override
	public boolean equals(Object x) {
		return(this.uuid.equals(((ahoj)x).uuid));

	}
}
