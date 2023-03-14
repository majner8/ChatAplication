package Server.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import Exception.sqlException;
import Server.Database.MainDatabase.DatabaseParametr;
import Server.Database.MainDatabase.databaseTaskType;
import Server.Main.ServerManipulation;
import TaskConstant.MainSocketComunication.SocketComunicationInterface;

public  class UserDatabaseConnection implements ServerManipulation {

	
	
	public static String[] getTaskCreateNewChat() {
		String tableName;
		ArrayList<String>columnValue;
		s;
		String createTable=this.replaceTableNameAndListOfCharacter(MainDatabase.databaseTaskType.createChatTable, tableName,columnValue);
		//generate task for creating table with permision for each User.
		String createPermisionTable=this.replaceTableNameAndListOfCharacter(MainDatabase.databaseTaskType.createPermisionTable, tableName, columnValue);
		
		//generate task for creating trigger, which set every new message into User quick message table.
		String createTriggerForTable=this.replaceTableNameAndListOfCharacter(databaseTaskType.createInsertTrigger, tableName, null);		
		
		String [] tasks=createTriggerForTable.split(DatabaseParametr.BeginFunction.getCharacter());	
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
	}

	/**Method return a SQL task as String, with change nameOfTable, eventual column value
	 * @param type type of Task, 
	 * @param nameOfTable NameOftable */
	public static String getTask(databaseTaskType type,String nameOfTable, ArrayList<String>columnValue) {
		return replaceTableNameAndListOfCharacter(type,nameOfTable,columnValue);
	}
	
	/** Method replace table name and List of element, which is representing for example list of user, which is joined into this chat
	 * @param message It is representing rought patern of SQL for each command. 
	 * @param tablename it is representing table name
	 * @param listOfReplacement it is representing list of user, or other column value.
	 *  if you do not have, put null instead*/
 	private static String replaceTableNameAndListOfCharacter(databaseTaskType TypeOfTask, String tablename,
			ArrayList<String> listOfReplacement) {
		String message=MainDatabase.getQueryPatern(TypeOfTask);
		
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

	
	
	@Override
	public void Start() {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void Stop() {
		// TODO Auto-generated method stub
		
	}


	


	


}

