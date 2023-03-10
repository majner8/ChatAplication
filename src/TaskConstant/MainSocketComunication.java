package TaskConstant;

public class MainSocketComunication {

	public static enum FirstSocketComunicationTaskEnum{
		
	}
	
	
	public static enum OtherSocketComunicationTaskEnum{
		UserRegistration(),CreateNewChat,InsertMessate;
		
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
		public void CreateNewChat(SocketComunication message);
		public void InsertMessage(SocketComunication message);
	}
	
	
	
	
}
