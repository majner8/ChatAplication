package Server.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TaskConstant.SocketovaKomunikace;
import TaskConstant.Interface.ChatComunicationInterface;

//pouze implementuje serverovou cast, v pripade clienta, dědi tuto tridu a pouze overidduje methodusave intoMessageChatHistory

/**Class which process received ChatTask and manage their save into database */ 
public  class ChatDatabaseProcessTask  implements ChatComunicationInterface{	
	
	/**Method manage process of loading new Object ChatDatabaseProcessTask
	 * If appropriate Object do not exist in database, method will create new one */
	public static ChatDatabaseProcessTask getLoadedChat(String UUIDOfChat){
		
	}
	
	
	private ChatDatabaseProcessTask(String nameOfChat) throws SQLException{
		
	}
	
	@Override
	public void SendMessage(SocketovaKomunikace Message) throws SQLException {
		// TODO Auto-generated method stub
	
		this.stmSaveMessage.setString(1,Message.getMessage());
		this.stmSaveMessage.setString(2, Message.getUNIQUEKODTasku());
		this.stmSaveMessage.setString(3, Message.getSender());
		this.stmSaveMessage.execute();		

	}
	//SQL Query for insert into database chat
	private final static String InrIntoDatabQuery="Insert into X (message,UniqueKodZpravy,odesilatelUUID) values(?,?,?,?))"; 

	//Prepared Statemnt for Inserting message into table.
	private PreparedStatement stmSaveMessage=getConnection().prepareStatement(InrIntoDatabQuery.replace(MainDatabase.getReplacement(),getNameOfChat()));	
	

	//List of PreparedStatement for saving a notice when a member of chat show message
	private final List<PreparedStatement> SetTimeOfShownMessage;
	
	@Override
	public void SenderShowMessage(SocketovaKomunikace Message) throws SQLException {
		this.SetTimeOfShownMessage.forEach(PrpStm ->{
			try {
				PrpStm.setTimestamp(1, Timestamp.valueOf(Message.getTimeOfMessage()));
				PrpStm.execute();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			
			
		});
	}

	
	private static final String QueryTaskChangeMessage;
	private final PreparedStatement ChangeMessageSTM=getConnection().prepareStatement(this.QueryTaskChangeMessage.replace(MainDatabase.getReplacement(),getNameOfChat()));

	@Override
	public void ChangeMessage(SocketovaKomunikace Message) throws SQLException {
		
		this.ChangeMessageSTM.setString(1, Message.getUUIDMessage());
		this.ChangeMessageSTM.setString(2,Message.getMessage());
		this.ChangeMessageSTM.execute();
	}

	


	class ChatMemberManipulation implements Server.Chat.ChatMemberManipulation{

		
	private static String createTriggerForChatManipulationStructure="";
	//Maps with query for save notice into quick history chat for each user
	//each value contain query task, which save quick historyOfMessage appropriate member of User
	Map <String ,String > mapsOfTriggerCode=Collections.synchronizedMap(new HashMap<String,String>());
	public ChatMemberManipulation(ResultSet rs) throws SQLException {
		
		
	}
	private void initMapsOfTriggerCode(ResultSet) {
		
	}

	
	@Override
	public void UserLeave(String userUUID) throws SQLException {

		
	}

	@Override
	public void UserAdd(String userUUID) throws SQLException {
		
	}
	
}

	
	
	private final Connection con;
	private final String nameOfChat;
	protected String getNameOfChat() {
		return this.nameOfChat;
	}
	protected Connection getConnection() {
		return this.con;
	}
	
}

