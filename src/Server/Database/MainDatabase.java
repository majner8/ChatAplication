package Server.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import Exception.sqlException;
import Exception.sqlException.EnumSQLException;

public abstract class MainDatabase {

	
	public static ArrayList<String> getListOfProcessTaskdatabaseTaskType ( MaindatabaseTaskType TypeOfTask,String tableName,ArrayList<String> columnValue) {
		ArrayList<String>  returns=new ArrayList<String>();
		String task;
	
		if(TypeOfTask==MaindatabaseTaskType.createChatTable) {
			//Generate task for  creating new table for Chat
			returns.add(MainDatabase.replaceTableNameAndListOfCharacter(databaseTaskType.createChatTable.getTask(), tableName, columnValue));
			//generate task for creating table with permision for each User.
			returns.add(MainDatabase.replaceTableNameAndListOfCharacter(databaseTaskType.createPermisionTable.getTask(), tableName, columnValue));
		
			
			//generate task for creating trigger, which set every new message into User quick message table.
			task=MainDatabase.replaceTableNameAndListOfCharacter(databaseTaskType.createInsertTrigger.getTask(), tableName, null);		
			
			String [] tasks=task.split(DatabaseParametr.BeginFunction.getCharacter());	
					//method set a begining of trigger
			String beginOfTrigger=tasks[0];
			tasks=tasks[1].split(DatabaseParametr.EndFunction.getCharacter());
				//set a patern 
			String patern=tasks[0];
				// set end of trigger
			String EndOfTrigger=tasks[1];
			patern=patern.trim().repeat(columnValue.size());
		
			String message=String.format("%s %s %s %s %s", beginOfTrigger,
					DatabaseParametr.BeginFunction.getCharacter(),
					patern,DatabaseParametr.EndFunction.getCharacter()
					,EndOfTrigger);
			Iterator<String> it=columnValue.iterator();
			while(it.hasNext()) {
				message.replace(DatabaseParametr.CollectionsCharacter.getCharacter(), it.next());
			}
			
			returns.add(message);
			
			return returns;
		}
		return null;
		
	}

	/** Method replace table name and List of element, which is representing for example list of user, which is joined into this chat
	 * @param message It is representing rought patern of SQL for each command. 
	 * @param tablename it is representing table name
	 * @param listOfReplacement it is representing list of user, or other column value.
	 *  if you do not have, put null instead*/
	private static String replaceTableNameAndListOfCharacter(String message, String tablename,
			ArrayList<String> listOfReplacement) {

		if (message == null || tablename == null) {
			throw new NullPointerException("Parametr message and tablename are not allowed to equal null");
		}
		//method replace TableName character by TableName
		message.replaceAll(DatabaseParametr.TableNameCharacter.getCharacter(), tablename);
	
		// method verify, if have to replace List OF user
		if (listOfReplacement != null) {
			
			String[] list = message.split(DatabaseParametr.DivedeCharacterColumn.getCharacter());
		
			for(int i=0;i<list.length;i++) {
			
				if (list[i].contains(DatabaseParametr.CollectionsCharacter.getCharacter())) {
					
					String startMessage="";
					String patern=list[i];
					String endMessage="";
					//method ask if a represent of ListOfUser is a first argument
					if (list[i].matches(".*\\)\s*;.*")) {
						String [] xx=list[i].split("\\)\s*;.*");
						patern=xx[0];			
						endMessage=list[i].split(patern)[0];
					}
					//method ask if a represent of ListOfUser is last argument
					else if(list[i].matches(".*\\(\s*"+DatabaseParametr.CollectionsCharacter.getCharacter())) {
						String[] xx;
						// fucntion verify, chance that SQL format has only one 
						// parametr which is character of List OfUser
						if(!patern.equals(list[i])) {
							xx=patern.split(".*\\(\s*");
						}
						else{xx=list[i].split(".*\\(\s*");
						}
						patern=xx[0];
						startMessage=list[i].split(patern)[0];
					}
					
					patern=patern+" ";
					patern=patern.repeat(listOfReplacement.size());
					list[i]=startMessage+patern+endMessage;
					break;
				}
			}
			
			message=String.join(",",list);
			Iterator<String> it=listOfReplacement.iterator();
			while(it.hasNext()) {
				message.replace(DatabaseParametr.CollectionsCharacter.getCharacter(), it.next());
			}
		}		
		return message;

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
		DivedeCharacterColumn(-1,","),
		BeginFunction(-1,"BEGIN"),
		EndFunction(-1,"END")
;
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
				+"%s DateTime);", DatabaseParametr.TableNameCharacter.getCharacter(),DatabaseParametr.LengUUIDPlayer.getLenght(),DatabaseParametr.LengMessage.getLenght(),DatabaseParametr.LengUniqueCodeMessage.getLenght(),DatabaseParametr.CollectionsCharacter.getCharacter())),
		createPermisionTable(""),
		createInsertTrigger("");				
						
						private String task;
						
						databaseTaskType(String task){
							this.task=task;
							
						}
						
						public String getTask() {
							return this.task;
						}
						
	}
	
	
	
	
	
	
	
}


