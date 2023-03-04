package Server.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import TaskConstant.SocketovaKomunikace;
import TaskConstant.Interface.UserInteraction;

public class UserInteractionDatabaseProcessTask extends MainDatabase implements UserInteraction {

	/**Method call SQL Statement */
	public ResultSet CallStatement(String Statement)throws SQLException {}
	
	
	
	@Override
	public SocketovaKomunikace[] LoadHistoryOfChat(String UUIDChat, String UUIDLastMessage, int AmoutOfMessage) {
		// TODO Auto-generated method stub
		return null;
	}

}
