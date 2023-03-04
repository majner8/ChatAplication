package Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestLoadSQLFile {

	public static void main(String [] args) {
		new TestLoadSQLFile().TestLoad();
	}
	
	public void TestLoad() {
		try {
			BufferedReader read=new BufferedReader(new FileReader("C:\\Users\\anton\\Programovani\\SQL\\ChatAplication\\SablonyPrepareStatement\\PrepareStatement InsertIntoChat.sql"));
			String vystup="";
			String readed=null;
			while((readed=read.readLine())!=null) {
				vystup=vystup+readed+"\n";
			}
			
			System.out.println(vystup);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
