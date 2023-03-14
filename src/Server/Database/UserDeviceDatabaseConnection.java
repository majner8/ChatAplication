package Server.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

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
	public void CreateNewChat(SocketComunication message) throws sqlException {
		// TODO Auto-generated method stub	
		
		String[] queryTask=DatabaseQueryPatern.getTaskCreateNewChat(message);
		try {
			Statement stm=con.createStatement();
			for(String task:queryTask) {			
		
				stm.execute(task);	
				}
			
		}
		catch(SQLException e) {
		if(e.getErrorCode()==1050) {
			return;
		 }
		e.printStackTrace();
		throw new sqlException(EnumSQLException.databaseServerIsUnavaible);
		}
	
	}
	
	//metoda musi navratit Datetime, doruceni zpravy
	@Override
	public LocalDateTime InsertMessage(SocketComunication message)throws Exception {
		// TODO Auto-generated method stub
		try {
			Statement stm=con.createStatement();
			ResultSet rs=stm.executeQuery(DatabaseQueryPatern.getTask(databaseTaskType.InsertMessage, message));
			return rs.getTimestamp(1).toLocalDateTime();
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new sqlException(EnumSQLException.databaseServerIsUnavaible);
		}
				
		
	}

	
	
}
