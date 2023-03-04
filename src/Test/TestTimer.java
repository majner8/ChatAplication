package Test;

import Constanty.Timer;
import Constanty.TimerInterface;

public class TestTimer implements TimerInterface {

	public static void main(String [] args) {
		
		
		Timer t=Timer.getNewTimer(0.1F, false);
		t.addCekaciRada(new TestTimer());
		System.out.println("Start timer");
	}

	@Override
	public void KontrolaCinnosti() {
		// TODO Auto-generated method stub
		System.out.println("KOntrola cinnosti X");
	}
	


}
