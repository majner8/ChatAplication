package Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;

import Exception.TaskConstantException.UnrecognizableEnumFieldException;
import TaskConstant.MainEnumSocketComunication;
import TaskConstant.Interface.ChatComunication;

public class InitTaskContanstInterface {

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		try {
			new InitTaskContanstInterface().test();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void test()throws Exception {
		String ParentLocationOfInterfaceFile=MainEnumSocketComunication.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		ParentLocationOfInterfaceFile=ParentLocationOfInterfaceFile.substring(0, ParentLocationOfInterfaceFile.length()-4);
		ParentLocationOfInterfaceFile=ParentLocationOfInterfaceFile+"src/"+MainEnumSocketComunication.class.getPackageName();;
		File Directory=new File(ParentLocationOfInterfaceFile+"/Interface");
		String [] ListOfFile=Directory.list();
		HashMap<String,Method[]> InterFaceWithMethod=new HashMap<>();
		for(String x:ListOfFile) {
			
			Class<?> cls=Class.forName(String.format("TaskConstant.Interface.%s",x.substring(0, x.length()-5)));
			Class<?> enumcls=Class.forName(String.format("TaskConstant.Enum.%s",x.substring(0, x.length()-5)));
			
			if(!enumcls.isEnum()) {
				System.out.println("ne");
			}
			System.out.println(cls.getProtectionDomain().getCodeSource().getLocation().getFile());
	}
	}
	
	public void LoadInterfaceFromClashPath() {
		try {
			
			String ParentLocationOfInterfaceFile=MainEnumSocketComunication.class.getProtectionDomain().getCodeSource().getLocation().getFile();
			ParentLocationOfInterfaceFile=ParentLocationOfInterfaceFile.substring(0, ParentLocationOfInterfaceFile.length()-4);
			ParentLocationOfInterfaceFile=ParentLocationOfInterfaceFile+"src/"+MainEnumSocketComunication.class.getPackageName();;
			File Directory=new File(ParentLocationOfInterfaceFile+"/Interface");
			
			
			for(String x:Directory.list()) {
			}
			
			
			String LocationOfInterfaceFile=MainEnumSocketComunication.class.getProtectionDomain().getCodeSource().getLocation().getFile();
			LocationOfInterfaceFile=LocationOfInterfaceFile.replace("bin", "src")+MainEnumSocketComunication.class.getPackageName()+".Interface";
			System.out.println(LocationOfInterfaceFile);
			
			for(Method x:Class.forName("TaskConstant.ChatComunicationInterface").getMethods()) {
				
				System.out.println(x.getName());
			}
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void b() {
		
		
		URL url=InitTaskContanstInterface.class.getProtectionDomain().getCodeSource().getLocation();
		System.out.println(url.getFile()+InitTaskContanstInterface.class.getPackageName()+"/"+InitTaskContanstInterface.class.getSimpleName());
		
		String urll=(url.getFile()+InitTaskContanstInterface.class.getPackageName()+"/"+InitTaskContanstInterface.class.getSimpleName()+".java").replaceFirst("bin", "src");
				System.out.println(urll);
		try {
			//BufferedReader rd=new BufferedReader((new FileReader("/C:/Users/anton/Programovani/VyvojCodu/Chat_Aplication/src/Test/InitTaskContanstInterface.java")));
			BufferedReader rd=new BufferedReader((new FileReader(urll)));
			
			while(rd.ready()) {
				System.out.println(rd.readLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Konec");
	}


}
