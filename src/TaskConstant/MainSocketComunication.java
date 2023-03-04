package TaskConstant;

import java.sql.SQLException;

public final class MainSocketComunication {

	// List of enum which do not have extension
	private final static MainSocketComunicationEnum[] listOfEnum = {
			MainSocketComunicationEnum.PotvrzeniPrijetiZpravy };

	public static enum MainSocketComunicationEnum {
		UserInteraction(), ChatComunication(),AplicationInteraction(), PotvrzeniPrijetiZpravy();
	}

	public static class ChatComunication {

		public static void CallAppropriateTask(SocketovaKomunikace socketMessage, ChatComunicationInterface inter)
				throws SQLException {

			ChatComunicationEnum Type = null;// dodělat az bude socketMessageHotova

			if (Type == ChatComunicationEnum.SendMessage) {
				inter.SendMessage(socketMessage);
			} else if (Type == ChatComunicationEnum.SenderShowMessage) {
				inter.SenderShowMessage(socketMessage);
			} else if (Type == ChatComunicationEnum.ChangeMessage) {
				inter.ChangeMessage(socketMessage);
			}

		}

		public static interface ChatComunicationInterface {

			public void SendMessage(SocketovaKomunikace Message) throws SQLException;

			public void MessageWasProcess() throws SQLException;
			public void ChangeMessage(SocketovaKomunikace Message) throws SQLException;

			public void SenderShowMessage(SocketovaKomunikace Message) throws SQLException;

		}

		public static enum ChatComunicationEnum {
			SendMessage(),MessageWasProcess(),/*After receive SendMessage, message will be save into database
			-with new UUID, this will be send to client, and UUID will be replace */
			SenderShowMessage(), ChangeMessage;
			
			public static ChatComunicationEnum [] getListOFEnumValue(String [] nameOfValue,int start ) throws IllegalArgumentException
			,IndexOutOfBoundsException{
				ChatComunicationEnum [] y=new ChatComunicationEnum [nameOfValue.length];
				int xx=start;
				for(String x:nameOfValue) {
					y[xx]=ChatComunicationEnum.valueOf(x);
					xx++;
				}
				return y;
			} 

		}

	}

	public static class UserInteraction {

		public static void CallAppropriateTask(UserInteractionEnum UserEnum, SocketovaKomunikace Message,
				UserInteractionInterface UserInterface) {

		}

		public static interface UserInteractionInterface {

			public void LoadHistory() throws SQLException;

		}

		public static enum UserInteractionEnum {
			LoadHistory();
			
			public static UserInteractionEnum [] getListOFEnumValue(String [] nameOfValue,int start ) throws IllegalArgumentException
			,IndexOutOfBoundsException{
				UserInteractionEnum [] y=new UserInteractionEnum [nameOfValue.length];
				int xx=start;
				for(String x:nameOfValue) {
					y[xx]=UserInteractionEnum.valueOf(x);
					xx++;
				}
				return y;
			} 
			
		}
	}

	public static class AplicationInteraction {

		public static void CallAppropriateTask(AplicationInteraction AplicationEnum, SocketovaKomunikace Message,
				AplicationInteractionInterface AplicationInterface) {

		}

		public static interface AplicationInteractionInterface {
			public void LoadHistory() throws SQLException;
		}

		public static enum AplicationInteractionEnum {
			LoadHistory();
			
			
			public static AplicationInteractionEnum [] getListOFEnumValue(String [] nameOfValue,int start ) throws IllegalArgumentException
			,IndexOutOfBoundsException{
				AplicationInteractionEnum [] y=new AplicationInteractionEnum [nameOfValue.length];
				int xx=start;
				for(String x:nameOfValue) {
					y[xx]=AplicationInteractionEnum.valueOf(x);
					xx++;
				}
				return y;
			} 
		}

	}
}
