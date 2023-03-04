package PomocneTridy;

public abstract class TimerChecher implements Runnable {

	private long LastTimeActivity;
	
	public void setLastCurrentActivity() {
		this.LastTimeActivity=System.currentTimeMillis();
	}
	
	public abstract void ClassDoesNotActive();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
}
