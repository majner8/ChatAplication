package Test;

public class Interface implements xx{

	
	public static void main(String[] args) {
		xx x=new InterfaceA(
			
		) {

			@Override
			public void x() {
				// TODO Auto-generated method stub
				System.out.println("ahoj");
			}
			
		};
		
		x.x();
		x=new InterfaceB(
				
				) {

					@Override
					public void x() {
						// TODO Auto-generated method stub
						System.out.println("BEE");
					}
					
				};
				
				x.x();
		
	}

	@Override
	public void x() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}

 interface xx{
	void x();
}

 interface InterfaceA extends xx {

	@Override
	void x();
}
 interface InterfaceB extends xx {
	@Override
	void x();
}
