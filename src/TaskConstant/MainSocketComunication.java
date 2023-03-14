package TaskConstant;

import java.time.LocalDateTime;

import Exception.sqlException;

public class MainSocketComunication {

	
	
	
	
	public static enum FirstSocketComunicationTaskEnum{
		
	}
	
	
	public static enum OtherSocketComunicationTaskEnum{
		UserRegistration(),CreateNewChat,SendMessage,//nahrada za inzert Message,
		//prvne zalozi chat,
		ShowMessage,getMessage,
		;
		
	}
	
	public static void CallAppropriateTask(OtherSocketComunicationTaskEnum type,SocketComunicationInterface interf) {
		if(type==OtherSocketComunicationTaskEnum.UserRegistration) {
			interf.UserRegistration();
		}
		else if(type==OtherSocketComunicationTaskEnum.CreateNewChat) {
			interf.CreateNewChat();
		}
	}
	
	public static interface SocketComunicationInterface{
		public void UserRegistration(SocketComunication message);
		public void CreateNewChat(SocketComunication message) throws sqlException;
		// METODA NAVRATI INT VZJADRUJICI PORADI V CHATU
		public LocalDateTime InsertMessage(SocketComunication message) throws Exception;
		
	}
	
	
	
	
}
