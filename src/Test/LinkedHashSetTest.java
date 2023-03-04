package Test;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class LinkedHashSetTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		        Map<Integer, String> noDuplicateKeyMap = new HashMap<>();
		        noDuplicateKeyMap.put(1, "one");
		        noDuplicateKeyMap.put(2, "two");
		        noDuplicateKeyMap.put(3, "three");

		        System.out.println("Original map: " + noDuplicateKeyMap);

		        try {
		            noDuplicateKeyMap.put(2, "another two");
		        } catch (IllegalArgumentException e) {
		            System.out.println("Duplicate key found: " + e.getMessage());
		        }

		        System.out.println("Modified map: " + noDuplicateKeyMap);
		    
	}
	
	
	public static class ahoj{
		
	}
	

}


