package Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import TaskConstant.MainEnumSocketComunication;

public class ClasPat {

	public static void main(String[] args) {
		 String LocationFile;
		
			
			String []file=MainEnumSocketComunication.class.getProtectionDomain().getCodeSource().getLocation().getFile().split("/");
			file[file.length-1]="src";
			LocationFile=String.join("/", file);
			System.out.println(LocationFile);
	}
	
	
	public void findFile() {
		
		
		URL url=InitTaskContanstInterface.class.getProtectionDomain().getCodeSource().getLocation();
		String urll=(url.getFile()+InitTaskContanstInterface.class.getPackageName().replaceFirst("bin", "src"));
		
		File f=new File(urll);
		for(String s:f.list()) {
			System.out.println(s);
		}
	}
public void ChangeMethod() {
		
	StringBuilder bd=new StringBuilder();

		URL url=InitTaskContanstInterface.class.getProtectionDomain().getCodeSource().getLocation();
		
		String urll=(url.getFile()+InitTaskContanstInterface.class.getPackageName()+"/"+InitTaskContanstInterface.class.getSimpleName()+".java").replaceFirst("bin", "src");
		try {
			//BufferedReader rd=new BufferedReader((new FileReader("/C:/Users/anton/Programovani/VyvojCodu/Chat_Aplication/src/Test/InitTaskContanstInterface.java")));
			BufferedReader rd=new BufferedReader((new FileReader(urll)));
			
			while(rd.ready()) {
				String s=rd.readLine();
				if(s.contains("public void a()")) {
					bd.append(s.replace("a()", "b()")).append("\n");

				}
				else {
					bd.append(s).append("\n");
				}
			}
			rd.close();
			FileWriter fl=new FileWriter(urll); 
			fl.write(bd.toString());
			fl.close();
			System.out.println("konec");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
