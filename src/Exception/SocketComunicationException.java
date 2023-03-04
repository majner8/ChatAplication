package Exception;

public class SocketComunicationException extends Exception{

	public SocketComunicationException(String message){
		super(message);
	}
	
	
	public static class IncorectFormatTaskSocketComunicationException extends SocketComunicationException{

		public IncorectFormatTaskSocketComunicationException(String message) {
			super(String.format("Type IncorectFormatTask. %s ", message));		}
		
	}
	
	public static class IncorectCertifikateSocketComunicationException extends SocketComunicationException{

		public IncorectCertifikateSocketComunicationException(String message) {
			super(String.format("Type IncorectCertifikate. %s ", message));
			// TODO Auto-generated constructor stub
		}
		
	}

	public static class CertificatIsNotSameException extends SocketComunicationException{

		public CertificatIsNotSameException(String message) {
			super(String.format("Type IncorectCertifikate. %s ", message));
			// TODO Auto-generated constructor stub
		}
		
	}
	
	public static class IncorrectFormatSocketComunicationException extends RuntimeException{
		
		public IncorrectFormatSocketComunicationException(String message) {
			super(message);
		}
	}
	
	public static class EnumSyntaxeError extends RuntimeException{
		public EnumSyntaxeError() {
			super("You have error in you syntaxe, chech if your code support every Enum value");
		}
	}
}
