package Exception;

public final abstract class TaskConstantException {

	
	
	
	public static class UnrecognizableEnumFieldException extends Exception{
		
		public UnrecognizableEnumFieldException (String message){
			super(message);
		}
		public UnrecognizableEnumFieldException (){
			
		}
		
		public void ahoj() {
			
		}
	}
	
	public static enum a{
		
	}
	
}
