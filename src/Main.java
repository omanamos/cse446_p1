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
	public static final int[][] VALUES = {{-6, -3, 0, 6},
										  {-6, -3, 0, 6},
										  {-1, 0, 1, 2},
										  {-4, 0, 4},
										  {-8, 0, 8},
										  {-12, -2, 10}};
	public static final HashMap<String, HashMap<String, Integer>> ORDER_MAP	= buildOrders();
	
	public static void main(String[] args){
		count3();
	}
	
	public static void count3(){
		try{
			double sum = 0;
			double total = 0;
			Scanner s = new Scanner(new File("car.test"));
			while(s.hasNextLine()){
				String fullLine = s.nextLine();
				String[] line = fullLine.split(",");
				int lvl = getLvl(line[line.length - 1]);
				int curLvl = 3;
				
				if(line[SAFETY].equals("med")){
					curLvl--;
				}else if(line[SAFETY].equals("low")){
					curLvl -= 3;
				}
				
				/*if(line[LUG_BOOT].equals("med") && line[DOORS].equals("2"))
					curLvl--;*/
				
				if(line[PERSONS].equals("2"))
					curLvl = 0;
				
				if(line[LUG_BOOT].equals("small"))
					curLvl--;
				
				if((line[MAINT].equals("vhigh") || line[MAINT].equals("high")) && (line[BUYING].equals("vhigh") ||  line[BUYING].equals("high")) && !(line[MAINT].equals("high") && line[BUYING].equals("high")))
					curLvl = 0;
				else if((line[MAINT].equals("vhigh") || line[MAINT].equals("high")) || (line[BUYING].equals("vhigh") ||  line[BUYING].equals("high")))
					curLvl -= 2;
				else if((line[MAINT].equals("med") && line[BUYING].equals("med")))
					curLvl--;
				
				if(curLvl < 0)
					curLvl = 0;
				
				
				if(curLvl == lvl)
					sum++;
				else{
					System.out.println(fullLine + " " + curLvl);
					
				}
				total++;
			}
			System.out.println(sum / total);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static int getLvl(String acc){
		if(acc.equals("unacc"))
			return 0;
		else if(acc.equals("acc"))
			return 1;
		else if(acc.equals("good"))
			return 2;
		else //if(acc.equals("vgood"))
			return 3;
	}
	
	public static void count2(){
		try{
			Scanner s = new Scanner(new File("car.train"));
			while(s.hasNextLine()){
				String fullLine = s.nextLine();
				String[] line = fullLine.split(",");
				String isAcc = line[line.length - 1];
				int score = 0;
				
				for(int i = 0; i < line.length - 1; i++){
					score += ORDER_MAP.get(ATTR_TO_S[i]).get(line[i]);
				}
				System.out.println(isAcc + " " + score + " " + fullLine);
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
				String fullLine = s.nextLine();
				String[] line = fullLine.split(",");
				String isAcc = line[line.length - 1];
				
				if(line[SAFETY].equals("high") && isAcc.equals("unacc")){
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
				order.put(ORDERS[i][n], VALUES[i][n]);
			}
			rtn.put(ATTR_TO_S[i], order);
		}
		return rtn;
	}
}
