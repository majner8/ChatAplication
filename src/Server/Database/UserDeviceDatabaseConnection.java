package Server.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Exception.sqlException;
import Exception.sqlException.EnumSQLException;
import Server.Database.MainDatabase.databaseTaskType;
import TaskConstant.MainSocketComunication.SocketComunicationInterface;
import TaskConstant.SocketComunication;

public class UserDeviceDatabaseConnection implements SocketComunicationInterface{
	
	private final UserDatabaseConnection UserConnection;
	private final Connection con;
	@Override
	public void UserRegistration(SocketComunication message) {
		// TODO Auto-generated method stub
		
	}
	//
	@Override
	public void CreateNewChat(SocketComunication message) throws SQLException {
		// TODO Auto-generated method stub	
		
		String[] queryTask=this.UserConnection.getTaskCreateNewChat();
		try {
			Statement stm=con.createStatement();
			for(String task:queryTask) {			
				try {
				stm.execute(task);
				}
				catch(SQLException e) {
					if(e.getErrorCode()==1050||e.getErrorCode()==1359) {
						continue; 
					 }
					throw e;
				}
			}
		}
		 catch (SQLException e) {	
			 e.printStackTrace();
			 throw e;
		 }
	}

	
	
	@Override
	public int InsertMessage(SocketComunication message)throws Exception {
		// TODO Auto-generated method stub
		try {
			Statement stm=con.createStatement();
			ResultSet rs;
			if(message.getUUID().doesItGroupChat()) {
				if(!stm.executeQuery(DatabaseQueryPatern.getTask(databaseTaskType.VerifyIfTableExist, message.getRecipient(), null)).next()) {
					throw new sqlException(EnumSQLException.GroupChatDoesNotExist);
				}
				rs=stm.executeQuery(DatabaseQueryPatern.getTask(databaseTaskType.InsertMessageGroupChat, message.getRecipient(), null))
			}
			
		}
		catch(SQLException e) {}
		
		if(message.getUUID().doesItGroupChat()) {
			
			if(!stm.executeQuery(UserConnection.getTask(databaseTaskType.VerifyIfTableExist, message.getRecipient(), null)).next()) {
			
			}
		}
		else {
			
		}
		
		try {
			
			Statement stm=con.createStatement();
			if(!stm.executeQuery(UserConnection.getTask(databaseTaskType.VerifyIfTableExist, message.getRecipient(), null)).next()) {
				//osetrit, mozna bude treba vytvorit novy task, message neobsahuje primarne task k vytvoreni
				
				//Posibly another Thread has already create Chat
				try {
					
				this.CreateNewChat(message)
			
				}
				catch(SQLException e) {}
			}
			String sqlInsert=DatabaseQueryPatern.InsertMessageIntoTable(message.getRecipient(), message.getUUIDSender(), message.getMessage(), message.getUNIQUDECodeMessage());
			//prikaz pro ziskani UUID kodu zpravy.
			String SQLoderOFMessage= DatabaseQueryPatern.getOrderOfMessage(message.getRecipient(), message.getUNIQUDECodeMessage());
			stm.executeUpdate(sqlInsert);
			return stm.executeQuery(SQLoderOFMessage).getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	
}
