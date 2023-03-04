package Server.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Exception.TaskConstantException;
import TaskConstant.MainEnumSocketComunication;


public class Main {

	/*public final static String pathOfSqlFile;
	public final static String PathOfDatabase;
	public final static String DatabaseUserName;
	public final static String DatabasePassword;
	*/
	private static boolean StopServer=false;
	
	public static boolean isServerStopped() {
		return Main.StopServer;
	}
	public static void stopServer() {
		Main.StopServer=true;
	}
	
	
	//Metoda zobrazí zaslanou zprávu v konzoli a zaloguje
	public static void WriteLogConsole(String Message) {
		
		System.out.println(Message);
		
	}
	



	public static void main(String args[]) {
		new Main().TestInitTask();
	}

	
	private void TestInitTask() {
		try {
			new InitTaskConstantInterface();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
/** This class scan located file of TaskContants for SocketComunication.
 * Class Prove that each interface will have appropriate enum will, for each interface method
 *enum will have appropriate enum value with same name. 
 * */
class InitTaskConstantInterface {
	
	//private final String LocationFile;
	
	public InitTaskConstantInterface() throws Exception {

		String ParentLocationOfInterfaceFile=MainEnumSocketComunication.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		ParentLocationOfInterfaceFile=ParentLocationOfInterfaceFile.substring(0, ParentLocationOfInterfaceFile.length()-4);
		ParentLocationOfInterfaceFile=ParentLocationOfInterfaceFile+"src/"+MainEnumSocketComunication.class.getPackageName();;
		File Directory=new File(ParentLocationOfInterfaceFile+"/Interface");
		String [] ListOfFile=Directory.list();
		Method xz;
		xz.
		HashMap<String,Method[]> InterFaceWithMethod=new HashMap<>();
		for(String x:ListOfFile) {
			Class<?> cls=Class.forName(String.format("TaskConstant.Interface.%s",x.substring(0, x.length()-5)));
			try {
				Class<?> enumcls=Class.forName(String.format("TaskConstant.Enum.%s",x.substring(0, x.length()-5)));
				if(!enumcls.isEnum()) {
					throw new TaskConstantException.UnrecognizableEnumFieldException(String.format("Loaded field %s is not enum type",x)); 
				}
			}
			catch(java.lang.ClassNotFoundException ex) {
				
			}
			}
		/*	
			Method [] InterMethod=Interface.getMethods();
			List<Method> EnumValue=Arrays.asList(enumcls.getMethods());
			ArrayList<String>ValueNeedtoAdd=new ArrayList<String>();
			for(Method y:InterMethod) {
				if(EnumValue.contains(y.getName())) {
					EnumValue.remove(y);
					continue;
				}
				ValueNeedtoAdd.add(y.getName());
			}
			
			Method[] EnumMetho=enumcls.getMethods();
			
			
			InterFaceWithMethod.put(x, cls.getMethods());
		}
		
		
		
		Class.forName("TaskConstant.ChatComunicationInterface");
		*/
		
	}
	
	
	
	
	
	

	
}



	