package Server.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import Exception.sqlException;
import Exception.sqlException.EnumSQLException;
import Server.User.Connection;

public abstract class MainDatabase {

	
	public static String [] getListOfProcessTaskdatabaseTaskType ( databaseTaskType TypeOfTask,String tableName,ArrayList<String> columnValue) {
		ArrayList<String>  returns=new ArrayList<String>();
		String task;
	
		if(TypeOfTask==databaseTaskType.createChatTable) {
			//create new table for Chat
			task=MainDatabase.replaceTableName(TypeOfTask.getTask(), tableName);
			task=MainDatabase.replaceListOfCharacter(task, columnValue);
			returns.add(task);
			
			
			
			return new String[] {task};
		}
		return null;
		
	}
	
	
	private static String replaceTableName(String message,String replacement) 
	{
		return message.replace(DatabaseParametr.TableNameCharacter.getCharacter(),replacement);}
	
	private static String replaceListOfCharacter(String message,String[] listOfReplacement) {
		String [] list=message.split(DatabaseParametr.DivedeCharacterColumn.getCharacter());
		for(String x:list) {
			if(x.contains(DatabaseParametr.CollectionsCharacter.getCharacter())) {
				if(x.matches(".*\\)\s*\\).*")) {
					
				}
				break;
			}
			
		}
		
	
		
			
		
		String variable=" "+list[1].trim().split("\\)")[0];
		String Restmessage=list[0];
		Iterator<String>it=listOfReplacement.iterator();
		while(it.hasNext()) {			
			Restmessage=Restmessage+it.next()+variable;
			if(it.hasNext()) {
				Restmessage=Restmessage+",";
			}
		}
		Restmessage=Restmessage+");";
		return Restmessage;
	}
	
	private static String getSQLTaskCreateChatTable() {
		//catch table is exist.
		
		return databaseTaskType.createChatTable.getTask();
		
	}
	
	public static String getTableUUIDNameFromPlayerUUID(String firstUUID,String secondUUID) throws sqlException {
		DatabaseParametr.LengUUIDPlayer.CompareLenght(firstUUID);
		DatabaseParametr.LengUUIDPlayer.CompareLenght(secondUUID);
		
		String [] sort=new String [] {firstUUID,secondUUID};
		Arrays.sort(sort);
		return String.join("", sort);
		
	}
	
	
	
	public static enum DatabaseParametr{
		//kontrola zdali volame odpovidajici enum Stringm/int pripadne vyvolat chybu
		LengUUIDPlayer(20,null),
		LengMessage(250,null),
		LengUniqueCodeMessage(20,null),
		TableNameCharacter(-1,"XXX"),
		CollectionsCharacter(-1,"YYY"),
		DivedeCharacterColumn(-1,",");
		private int Lenght;
		private String character;
		DatabaseParametr(int i,String character) {
			this.Lenght=i;
			this.character=character;
		}
		
		public int getLenght() {
			return this.Lenght;
		}
		public String getCharacter() {
			return this.character;
		}
		
		
		public void CompareLenght(String valueForCompare) throws sqlException  {
			if(valueForCompare.length()!=this.getLenght()) 
			{
				throw new sqlException(EnumSQLException.UUIDPlayerToLong,valueForCompare);
			}
		}
	}
	
	public static enum MaindatabaseTaskType{
		createChatTable;
		
		
	
		
	}
	//dateType, which has unknown quantity, has to be at end of Task.
	private static enum databaseTaskType{
		
		createChatTable(String.format("create table %s(UUIDSender varchar(%d),"
				+"Message varchar(%d),UNiqueCodeMessage varchar(%d),"
				+"%s DateTime);", DatabaseParametr.TableNameCharacter.getCharacter(),DatabaseParametr.LengUUIDPlayer.getLenght(),DatabaseParametr.LengMessage.getLenght(),DatabaseParametr.LengUniqueCodeMessage.getLenght(),DatabaseParametr.CollectionsCharacter.getCharacter()));
						
						
						private String task;
						
						databaseTaskType(String task){
							this.task=task;
							
						}
						
						public String getTask() {
							return this.task;
						}
						
	}
	
	public static void main(String []args) {
		ArrayList<String> list=new ArrayList<String>();
		list.add("dasads");
		list.add("das");
		String [] x=MainDatabase.getListOfProcessTaskdatabaseTaskType(databaseTaskType.createChatTable, "ahoj", list);
		for(String xx:x) {
			System.out.println(xx);
		}
	}
	
	
	
	
	
}


