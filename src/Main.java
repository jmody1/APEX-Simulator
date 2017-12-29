import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	
	static String fileName = "";
	
	static Map<Integer, String> instWithNo = new HashMap<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		fileName = args[0];
		
		int noOfCyclesToSimulate =0;
		Simulator simulate = new Simulator();
		Scanner scanner = new Scanner(System.in);
		try{
			while(true){
				options();
				int option = scanner.nextInt();
				switch(option){
				
				case 1: simulate.freePipeline();
						System.out.println("Initializing Program Counter to 4000");
						break;
						
				case 2: System.out.println("Enter the Number of Cycles");
						noOfCyclesToSimulate = scanner.nextInt();
						simulate.simulateCycles(noOfCyclesToSimulate);
						break;
				
				case 3: simulate.display();
						break;
						
				case 4: System.out.println("Exit");
				        scanner.close();
				        System.exit(0);
				       
				default: System.out.println("Please enter a valid option");
						 break;
						
				}
			}
		} catch(Exception e){
			System.out.println("\n");
		}
	
	
	}
		
		private static void options(){
			
			try{
				System.out.println("\tSimulator for APEX with In-Order Issue");
				System.out.println("\t1. Initialize");
				System.out.println("\t2. Simulate <Number of Cycles>");
				System.out.println("\t3. Display");
				System.out.println("\t4. Exit");
				System.out.println("Please choose one of the above options:");
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}
	

}
