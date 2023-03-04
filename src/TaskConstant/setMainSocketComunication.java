package TaskConstant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;


/**Class which provided that all socket comunication Interface have appropriate enum value,
 * If not, appropriate enum value will be created */
public class setMainSocketComunication {
	
	
	private final static String paternOfClass="(?:\\s*/\\*.\\*/)*\\s*public\\s+static\\s+class\\s+[A-z]*\\s+\\{\\s*(?:/\\*.\\*/)*(?:/\\*|//)?.[^\\*/]*";

	
	
	private final static String nameOfMainEnumClass="MainSocketComunicationEnum";
	
	
	/**Class set Enum from interface.
	 * @return Report with summary, how many enum value each field have, and warning, 
	 * if some enum value does not have appropriate interface method  */
	public static String setMainSocketComunication(boolean developMode) {
	
		
		// Defined Main class of SocketComunication taks
		Class<?> maincls=MainSocketComunication.class;
		String pathOfMainCls=maincls.getProtectionDomain().getCodeSource().getLocation().getPath();
		pathOfMainCls=pathOfMainCls.trim().substring(0, pathOfMainCls.length()-4)+"src/"+maincls.getSimpleName()+".java";

		
		//Collections, which represent change in field
		LinkedHashMap<String,LinkedList<String>>ListEnumInterMeth=new LinkedHashMap<String,LinkedList<String>>();
		
		
		//String pathPreflix=maincls.getName()+"$";
		
		//Method scan Main Class
		for(Class<?> TaskClass:maincls.getClasses()){
			if(TaskClass.getSimpleName().equals(nameOfMainEnumClass)) {
				continue;
			}
			Class<?> enumClass=null;
			Class<?> InterfaceClass=null;
			for(Class<?> x:TaskClass.getClasses()) {
				if(x.isEnum()) {
					enumClass=x;
				}
				else if(x.isInterface()) {
					InterfaceClass=x;
				}
			}
			
			LinkedList<Field> EnumMethod=new LinkedList<Field>(Arrays.asList(enumClass.getDeclaredFields()));
			
			
			EnumMethod.sort((Field o1,Field o2)->{
				return o1.getName().compareTo(o2.getName());
			}
			);
			
			LinkedList<Method>	InterfaceMethod=new LinkedList<Method>(Arrays.asList(InterfaceClass.getDeclaredMethods()));
			
			InterfaceMethod.sort((Method o1,Method o2)->{
				return o1.getName().compareTo(o2.getName());	
			}
			);
			// List of Interface, which does not have appropriate enum value
			LinkedList<String>EnumValueForAdd=new LinkedList<String>();
			// List of Enum which dont have appropriate interface
			LinkedList<String>UnRecognizeEnumValue=new LinkedList<String>();
			EnumMethod.removeIf((Object o)->{
				return ((Field)o).getName().equals("ENUM$VALUES");
			});
			
			
			
			//compare each interface method, with enum value, if interface method doent have 
			//appropriate enum value, method will put into enumValueForADd list
			//if enum value do not have interface method, method name 
			//will put into UnRecognizeEnumValue List
			while(!InterfaceMethod.isEmpty()&&!EnumMethod.isEmpty()) {
				int result=0;	
					result=InterfaceMethod.getFirst().getName().compareToIgnoreCase(EnumMethod.getFirst().getName());
					if(result>0) {
						UnRecognizeEnumValue.add(EnumMethod.getFirst().getName());
						EnumMethod.removeFirst();
					}
					else if(result==0) {
						EnumMethod.removeFirst();
						InterfaceMethod.removeFirst();
					}
					else if(result<0) {
						EnumValueForAdd.add(InterfaceMethod.getFirst().getName());
						InterfaceMethod.removeFirst();
					}	
				}
			//if some InterfaceMethod stay in scan List, will be put into EnumValue.
			for(Method xx:InterfaceMethod) {	
				EnumValueForAdd.add(xx.getName());
			}
			//if some enum stay in scan list, will be put into unrecognize..
			for(Field xx: EnumMethod) {
				UnRecognizeEnumValue.add(xx.getName());
			}
			

			if(!EnumValueForAdd.isEmpty()) {
	
				ListEnumInterMeth.put(InterfaceClass.getName(),EnumValueForAdd);
			}
			if(!UnRecognizeEnumValue.isEmpty()) {
				ListEnumInterMeth.put(enumClass.getName(),UnRecognizeEnumValue);
			}
			
		}
		StringBuilder report=new StringBuilder();
		
	
		if(developMode) {
			if(ListEnumInterMeth.isEmpty()) {
				report.append("Control system nothing find \n");
			}
			else {
				report.append("Control system find some difference\n");

				for(String x:ListEnumInterMeth.keySet()) {
					report.append((x.substring(maincls.getName().length()+1)));
					LinkedList<String> y=ListEnumInterMeth.get(x);
					for(String xx:y) {
					
						report.append("\n"+xx);
					}
					report.append("\n \n");
				}
			
			}
			System.out.println(report);
		
			//Dotaz zdali chce nektere methody vyradit nasledne upravy filu	
		}
		
		try {
			
			BufferedReader reader=new BufferedReader(new FileReader(""));
			
			//udělat kod ktery bude cist radek po radku, pokud narazi na classu, oznaceni jmeno, 
			//pokud pri cteni narazi na enum/interface, pta se v hashMap zdali jsou pro konretni interface ulozene metody
			// ChatComunication$ChatComunicationInterface.....

			
			String ReadingClass;
			boolean Note=false;
			while(reader.ready()) {
				String ReadLine=reader.readLine();
				if(!Note||!ReadLine.matches(""))
				
				if(ReadLine.matches(paternOfClass)) {
				
				}
				
				// Chech if raw code contains notes
				if(ReadLine.contains("/*")||Note) {
					if(!ReadLine.contains("*/")) {
						Note=true;
						continue;
					}
					Note=false;
				}
				if(ReadLine.contains("//")&&!Note) {
					continue;
				}
				
				
				if(ReadLine.matches(setMainSocketComunication.paternOfClass)) {
					ReadLine.split(ReadLine)
				}
				
				
			}
			
			FileWriter writer=new FileWriter(new File(""));
			writer.write(String srt, int off, int len)
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*		for(String x:ListEnumInterMeth.keySet()) {
			LinkedList<String> y=ListEnumInterMeth.get(x);
			System.out.println(x);
			for(String xx:y) {
				System.out.println(xx);
			}
		}
		
	//while(!ListEnumInterMeth.isEmpty()) {
	
		
//	}
		
		
		/*
		StringBuilder report=new StringBuilder();
		if(developMode) {
			if(!ListOfEnumInterfaceMeth.isEmpty()) {
				Set<String> set=ListOfEnumInterfaceMeth.keySet();
				for(String x:set) {
					
					
					LinkedList<LinkedList<String>>y=ListOfEnumInterfaceMeth.get(x);
					while(!y.isEmpty()) {
						LinkedList<String> z=y.getFirst();
						y.removeFirst();
						while(!z.isEmpty()) {
							z.getFirst();
							
						}
					}
				}
			}
			else {
				report.append("I do not find any diference");
				
			
			}
			*/
		return null;
		}
	
		
	
	
	public static void main(String []args) {
		setMainSocketComunication(true);
	}
}
