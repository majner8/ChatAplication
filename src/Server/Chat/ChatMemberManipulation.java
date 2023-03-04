package Server.Chat;

import java.sql.SQLException;

public interface ChatMemberManipulation {

	public void  UserLeave(String userUUID) throws SQLException;
	
	public void UserAdd(String userUUID) throws SQLException;
}
