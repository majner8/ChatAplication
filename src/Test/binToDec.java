package Test;

import java.util.Scanner;

public class binToDec {
	
    public static void main(String[] args) {
    	
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the binary number : ");
        String binary = scan.nextLine();
        int len = binary.length()-1;
        double decimal =((binary.charAt(0)-48) * Math.pow(2,len));
        int j = 1;
        for(int i = 1; i < len; i++)
        {
            decimal = decimal + ((binary.charAt(i)-48) * Math.pow(2,len-j));
            j++;
        }
        System.out.println(decimal);
    }
}