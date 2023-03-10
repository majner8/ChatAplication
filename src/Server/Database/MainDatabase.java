package Server.Database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Exception.sqlException;
import Exception.sqlException.EnumSQLException;
import Server.Main.Main;
import Server.Main.ServerManipulation;
import TaskConstant.SocketComunication;

public abstract class MainDatabase implements ServerManipulation   {


	
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
	
 	private static Map<String, String> ListOfPaternSQLTask;

 	static {
		ListOfPaternSQLTask= Collections.synchronizedMap(new HashMap<String, String>());
		for (databaseTaskType x : databaseTaskType.values()) {
			try (BufferedReader rd = new BufferedReader(new FileReader(x.getURL()))) {
				String text = "";
				while (rd.ready()) {
					text = text + rd.readLine() + "\n";
				}
				ListOfPaternSQLTask.put(x.toString(), text);

			} catch (Exception e) {
				e.printStackTrace();
				ListOfPaternSQLTask=null;
			}
		}
 	}
		
	public static String getQueryPatern(databaseTaskType TypeOfTask) {
		if(ListOfPaternSQLTask==null) {
			throw new NullPointerException("Exception with loading SQL patern");
		}
		return ListOfPaternSQLTask.get(TypeOfTask.toString());
	}
	//dateType, which has unknown quantity, has to be at end of Task.
	public static enum databaseTaskType{
			
			createChatTable(String.format("create table %s(UUIDSender varchar(%d),"
					+"Message varchar(%d),UNiqueCodeMessage varchar(%d),"
					+"%s DateTime);", DatabaseParametr.TableNameCharacter.getCharacter(),DatabaseParametr.LengUUIDPlayer.getLenght(),DatabaseParametr.LengMessage.getLenght(),DatabaseParametr.LengUniqueCodeMessage.getLenght(),DatabaseParametr.CollectionsCharacter.getCharacter())),
			createPermisionTable(""),
			createInsertTrigger(""),SaveMessage(""),VerifyIfTableExist("");				
							
					private String URLOfQUery;
							
							databaseTaskType(String URL){
								this.URLOfQUery=URL;
								
							}
							
							public String getURL() {
								return this.URLOfQUery;
							}
			
							@Override
							public String toString() {
								return this.name();
							}
		}
		

	
	


	
	
	
	
}


