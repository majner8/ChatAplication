package ProcessTaskDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Server.Chat.ChatConnection;
import Server.Database.DatabazovaKomunikace;
import Server.ZpracovaniTasku.MemberChatManipulationInterface;
import TaskConstant.SocketovaKomunikace;

public class ServerProcessingTaskChatDatabase extends ProcessingTaskChatDatabase {

	public ServerProcessingTaskChatDatabase(String UUIDChatu) throws Exception {
		super(UUIDChatu);
		// TODO Auto-generated constructor stub
	}

	//List of PreparedStatement, for save shorten message into history of activity each Member
	private List<PreparedStatement> Member=Collections.synchronizedList(new ArrayList<PreparedStatement>());
	//Form of query for inserting shorten message, form have to be finish when will be known all detail of table
	private final static String SaveShortenMessage="Insert into X()values(?,?)"
	//Form of query for selecting User member 
	private final static String SelectListOfChatMember="Select PridanyUserUUID from X chatsetting";
	//Method which inicializet List of Member
	private void initListOfMember() throws SQLException {
		
		Statement stm=getConnection().createStatement();
		ResultSet output=stm.executeQuery(this.SelectListOfChatMember.replaceFirst(DatabazovaKomunikace.getReplacement(), getNameOfChat()));
		
		while(output.next()) {
			this.Member.add(super.GetConnection().prepareStatement(this.SaveShortenMessage.replaceFirst(super.getReplacement(), output.getString(1))));
		
		}
	}
	
	//SQL Query for insert into database chat
	private final static String InrIntoDatabQuery="Insert into X (message,UniqueKodZpravy,odesilatelUUID) values(?,?,?,?))"; 
	//Prepared Stateemnt for Inserting message into table.
	private PreparedStatement stmSaveMessage=super.GetConnection().prepareStatement(InrIntoDatabQuery.replaceAll(DatabazovaKomunikace.getReplacement(),getNameOfChat()));

	@Override
	public void SendMessage(SocketovaKomunikace Message) {
		// TODO Auto-generated method stub
		try {
			this.stmSaveMessage.setString(1,Message.getMessage());
			this.stmSaveMessage.setString(2, Message.getUNIQUEKODTasku());
			this.stmSaveMessage.setString(3, Message.getSender());
			this.stmSaveMessage.execute();
			
			this.Member.forEach(User->{
			
					
					// dodělat az bude presne znama tabulka
					try {
						User.setString(1, message.getSender());
					
					User.setString(2, message.getMessage());
					User.execute();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace()
						//napsat do konzole více info o konretni zprávě..
					}
			});
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	
	
	
	
	
}


//Class perfom task SendMessage, including save message into Chat table, 
//and save shorten message into quick history of activity, to each member of chat.
	private  class SendMessage implements MemberChatManipulationInterface{
	
		private final Connection con;
		
		public SendMessage() {
	
		}
		
		//Form of query for selecting User member 
		private final static String SelectListOfChatMember="Select PridanyUserUUID from X chatsetting";
		private void initUserMember() {
			
			Statement stm=con.createStatement();
			ResultSet output=stm.executeQuery(this.SelectListOfChatMember.replaceFirst(DatabazovaKomunikace.getReplacement(), getNameOfChat()));
			
			while(output.next()) {
				output.getString(1);
			}
		}	
		/** Method save message and  */
		public void runProcess(SocketovaKomunikace message,ChatConnection chat) {
			this.SaveMessageIntoDatabase(message);
			this.UpdateUserTable(message,chat);
		}
		

		//SQL Query for insert into database chat
		private final static String InrIntoDatabQuery="Insert into X (message,UniqueKodZpravy,odesilatelUUID) values(?,?,?,?))"; 
		//Prepared Stateemnt for Inserting message into table.
		private PreparedStatement stmSaveMessage=con.prepareStatement(InrIntoDatabQuery.replaceAll(DatabazovaKomunikace.getReplacement(),getNameOfChat()));
		private void SaveMessageIntoDatabase(SocketovaKomunikace message) {
			try {
			this.stmSaveMessage.setString(1,message.getMessage());
			this.stmSaveMessage.setString(2, message.getUNIQUEKODTasku());
			this.stmSaveMessage.setString(3, message.getSender());
			this.stmSaveMessage.execute();
			}
			catch(Exception e) {
				
			}
		}
		
		private List<PreparedStatement> Member=Collections.synchronizedList(new ArrayList<PreparedStatement>());
		
		private void UpdateUserTable(SocketovaKomunikace message,ChatConnection chat) throws Exception {
			
			
			
			this.Member.forEach(User->{
				try {
					
					// dodělat az bude presne znama tabulka
					User.setStrings(1, message.getSender());
					User.setString(2, message.getMessage());
					User.execute();
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			});
			
		}
		
		//Method which is call when a user from chat was removed
		@Override
		public void RemoveUser(String UserUUID) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
