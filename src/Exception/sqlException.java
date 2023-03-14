package Exception;

public class sqlException extends Exception{

	public sqlException(String Message) {
		super(Message);
	}

	public sqlException(EnumSQLException ExceptionType,String message) {
		super(ExceptionType.getMessage()+message);
	}
	public sqlException(EnumSQLException ExceptionType) {
		super(ExceptionType.getMessage());
	}
	public static enum EnumSQLException{
		
		UUIDPlayerToLong("UUID code of User have to be 20 character Long. Here receive UUID:"),
		GroupChatDoesNotExist(),
		databaseServerIsUnavaible;
		
		private String message;
		
		EnumSQLException(String message){
			this.message=message;
		}
		
		public String getMessage() {
			return this.message;
		}
	}
}
