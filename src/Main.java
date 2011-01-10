import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public class Main {
	
	public static final int BUYING = 0;
	public static final int MAINT = 1;
	public static final int DOORS = 2;
	public static final int PERSONS = 3;
	public static final int LUG_BOOT = 4;
	public static final int SAFETY = 5;
	public static final String[] ATTR_TO_S = {"buying", "maint", "doors", "persons", "lug_boot", "safety"};
	
	public static final String[][] ORDERS = {{"vhigh", "high", "med", "low"},
											 {"vhigh", "high", "med", "low"},
											 {"2", "3", "4", "5more"},
											 {"2", "4", "more"},
											 {"small", "med", "big"},
											 {"low", "med", "high"}};
	public static final HashMap<String, HashMap<String, Integer>> ORDER_MAP	= buildOrders();
	
	public static void main(String[] args){
		count2();
	}
	
	public static void count2(){
		try{
			Scanner s = new Scanner(new File("car.train"));
			while(s.hasNextLine()){
				String[] line = s.nextLine().split(",");
				String isAcc = line[line.length - 1];
				int score = 0;
				
				for(int i = 0; i < line.length - 1; i++){
					score += ORDER_MAP.get(ATTR_TO_S[i]).get(line[i]);
				}
				System.out.println(isAcc + " " + score);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void count1(){
		try {
			Scanner s = new Scanner(new File("car.train"));
			HashMap<String, HashMap<String, HashMap<String, Integer>>> graph = new HashMap<String, HashMap<String, HashMap<String, Integer>>>();
			while(s.hasNextLine()){
				String[] line = s.nextLine().split(",");
				String isAcc = line[line.length - 1];
				
				if(!graph.containsKey(isAcc)){
					graph.put(isAcc, new HashMap<String, HashMap<String, Integer>>());
				}
				HashMap<String, HashMap<String, Integer>> tmp = graph.get(isAcc);
				for(int i = 0; i < line.length - 1; i++){
					String attr = ATTR_TO_S[i];
					if(!tmp.containsKey(attr)){
						tmp.put(attr, new HashMap<String, Integer>());
					}
					
					HashMap<String, Integer> cnts = tmp.get(attr);
					if(!cnts.containsKey(line[i])){
						cnts.put(line[i], 0);
					}
					cnts.put(line[i], cnts.get(line[i]) + 1);
				}
			}
			System.out.println(graph);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static HashMap<String, HashMap<String, Integer>> buildOrders(){
		HashMap<String, HashMap<String, Integer>> rtn = new HashMap<String, HashMap<String, Integer>>();
		for(int i = 0; i < ORDERS.length; i++){
			HashMap<String, Integer> order = new HashMap<String, Integer>();
			for(int n = 0; n < ORDERS[i].length; n++){
				order.put(ORDERS[i][n], n);
			}
			rtn.put(ATTR_TO_S[i], order);
		}
		return rtn;
	}
}
