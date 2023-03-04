package Server.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Exception.ActiveUserRunTimeException;
import PomocneTridy.UniqueHashMap;

/** Class which keep User UUID and his UserConnection instance, if a User is online.
 * Other class such as chatConnection referencing this user.
 */
public class ActiveUser {
	
	private final String UserUUDI;
	private volatile UserConnection User=null;
	//collection for ActiveUser, or User which is reference.
	private static UniqueHashMap<String,ActiveUser> ActiveUser=new UniqueHashMap<String,ActiveUser>();
	
	/** Method return UserConnection, if a UserConnection doesNotExit, metod create new one*/
	public synchronized UserConnection getUserConnection() {
		
		if(User!=null) {
			return User;
		}
		//zalozy novy UserConnection
		this.User=new UserConnection();
		
		return User;
	}
	
	/** Method load Active User From field*/
	public static ActiveUser getActiveUser(String UUIDUser) {
		ActiveUser user;
		if((user=ActiveUser.get(UUIDUser))==null) {
			user=new ActiveUser(UUIDUser);
			ActiveUser.put(user.toString(), user);
		}
		return user;
		
	}
	
	
	
	
	private ActiveUser(String UserUUID) {
		this.UserUUDI=UserUUID;
	}
	
	@Override
	public String toString() {
		return this.UserUUDI;
	}
}
