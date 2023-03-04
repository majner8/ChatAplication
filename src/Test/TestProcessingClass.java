package Test;

import ProcessTaskDatabase.ProcessingTaskChatDatabase;
import ProcessTaskDatabase.ServerProcessingTaskChatDatabase;

public class TestProcessingClass {

	public static void main(String args) {
		try {
			
			ProcessingTaskChatDatabase x=new ServerProcessingTaskChatDatabase(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
