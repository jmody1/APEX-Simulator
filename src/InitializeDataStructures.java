import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class InitializeDataStructures extends Main{

	public static Map<Integer, Instruction> populateInstructionsInMap() {
		Map<Integer, Instruction> instructions = new HashMap<>();
		Map<Integer,String> instWithAddress = new HashMap<>();
		int instNum =0;
	    
		String fileName = Main.fileName;

		File file = new File(fileName);
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			int programCounter = 4000;
			String line;
			try {
				while ((line = br.readLine()) != null) {
					Instruction instruction = new Instruction();
					instruction.setInstruction(line);
					instruction.setAddress(programCounter);
					instructions.put(programCounter, instruction);
					instWithAddress.put(programCounter, instruction.getInstruction());
					instWithNo.put(programCounter, "I"+instNum);
					System.out.println(programCounter+" : ("+instWithNo.get(programCounter)+")"+instWithAddress.get(programCounter));
					programCounter = programCounter+4;
					instNum = instNum+1;
				}
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return instructions;
	}
	
	public static Map<String, Integer> initializeArchRegisters(){
		Map<String, Integer> archRegisterFile = new HashMap<>();
		for(int i=0; i<=15; i++){
			archRegisterFile.put("R" + i, 0);
		}
		return archRegisterFile;
		
	}
	
	public static int[] initializeMemory() {
		int[] memory = new int[1000];
		for (int i = 0; i < 999; i++) {
			memory[i] = 1111;
		}
		return memory;
	}
	
	public static Map<Integer, Instruction> initAllInstructions (Map<Integer, Instruction> instruction1, Map<String, Integer> archRegister){
		int counter = 4000;
		Map<Integer, Instruction> instructionMap = new HashMap<>();
		for(int i=0; i<instruction1.size(); i++){
			String instruction = instruction1.get(counter).getInstruction();
			String instructionFields[] = instruction.split(", ");
			Instruction inputInstruction = new Instruction();
			if(instructionFields[0].equals("ADD")
					|| instructionFields[0].equals("SUB")
					|| instructionFields[0].equals("MUL")
					|| instructionFields[0].equals("DIV")){
				
				inputInstruction.setInstructionType(instructionFields[0]);
				
				Operand destination = new Operand();
				destination.setOperandName(instructionFields[1]);
				inputInstruction.setDestination(destination);
				
				if (instructionFields[2].indexOf("#") < 0) {
					Operand source1 = new Operand();
					source1.setOperandName(instructionFields[2]);
					source1.setOperandValue(archRegister.get(instructionFields[2]));
					inputInstruction.setSource1(source1);
				} else {
					Operand source1 = new Operand();
					source1.setOperandName("literal");
					source1.setOperandValue(Integer.parseInt(instructionFields[2].substring(1)));
					inputInstruction.setSource1(source1);
				}

				if (instructionFields[3].indexOf("#") < 0) {
					Operand source2 = new Operand();
					source2.setOperandName(instructionFields[3]);
					source2.setOperandValue(archRegister.get(instructionFields[3]));
					inputInstruction.setSource2(source2);
				} else {
					Operand source2 = new Operand();
					source2.setOperandName("literal");
					source2.setOperandValue(Integer.parseInt(instructionFields[3].substring(1)));
					inputInstruction.setSource2(source2);
				}
				}else if (instructionFields[0].equals("LOAD")) {				
					inputInstruction.setInstructionType(instructionFields[0]);
					Operand destination = new Operand();
					destination.setOperandName(instructionFields[1]);
					inputInstruction.setDestination(destination);

					Operand source1 = new Operand();
					source1.setOperandName(instructionFields[2]);
					source1.setOperandValue(archRegister.get(instructionFields[2]));
					inputInstruction.setSource1(source1);

					Operand source2 = new Operand();
					source2.setOperandName("literal");
					source2.setOperandValue(Integer.parseInt(instructionFields[3].substring(1)));
					inputInstruction.setSource2(source2);
					
				} else if (instructionFields[0].equals("STORE")) {
					inputInstruction.setInstructionType(instructionFields[0]);

					Operand source1 = new Operand();
					source1.setOperandName(instructionFields[1]);
					source1.setOperandValue(archRegister.get(instructionFields[1]));
					inputInstruction.setSource1(source1);

					Operand source2 = new Operand();
					source2.setOperandName(instructionFields[2]);
					source2.setOperandValue(archRegister.get(instructionFields[2]));
					inputInstruction.setSource2(source2);
	
					Operand literal = new Operand();
					literal.setOperandName("literal");
					literal.setOperandValue(Integer.parseInt(instructionFields[3].substring(1)));
					inputInstruction.setLiteral(literal);
				} else if (instructionFields[0].equals("MOVC")) {
					inputInstruction.setInstructionType(instructionFields[0]);

					Operand destination = new Operand();
					destination.setOperandName(instructionFields[1]);
					inputInstruction.setDestination(destination);

					Operand source1 = new Operand();
					source1.setOperandName("literal");
					source1.setOperandValue(Integer.parseInt(instructionFields[2].substring(1)));
					inputInstruction.setSource1(source1);				
			   } else if(instructionFields[0].equals("BNZ")){
					//BNZ #-16
					inputInstruction.setInstructionType(instructionFields[0]);
					
					Operand source1 = new Operand();
					source1.setOperandName("literal");
					source1.setOperandValue(Integer.parseInt(instructionFields[1].substring(1)));
					inputInstruction.setSource1(source1);
				}
				instructionMap.put(counter, inputInstruction);
				counter= counter+4;
			}
			return instructionMap;
			
			}

}
	
	
	
	
	
				
		

	


