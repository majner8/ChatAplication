package Server.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
	public void InsertMessage(SocketComunication message) {
		// TODO Auto-generated method stub
		try {
			Statement stm=con.createStatement();
			if(!stm.executeQuery(UserConnection.getTask(databaseTaskType.VerifyIfTableExist, message.getRecipient(), null)).next()) {
				//osetrit, mozna bude treba vytvorit novy task, message neobsahuje primarne task k vytvoreni
				this.CreateNewChat(message)
			}
			stm.getge
			stm.executeup
			stm.executeUpdate("");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	
}
