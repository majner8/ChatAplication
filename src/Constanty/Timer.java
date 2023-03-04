package Constanty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;


public class Timer extends java.util.Timer implements TimerInterface{


	//List Vsech Interface, nutný ke kontrole
	private ArrayList<TimerInterface> ListOfInterface=new ArrayList<TimerInterface>();	

	
	public  void removeInterface(TimerInterface interfc) {
		synchronized(this.ListOfInterface) {
			this.ListOfInterface.remove(interfc);
		}
	}
	
	public void PridejTimerInterface(TimerInterface interfc) {
			synchronized(this.ListOfInterface) {
				this.ListOfInterface.add(interfc);
			}
	}
	

	private synchronized void Kontrola() {
		synchronized(this.ListOfInterface) {
			Iterator<TimerInterface> it=this.ListOfInterface.iterator();
		
			while(it.hasNext()) {
				it.next().KontrolaCinnosti();
			}
		}
	}

		
	private java.util.Timer timer;
	

	private Timer(float WaintingCycleInMinute,boolean waitForStart) {
		
		this.timer=new java.util.Timer();
		//prevedeni casu z parametru na milisecukdy
		long Cas=(long)Math.floor(WaintingCycleInMinute*1000*60);
	
		
		TimerTask task=new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						Kontrola();
					}
					
			};
			
		//Spusti Timer s cakenim
		if(waitForStart) {
			this.timer.schedule(task, Cas,Cas);
		}
		//Spusti timer bez cekani
		else {
			
			this.timer.schedule(task, 0,Cas);
		}
	}
	
	/**Navrati novou instanci Timer,  
	 * @param WaintingCycleInMinute  Perioda opakování, násobek minuty. 
	 * @param waitForStart Vlákno před startem vyčká po dobu jednoho cyklu */
	public static Timer getNewTimer(float WaintingCycleInMinute,boolean waitForStart) {
		return new Timer(WaintingCycleInMinute,waitForStart);
	}
	
	/**Methoda ukončí činnost Timeru */
	public void StopTimer() {
		this.timer.cancel();
	}
	
	
	@Override
	public void KontrolaCinnosti() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
