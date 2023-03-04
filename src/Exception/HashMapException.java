package Exception;

public class HashMapException extends Exception {

	
	public HashMapException(String message) {
		super(message);
	}
	
	public HashMapException() {
		super("Map has already contain value for insert key");
	}
}
