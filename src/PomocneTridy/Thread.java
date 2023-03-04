package PomocneTridy;

public abstract class Thread extends java.lang.Thread {

	private boolean stop=false;
	private boolean TaskFinis;
	private int pocetOpakovani=0;
	private final int MaximumOpakovani;
	private final int sleepTime;
	
	public Thread(int MaximumCycle,int sleepTime) {
		this.MaximumOpakovani=MaximumCycle;
		this.sleepTime=sleepTime;
	}
	
	@Override
	public final void run() {

		while(!stop) {
			try {
				if(this.TaskFinis) {
					this.pocetOpakovani=0;
				}
				else {
					
				}
				
				
				this.sleep(2);


				
			if(this.pocetOpakovani>=this.MaximumOpakovani) {
				this.wait();
				this.pocetOpakovani=0;
			}
			
			}
			catch(InterruptedException e) {
				this.stop=true;
				break;
			}
			catch(Exception e) {
				
			}
		}
		
		
	}
	
	public void Stop() {
		
		this.stop=true;
	}
	
	public void MakeTask() {
		this.TaskFinis=true;
		this.VykonejCinnost();
	}
	
	protected abstract void VykonejCinnost();

	
}
