import java.util.*;


public class Simulator extends Main{

	private Map<Integer, Instruction> instructions = new HashMap<>();
	private Map<String, Integer> archRegisterFile = new HashMap<>(); 
	private int programCounter =4000;
	private List<Instruction> fetchStageList = new ArrayList<>();
	private List<Instruction> decodeStageList = new ArrayList<>();
	private List<Instruction> aluStageList = new ArrayList<>();
	private List<Instruction> mul1StageList = new ArrayList<>();
	private List<Instruction> mul2StageList = new ArrayList<>();
	private List<Instruction> div1StageList = new ArrayList<>();
	private List<Instruction> div2StageList = new ArrayList<>();
	private List<Instruction> div3StageList = new ArrayList<>();
	private List<Instruction> div4StageList = new ArrayList<>();
	private List<Instruction> memStageList = new ArrayList<>();
	private List<Instruction> writeBackStageList = new ArrayList<>();
	
	private Boolean isDecodeStageStalled = false;
	private Boolean stallFlag = false;
	private Boolean instDependent = false;
	private Map<Integer, Instruction> allInstructions = new HashMap<>();
	private int[] memory = new int[1000];
	private String instructioninWB;
	private Map<String, Boolean> phyRegister = new HashMap<>();{
		for(int i=0; i<16; i++){
			phyRegister.put("R"+i, true);
			}
		}
	private Map<String, Boolean> dependentRegister = new HashMap<>();{
		for(int i=0; i<16; i++){
			dependentRegister.put("R"+i, false);
			}
		}
	private Map <String, Boolean> stallDone = new HashMap<>();{
		for(int i=0; i<16; i++){
			stallDone.put("R"+i, false);
			}
	}
	private Boolean halt = false;
	
	private static int bzpc,pswpc, zeroflag=0;
	private int depcount;
	
	
	public void freePipeline(){
		archRegisterFile.clear();
		fetchStageList.clear();
		decodeStageList.clear();
		aluStageList.clear();
		memStageList.clear();
		writeBackStageList.clear();
	}
	public void simulateCycles(int totalCycles){
		instructions = InitializeDataStructures.populateInstructionsInMap();
		archRegisterFile = InitializeDataStructures.initializeArchRegisters();
		memory = InitializeDataStructures.initializeMemory();
		allInstructions = InitializeDataStructures.initAllInstructions(instructions, archRegisterFile);
		
		for(int i=0; i< totalCycles;i++){
			System.out.println("\nCycle : "+(i+1));
			if(!stallFlag){
			if(!halt){
			if(!isDecodeStageStalled){
				if(fetchStageList.size()==0){
					if(instructions.get(programCounter) != null){
						fetchStageList.add(instructions.get(programCounter));
						programCounter = programCounter+4;
						fetchStageExecution();
											
					}
				} else {
					if (instructions.get(programCounter) != null) {
						fetchStageList.add(instructions.get(programCounter));
						programCounter = programCounter + 4;
					}
					if (fetchStageList.size() > 0) {
						decodeStageList.add(fetchStageList.get(0));
						fetchStageList.remove(0);
						fetchStageExecution();
					}
				}
			}
			else{
				if (fetchStageList.size() > 0)
				{
					
					int adreess= fetchStageList.get(0).getAddress();
					String Number= instWithNo.get(adreess);
					
					System.out.println("Fetch Stage : ("+Number+") "+ fetchStageList.get(0).toString());
					//System.out.println("Fetch Stage : "+ fetchStageList.get(0).toString());
				}
				else
				{
					System.out.println("Fetch Stage : Empty");
				}
			}
			}
			else{
				if (fetchStageList.size() > 0)
				{
					
					int adreess= fetchStageList.get(0).getAddress();
					String Number= instWithNo.get(adreess);
					
					System.out.println("Fetch Stage : ("+Number+") "+ fetchStageList.get(0).toString());
					//System.out.println("Fetch Stage : "+ fetchStageList.get(0).toString());
				}
				else
				{
					System.out.println("Fetch Stage : Empty");
				}
			}
			}
			else
			{
				if (fetchStageList.size() > 0)
				{
					
					int adreess= fetchStageList.get(0).getAddress();
					String Number= instWithNo.get(adreess);
					
					System.out.println("Fetch Stage : ("+Number+") "+ fetchStageList.get(0).toString());
					//System.out.println("Fetch Stage : "+ fetchStageList.get(0).toString());
				}
				else
				{
					System.out.println("Fetch Stage : Empty");
				}
			}
			if(!stallFlag){
			if(!isDecodeStageStalled){
			if(decodeStageList.size()==1 && !decodeStageList.get(0).getIsDecoded()){
				decodeStageExecution();
				////////continue;
			}
			else if(decodeStageList.size()>0){
					if(decodeStageList.get(0).getInstructionType().equals("MUL")){
						mul1StageList.add(decodeStageList.get(0));
					}
					else if(decodeStageList.get(0).getInstructionType().equals("DIV")){
						div1StageList.add(decodeStageList.get(0));
					}
					else{
					aluStageList.add(decodeStageList.get(0));
					}
					decodeStageList.remove(0);
					decodeStageExecution();
					
				}
				else if(decodeStageList.size()>0){
					decodeStageList.get(0).setIsStalled(true);
				}
				else
				{
					System.out.println("Decode Stage : Empty");
				}
				}
			else
			{
				if(decodeStageList.size()>0)
				{
					int adreess= decodeStageList.get(0).getAddress();
					String Number= instWithNo.get(adreess);
					
					System.out.println("Decode Stage : ("+Number+") "+ decodeStageList.get(0).toString());
					//System.out.println("Decode Stage : "+ decodeStageList.get(0).toString());
				}
				else
				{
					System.out.println("Decode Stage : Empty");
				}
			}
			
			}
			else
			{
				if(decodeStageList.size()>0)
				{
					int adreess= decodeStageList.get(0).getAddress();
					String Number= instWithNo.get(adreess);
					
					System.out.println("Decode Stage : ("+Number+") "+ decodeStageList.get(0).toString());
					//System.out.println("Decode Stage : "+ decodeStageList.get(0).toString());
				}
				else
				{
					System.out.println("Decode Stage : Empty");
				}
			}
			
			
			
			if(aluStageList.size()==1 && !aluStageList.get(0).getIsALUExecuted()){
				aluStageExecution();
				if(memStageList.size()== 0 && writeBackStageList.size()== 0){
				////////continue;
				}
			}
			
							
			else if(aluStageList.size()>0){
					if(mul2StageList.size()>0){
						//isDecodeStageStalled = true;
						memStageList.add(mul2StageList.get(0));
					}
					else if(div4StageList.size()>0){
						memStageList.add(div4StageList.get(0));
					}
					else{
						memStageList.add(aluStageList.get(0));
						aluStageList.remove(0);
						aluStageExecution();
						
					}
					
				}
			else
			{
				if(aluStageList.size()>0)
				{
					int adreess= aluStageList.get(0).getAddress();
					String Number= instWithNo.get(adreess);
					
					System.out.println("ALU Stage : ("+Number+") "+ aluStageList.get(0).toString());
					//System.out.println("ALU Stage : "+ aluStageList.get(0).toString());
				}
				else
				{
					System.out.println("ALU Stage : Empty");
				}
			}
				
//				
//				if(aluStageList.size()==0 && memStageList.size()==0 && writeBackStageList.size()==0){
//					if(mul2StageList.size()>1){
//						memStageList.add(mul2StageList.get(0));
//						//mul2StageList.add(mul1StageList.get(0));
//					    mul2StageList.remove(0);
//					}					
//				} 
			
			if(mul1StageList.size()==1 && !mul1StageList.get(0).getIsMul1Executed()){
				mul1StageExecution();
//				mul2StageList.add(mul1StageList.get(0));
//				mul1StageList.remove(0);
				if(mul2StageList.size()==0 && memStageList.size()==0 && writeBackStageList.size()==0){
					////////continue;
				}
			}
			
			else if(mul1StageList.size()>0){
				mul2StageList.add(mul1StageList.get(0));
				mul1StageList.remove(0);
				mul1StageExecution();
			}
			else
			{
				if(mul1StageList.size()>0)
				{
					int adreess= mul1StageList.get(0).getAddress();
					String Number= instWithNo.get(adreess);
					
					System.out.println("MUL1 Stage : ("+Number+") "+ mul1StageList.get(0).toString());
					//System.out.println("MUL1 Stage : "+ mul1StageList.get(0).toString());
				}
				else
				{
					System.out.println("MUL1 Stage : Empty");
				}
			}
			
			if(mul2StageList.size()==1 && !mul2StageList.get(0).getIsMul2Executed()){
				mul2StageExecution();
				//mul1StageList.remove(0);
				if(memStageList.size()==0 && writeBackStageList.size()==0){
					////////continue;
				}
			}
			
			else if(mul2StageList.size()>0){
				memStageList.add(mul2StageList.get(0));
				mul2StageList.remove(0);
				mul2StageExecution();
			}
			else
			{
				System.out.println("MUL2 Stage : Empty");
			}
			
			if(div1StageList.size()==1 && !div1StageList.get(0).getIsDiv1StageExecuted()){
				div1StageExecution();
				if(div2StageList.size()==0 && div3StageList.size()==0 && div4StageList.size()==0){
					////////continue;
				}
			}
			else if(div1StageList.size()>0){
				div2StageList.add(div1StageList.get(0));
				div1StageList.remove(0);
				div1StageExecution();
			}
			else
			{
				System.out.println("DIV1 Stage : Empty");
			}
			if(div2StageList.size()==1 && !div2StageList.get(0).getIsDiv2StageExecuted()){
				div2StageExecution();
				if(div3StageList.size()==0 && div4StageList.size()==0 && memStageList.size()==0){
					//////continue;
				}
			}
			else if(div2StageList.size()>0){
				div3StageList.add(div2StageList.get(0));
				div2StageList.remove(0);
				div2StageExecution();
			}
			else
			{
				System.out.println("DIV2 Stage : Empty");
			}
			if(div3StageList.size()==1 && !div3StageList.get(0).getIsDiv3StageExecuted()){
				div3StageExecution();
				if(div4StageList.size()==0 && memStageList.size()==0 && writeBackStageList.size()==0){
					//////continue;
				}
			}
			else if(div3StageList.size()>0){
				div4StageList.add(div3StageList.get(0));
				div3StageList.remove(0);
				div3StageExecution();
			}
			else
			{
				System.out.println("DIV3 Stage : Empty");
			}
			if(div4StageList.size()==1 && !div4StageList.get(0).getIsDiv4StageExecuted()){
				div4StageExecution();
				if(memStageList.size()==0 && writeBackStageList.size()==0){
					//////continue;
				}
			}
			else if(div4StageList.size()>0){
				memStageList.add(div4StageList.get(0));
				div4StageList.remove(0);
				div4StageExecution();
			}
			else
			{
				System.out.println("DIV4 Stage : Empty");
			}
			
			if(memStageList.size()==1 && !memStageList.get(0).getIsMemExecuted()){
				memStageExecution();
//				if(writeBackStageList.size()==0){
//					writeBackStageList.add(memStageList.get(0));
//					memStageList.remove(0);
//				}
			}else if(memStageList.size()>0){
				writeBackStageList.add(memStageList.get(0));
				memStageList.remove(0);
				memStageExecution();
			}
			else
			{
				System.out.println("Mem Stage : Empty");
			}
			if(writeBackStageList.size()==1){
				writeBackStageExecution();
				writeBackStageList.remove(0);
				
			}
			else
			{
				System.out.println("WriteBack Stage : Empty");
			}
	}
	}
			
	
	public void display(){
		System.out.println("Below are the stages after the end of last cycle:");
		if(fetchStageList.get(0)!= null){
			System.out.println("Fetch Stage : "+fetchStageList.get(0).getInstruction());
		}
		if(decodeStageList.get(0)!= null){
			System.out.println("Decode Stage : "+decodeStageList.get(0).getInstruction());
		}
		if(aluStageList.get(0)!= null){
			System.out.println("ALU Stage : "+aluStageList.get(0).getInstruction());
		}
		if(mul1StageList.get(0)!= null){
			System.out.println("Mul1 Stage : "+mul1StageList.get(0).getInstruction());
		}
		if(mul2StageList.get(0)!= null){
			System.out.println("Mul2 Stage : "+mul2StageList.get(0).getInstruction());
		}
		if(memStageList.get(0)!= null){
			System.out.println("Memory Stage : "+memStageList.get(0).getInstruction());
			System.out.println("Write Back Stage : "+instructioninWB);
		}
		
		for(int i=0; i<=15; i++){
			System.out.println("R"+i +"\t"+archRegisterFile.get("R" + i));
		}
	}
	
	
	private void fetchStageExecution(){
		if (fetchStageList.size() > 0)
		{
			int adreess= fetchStageList.get(0).getAddress();
			String Number= instWithNo.get(adreess);
			
			System.out.println("Fetch Stage : ("+Number+") "+ fetchStageList.get(0).toString());
		}
		else
		{
			System.out.println("Fetch Stage : Empty");
		}
	}
	
	
	private void decodeStageExecution(){
		if (decodeStageList.size() > 0) {
			//System.out.println("Instuction in decode stage(Before decode) -->" + decodeStageList.get(0).toString());

			String instruction = decodeStageList.get(0).getInstruction();
			String instructionFields[] = instruction.split("[,]");

			/*
			 * Check for the type of instructions ADD dest src1 src2 SUB dest
			 * src1 src2 MUL destination src1 src2
			 */
			if (instructionFields[0].equals("ADD")
					|| instructionFields[0].equals("SUB")
					|| instructionFields[0].equals("MUL")
					|| instructionFields[0].equals("DIV")) {

				decodeStageList.get(0).setInstructionType(instructionFields[0]);

				Operand destination = new Operand();
				destination.setOperandName(instructionFields[1]);
				decodeStageList.get(0).setDestination(destination);
				phyRegister.put(instructionFields[1], false);

				if (instructionFields[2].indexOf("#") < 0) {
					Operand source1 = new Operand();
					source1.setOperandName(instructionFields[2]);
					source1.setOperandValue(archRegisterFile.get(instructionFields[2]));
					decodeStageList.get(0).setSource1(source1);
					if(phyRegister.get(instructionFields[2]).equals(false)){
						setIsDecodeStageStalled(true);
						stallDone.put(instructionFields[2], true);
						instDependent = true;
						depcount =1;
						dependentRegister.put(decodeStageList.get(0).getSource1().getOperandName(), true);
						
					}
				} else {
					Operand source1 = new Operand();
					source1.setOperandName("literal");
					source1.setOperandValue(Integer.parseInt(instructionFields[3].substring(1)));
					decodeStageList.get(0).setSource1(source1);
				}

				if (instructionFields[3].indexOf("#") < 0) {
					Operand source2 = new Operand();
					source2.setOperandName(instructionFields[3]);
					source2.setOperandValue(archRegisterFile.get(instructionFields[3]));
					decodeStageList.get(0).setSource2(source2);
					if(phyRegister.get(instructionFields[3]).equals(false)){
						setIsDecodeStageStalled(true);
						instDependent = true;
						if(depcount ==1){
							if(decodeStageList.get(0).getSource1().getOperandName().equals(decodeStageList.get(0).getSource2().getOperandName()))
									
							{
								depcount = 1;
								dependentRegister.put(decodeStageList.get(0).getSource2().getOperandName(), true);
							}
							else{
							depcount =2;
							dependentRegister.put(decodeStageList.get(0).getSource2().getOperandName(), true);
							}
							
						
					}
						else{
							depcount = 1;
							dependentRegister.put(decodeStageList.get(0).getSource2().getOperandName(), true);
						}
				}} else {
					Operand source2 = new Operand();
					source2.setOperandName("literal");
					source2.setOperandValue(Integer.parseInt(instructionFields[3].substring(1)));
					decodeStageList.get(0).setSource2(source2);
				}
			} else if (instructionFields[0].equals("MOVC")) {
				// MOVC destination literal
				decodeStageList.get(0).setInstructionType(instructionFields[0]);

				Operand destination = new Operand();
				destination.setOperandName(instructionFields[1]);
				decodeStageList.get(0).setDestination(destination);
				phyRegister.put(instructionFields[1], false);

				Operand source1 = new Operand();
				source1.setOperandName("literal");
				source1.setOperandValue(Integer.parseInt(instructionFields[2].substring(1)));
				decodeStageList.get(0).setSource1(source1);
			} else if (instructionFields[0].equals("LOAD")) {
				// LOAD destination src1 literal
				decodeStageList.get(0).setInstructionType(instructionFields[0]);
				

				Operand destination = new Operand();
				destination.setOperandName(instructionFields[1]);
				decodeStageList.get(0).setDestination(destination);
				phyRegister.put(instructionFields[1], false);

				Operand source1 = new Operand();
				source1.setOperandName(instructionFields[2]);
				source1.setOperandValue(archRegisterFile.get(instructionFields[2]));
				decodeStageList.get(0).setSource1(source1);
				if(phyRegister.get(instructionFields[2]).equals(false)){
					setIsDecodeStageStalled(true);
					stallDone.put(instructionFields[2], true);
					instDependent = true;
					depcount =1;
					dependentRegister.put(decodeStageList.get(0).getSource1().getOperandName(), true);
					
				}
				

				Operand source2 = new Operand();
				source2.setOperandName("literal");
				source2.setOperandValue(Integer.parseInt(instructionFields[3].substring(1)));
				decodeStageList.get(0).setSource2(source2);
			} 
			else if (instructionFields[0].equals("STORE")) {
				// STORE src1 src2 literal
				decodeStageList.get(0).setInstructionType(instructionFields[0]);

				Operand source1 = new Operand();
				source1.setOperandName(instructionFields[1]);
				source1.setOperandValue(archRegisterFile.get(instructionFields[1]));
				decodeStageList.get(0).setSource1(source1);
				if(phyRegister.get(instructionFields[1]).equals(false)){
					setIsDecodeStageStalled(true);
					stallDone.put(instructionFields[1], true);
					instDependent= true;
					depcount =1;
					dependentRegister.put(decodeStageList.get(0).getSource1().getOperandName(), true);
				}

				Operand source2 = new Operand();
				source2.setOperandName(instructionFields[2]);
				source2.setOperandValue(archRegisterFile.get(instructionFields[2]));
				decodeStageList.get(0).setSource2(source2);
				if(phyRegister.get(instructionFields[2]).equals(false)){
					setIsDecodeStageStalled(true);
					instDependent = true;
					if(depcount ==1){
						if(phyRegister.containsKey(instructionFields[2].equals(phyRegister.containsKey(instructionFields[1])))){
							depcount = 1;
							dependentRegister.put(decodeStageList.get(0).getSource2().getOperandName(), true);
						}
						else{
						depcount =2;
						dependentRegister.put(decodeStageList.get(0).getSource2().getOperandName(), true);
						}
						
					
				}
					else{
						depcount = 1;
						dependentRegister.put(decodeStageList.get(0).getSource2().getOperandName(), true);
					}
				}
			

				Operand literal = new Operand();
				literal.setOperandName("literal");
				literal.setOperandValue(Integer.parseInt(instructionFields[3].substring(1)));
				decodeStageList.get(0).setLiteral(literal);
			} else if (instructionFields[0].equals("BNZ")
					|| instructionFields[0].equals("BZ")) {
				// BNZ #20
				decodeStageList.get(0).setInstructionType(instructionFields[0]);

				Operand literal = new Operand();
				literal.setOperandName("literal");
				literal.setOperandValue(Integer.parseInt(instructionFields[1].substring(1)));
				decodeStageList.get(0).setSource1(literal);
				bzpc = decodeStageList.get(0).getAddress();
				if(mul1StageList.size()>0){
					mul1StageList.get(0).setPsw(true);
					stallFlag = true;

				}
				else if(aluStageList.get(0).getInstructionType().equals("ADD")|| aluStageList.get(0).getInstructionType().equals("SUB")){
					if(aluStageList.size()==1){
						aluStageList.get(0).setPsw(true);
						stallFlag = true;
					}
					else{
						aluStageList.get(1).setPsw(true);
						//stallDone.put(instructionFields[1], true);
						stallFlag = true;
					}
				}
				else if(memStageList.get(0).getInstructionType().equals("ADD")|| memStageList.get(0).getInstructionType().equals("SUB")){
					pswpc = memStageList.get(0).getAddress();
					memStageList.get(0).setPsw(true);
					//stallDone.put(instructionFields[1], true);
					stallFlag = true;
				}

			}
			else if (instructionFields[0].equals("HALT"))
			{
				decodeStageList.get(0).setInstructionType(instructionFields[0]);
				halt=true;
				System.out.println("Decode Stage : "+ decodeStageList.get(0).toString());
				//aluStageList.add(decodeStageList.get(0));
				fetchStageList.clear();
				//decodeStageList.clear();
			}
			else if(instructionFields[0].equals("JUMP"))
			{
				decodeStageList.get(0).setInstructionType(instructionFields[0]);
//				Operand destination = new Operand();
//				destination.setOperandName(instructionFields[1]);
//				decodeStageList.get(0).setDestination(destination);
//				phyRegister.put(instructionFields[1], false);

				if (instructionFields[1].indexOf("#") < 0) 
				{
					Operand source1 = new Operand();
					source1.setOperandName(instructionFields[1]);
					source1.setOperandValue(archRegisterFile.get(instructionFields[1]));
					decodeStageList.get(0).setSource1(source1);
					if(phyRegister.get(instructionFields[1]).equals(false))
					{
						setIsDecodeStageStalled(true);
						instDependent = true;
						stallDone.put(instructionFields[1], true);
						depcount =1;
						dependentRegister.put(decodeStageList.get(0).getSource1().getOperandName(), true);
					}
					
				} 
				else 
				{
					Operand source1 = new Operand();
					source1.setOperandName("literal");
					source1.setOperandValue(Integer.parseInt(instructionFields[3].substring(1)));
					decodeStageList.get(0).setSource1(source1);
				}

				if (instructionFields[2].indexOf("#") < 0) {
					Operand source2 = new Operand();
					source2.setOperandName(instructionFields[2]);
					source2.setOperandValue(archRegisterFile.get(instructionFields[2]));
					decodeStageList.get(0).setSource2(source2);
					if(phyRegister.get(instructionFields[2]).equals(false)){
						setIsDecodeStageStalled(true);
						instDependent = true;
					}
				} else {
					Operand source2 = new Operand();
					source2.setOperandName("literal");
					source2.setOperandValue(Integer.parseInt(instructionFields[2].substring(1)));
					decodeStageList.get(0).setSource2(source2);
				}
			}
			
			else if(instructionFields[0].equals("JAL"))
			{
				decodeStageList.get(0).setInstructionType(instructionFields[0]);
				if (instructionFields[1].indexOf("#") < 0) 
				{
					Operand source1 = new Operand();
					source1.setOperandName(instructionFields[1]);
					source1.setOperandValue(archRegisterFile.get(instructionFields[1]));
					decodeStageList.get(0).setSource1(source1);
					if(phyRegister.get(instructionFields[1]).equals(false))
					{
						setIsDecodeStageStalled(true);
						instDependent = true;
						stallDone.put(instructionFields[1], true);
						depcount =1;
						dependentRegister.put(decodeStageList.get(0).getSource1().getOperandName(), true);
					}
					
				} 
				else 
				{
					Operand source1 = new Operand();
					source1.setOperandName("literal");
					source1.setOperandValue(Integer.parseInt(instructionFields[3].substring(1)));
					decodeStageList.get(0).setSource1(source1);
				}

				if (instructionFields[2].indexOf("#") < 0) {
					Operand source2 = new Operand();
					source2.setOperandName(instructionFields[2]);
					source2.setOperandValue(archRegisterFile.get(instructionFields[2]));
					decodeStageList.get(0).setSource2(source2);
					if(phyRegister.get(instructionFields[2]).equals(false)){
						setIsDecodeStageStalled(true);
						instDependent = true;
					}
				} else {
					Operand source2 = new Operand();
					source2.setOperandName("literal");
					source2.setOperandValue(Integer.parseInt(instructionFields[2].substring(1)));
					decodeStageList.get(0).setSource2(source2);
				}
				
			}
			
//			else if(instructionFields.length>1 && instructionFields.length<=2){
//				if(instructionFields[0].equals("BZ")|| instructionFields[0].equals("BNZ")){
//					Operand destination = new Operand();
//					destination.setOperandName(instructionFields[1]);
//					decodeStageList.get(0).setDestination(destination);
//				}
//			}
			if (decodeStageList.size()>0)
			{
			decodeStageList.get(0).setIsDecoded(true);
			int adreess= decodeStageList.get(0).getAddress();
			String Number= instWithNo.get(adreess);
			
			System.out.println("Decode Stage : ("+Number+") "+ decodeStageList.get(0).toString());
			//System.out.println("Decode Stage : "+ decodeStageList.get(0).toString());
			}
			else
			{
				System.out.println("Decode Stage : Empty");
			}
			
		}
	}
	
	
	public Boolean getIsDecodeStageStalled() {
		return isDecodeStageStalled;
	}
	public void setIsDecodeStageStalled(Boolean isDecodeStageStalled) {
		this.isDecodeStageStalled = isDecodeStageStalled;
	}
	private void aluStageExecution(){
		if(aluStageList.size()>0){
			Instruction aluInstruction = aluStageList.get(0);
			int source1 = 0, source2 = 0;
			if(aluInstruction.getInstructionType().equals("ADD")
			 ||aluInstruction.getInstructionType().equals("SUB")
			 ||aluInstruction.getInstructionType().equals("STORE"))
			{
				String instruction = aluStageList.get(0).getInstruction();
				String instructionFields[] = instruction.split("[,]");
				String source1name = aluInstruction.getSource1().getOperandName();
				if(!source1name.equals("literal"))
				{
					source1 = archRegisterFile.get(instructionFields[2]);
				}
				else
				{
					source1 = aluInstruction.getSource1().getOperandValue();
				}
				String source2name = aluInstruction.getSource2().getOperandName();
				if(!source2name.equals("literal"))
				{
					source2 = archRegisterFile.get(instructionFields[3]);
				}
				else
				{
					source2 = aluInstruction.getSource2().getOperandValue();
				}
//				source1 = aluInstruction.getSource1().getOperandValue();
//				source2 = aluInstruction.getSource2().getOperandValue();
			
				if(aluInstruction.getInstructionType().equals("ADD"))
				{
					int destination = source1 + source2;
					aluStageList.get(0).getDestination().setOperandValue(destination);
				}
				else if(aluInstruction.getInstructionType().equals("SUB"))
				{
					int destination = source1 - source2;
					aluStageList.get(0).getDestination().setOperandValue(destination);
				}
				else if(aluInstruction.getInstructionType().equals("LOAD"))
				{
					source1 = aluInstruction.getSource1().getOperandValue();
					int literal = aluInstruction.getLiteral().getOperandValue();
					int memoryAdd = source1 + literal;
					aluStageList.get(0).getDestination().setOperandValue(memoryAdd);
				}
				else if (aluInstruction.getInstructionType().equals("STORE"))
				{
					aluStageList.get(0).getSource1().setOperandValue(source1);
					int literal = aluInstruction.getLiteral().getOperandValue();
					int memoryAdd = source2 + literal;
					Operand destination = new Operand();
					destination.setOperandValue(memoryAdd);
					aluStageList.get(0).setDestination(destination);
				}
			}	
			
			else if(aluInstruction.getInstructionType().equals("MOVC"))
			{
					int literal = aluStageList.get(0).getSource1().getOperandValue();
					aluStageList.get(0).getDestination().setOperandValue(literal);
				}
			else if(aluInstruction.getInstructionType().equals("HALT"))
			{
				
			}
			else if(aluInstruction.getInstructionType().equals("JUMP"))
			{
				source1 = aluInstruction.getSource1().getOperandValue();
				int literal = aluInstruction.getSource2().getOperandValue();
				int destination = source1 + literal;
				programCounter = destination;
				fetchStageList.clear();
				decodeStageList.clear();
				
			}
			else if(aluInstruction.getInstructionType().equals("JAL")){
				source1 = aluInstruction.getSource1().getOperandValue();
				int literal = aluInstruction.getSource2().getOperandValue();
				int destination = source1 + literal;
				programCounter = destination;
				fetchStageList.clear();
				decodeStageList.clear();
			}
			else if(aluInstruction.getInstructionType().equals("BZ")){
				if(zeroflag ==0){
					programCounter = bzpc + aluStageList.get(0).getSource1().getOperandValue();
					fetchStageList.clear();
					decodeStageList.get(0).setIsDecoded(false);
					decodeStageList.clear();
					decodeStageList.removeAll(decodeStageList);
				}
			}
			else if(aluInstruction.getInstructionType().equals("BNZ")){
				if(zeroflag !=0){
					programCounter = bzpc + aluStageList.get(0).getSource1().getOperandValue();
					fetchStageList.clear();
					decodeStageList.get(0).setIsDecoded(false);
					decodeStageList.clear();
					decodeStageList.removeAll(decodeStageList);
				}
			}
			
				int adreess= aluStageList.get(0).getAddress();
				String Number= instWithNo.get(adreess);
				
				System.out.println("ALU Stage : ("+Number+") "+ aluStageList.get(0).toString());
			
				//System.out.println("ALU Stage : "+aluStageList.get(0).toString());
				aluStageList.get(0).setIsALUExecuted(true);
			}
			else
			{
				System.out.println("ALU Stage : Empty");
			}
		}
	
	private void mul1StageExecution(){
		if(mul1StageList.size()>0){
			Instruction aluInstruction = mul1StageList.get(0);
			int source1 = 0, source2 = 0;
			if(aluInstruction.getInstructionType().equals("MUL")){
				source1 = aluInstruction.getSource1().getOperandValue();
				source2 = aluInstruction.getSource2().getOperandValue();
				
				if(aluInstruction.getInstructionType().equals("MUL")){
					int destination = source1 * source2;
					mul1StageList.get(0).getDestination().setOperandValue(destination);
				}
				
				int adreess= mul1StageList.get(0).getAddress();
				String Number= instWithNo.get(adreess);
				
				System.out.println("MUL1 Stage : ("+Number+") "+ mul1StageList.get(0).toString());	
				//System.out.println("MUL1 Stage : "+mul1StageList.get(0).toString());
					mul1StageList.get(0).setIsMul1Executed(true);
				}
			}
		else
		{
			System.out.println("MUL1 Stage : Empty");
		}
	}
	
	private void mul2StageExecution(){
		if(mul2StageList.size()>0){
			int adreess= mul2StageList.get(0).getAddress();
			String Number= instWithNo.get(adreess);
			
			System.out.println("MUL2 Stage : ("+Number+") "+ mul2StageList.get(0).toString());
			//System.out.println("Mul2 Stage : "+mul2StageList.get(0).toString());
			mul2StageList.get(0).setIsMul2Executed(true);
			
		}
		else
		{
			System.out.println("MUL2 Stage : Empty");
		}
	}
	
	private void div1StageExecution(){
		if(div1StageList.size()>0){
			Instruction aluInstruction = div1StageList.get(0);
			int source1 = 0, source2 = 0;
			if(aluInstruction.getInstructionType().equals("DIV")){
				source1 = aluInstruction.getSource1().getOperandValue();
				source2 = aluInstruction.getSource2().getOperandValue();
				
				if(aluInstruction.getInstructionType().equals("DIV")){
					int destination = source1 / source2;
					div1StageList.get(0).getDestination().setOperandValue(destination);
				}
				int adreess= div1StageList.get(0).getAddress();
				String Number= instWithNo.get(adreess);
				
				System.out.println("DIV1 Stage : ("+Number+") "+ div1StageList.get(0).toString());	
				//System.out.println("DIV1 Stage : "+div1StageList.get(0).toString());
					div1StageList.get(0).setIsDiv1StageExecuted(true);
				}
			}
		else
		{
			System.out.println("DIV1 Stage : Empty");
		}
	}
	
	private void div2StageExecution(){
		if(div1StageList.size()>0 && div2StageList.size()==0){
			div2StageList.add(div1StageList.get(0));
			div2StageList.get(0).setIsDiv2StageExecuted(true);
			int adreess= div2StageList.get(0).getAddress();
			String Number= instWithNo.get(adreess);
			
			System.out.println("DIV2 Stage : ("+Number+") "+ div2StageList.get(0).toString());
			//System.out.println("DIV2 Stage : "+div2StageList.get(0).toString());
			
		}
		else if(div2StageList.size()==1)
		{
			div2StageList.get(0).setIsDiv2StageExecuted(true);
			int adreess= div2StageList.get(0).getAddress();
			String Number= instWithNo.get(adreess);
			
			System.out.println("DIV2 Stage : ("+Number+") "+ div2StageList.get(0).toString());
		}
		else
		{
			System.out.println("DIV2 Stage : Empty");
		}
	}
	private void div3StageExecution(){
		if(div2StageList.size()>0 && div3StageList.size()==0){
			div3StageList.add(div2StageList.get(0));
			div3StageList.get(0).setIsDiv3StageExecuted(true);
			int adreess= div3StageList.get(0).getAddress();
			String Number= instWithNo.get(adreess);
			
			System.out.println("DIV3 Stage : ("+Number+") "+ div3StageList.get(0).toString());
			//System.out.println("DIV3 Stage : "+div3StageList.get(0).toString());
			
		}
		else if(div3StageList.size()==1)
		{
			div3StageList.get(0).setIsDiv3StageExecuted(true);
			int adreess= div3StageList.get(0).getAddress();
			String Number= instWithNo.get(adreess);
			
			System.out.println("DIV3 Stage : ("+Number+") "+ div3StageList.get(0).toString());
		}
		else
		{
			System.out.println("DIV3 Stage : Empty");
		}
	}
	private void div4StageExecution(){
		if(div3StageList.size()>0 && div4StageList.size()==0){
			div4StageList.add(div3StageList.get(0));
			div4StageList.get(0).setIsDiv4StageExecuted(true);
			int adreess= div4StageList.get(0).getAddress();
			String Number= instWithNo.get(adreess);
			
			System.out.println("DIV4 Stage : ("+Number+") "+ div4StageList.get(0).toString());
			//System.out.println("DIV4 Stage : "+div4StageList.get(0).toString());
			
		}
		else if(div4StageList.size()==1)
		{
			div4StageList.get(0).setIsDiv4StageExecuted(true);
			int adreess= div4StageList.get(0).getAddress();
			String Number= instWithNo.get(adreess);
			
			System.out.println("DIV4 Stage : ("+Number+") "+ div4StageList.get(0).toString());
		}
		else
		{
			System.out.println("DIV4 Stage : Empty");
		}
	}
	
	private void memStageExecution(){
		if(memStageList.size()>0){
			int adreess= memStageList.get(0).getAddress();
			String Number= instWithNo.get(adreess);
			
			System.out.println("Mem Stage : ("+Number+") "+ memStageList.get(0).toString());
			//System.out.println("Mem Stage : "+memStageList.get(0).toString());
			if(memStageList.get(0).getInstructionType().equals("LOAD")){
			memStageList.get(0).getDestination().setOperandValue(memory[memStageList.get(0).getDestination().getOperandValue()]);
			}
			if(memStageList.get(0).getInstructionType().equals("STORE")){
				int source1 = memStageList.get(0).getSource1().getOperandValue();
				memory[memStageList.get(0).getSource1().getOperandValue()] = source1;
			}
			memStageList.get(0).setIsMemExecuted(true);
		}
		else
		{
			System.out.println("MEM Stage : Empty");
		}
	}
	
	private void writeBackStageExecution(){
		if(writeBackStageList.size()>0){
			int adreess= writeBackStageList.get(0).getAddress();
			String Number= instWithNo.get(adreess);
			
			System.out.println("WriteBack Stage : ("+Number+") "+ writeBackStageList.get(0).toString());
			//System.out.println("WriteBack Stage : "+writeBackStageList.get(0).toString());
			instructioninWB = writeBackStageList.get(0).toString();
			
			
			if(writeBackStageList.get(0).getInstructionType().equals("ADD")
			  ||writeBackStageList.get(0).getInstructionType().equals("SUB")
			  ||writeBackStageList.get(0).getInstructionType().equals("MUL")
			  ||writeBackStageList.get(0).getInstructionType().equals("LOAD")
			  ||writeBackStageList.get(0).getInstructionType().equals("DIV")
			  ||writeBackStageList.get(0).getInstructionType().equals("JAL")){
				
				archRegisterFile.put(writeBackStageList.get(0).getDestination().getOperandName(), writeBackStageList.get(0).getDestination().getOperandValue());
				String dest = writeBackStageList.get(0).getDestination().getOperandName();
				if(writeBackStageList.get(0).getPsw().equals(true))
				{
					writeBackStageList.get(0).setPsw(false);
					stallFlag = false;
				
				int regvalue = writeBackStageList.get(0).getDestination().getOperandValue();
				 if(regvalue == 0){
					 zeroflag = 0;
				 }
				 else{
					 zeroflag = 1;
				 }
				}
				 if(phyRegister.get(dest).equals(false)){
				 phyRegister.put(dest, true);
				 if(phyRegister.get(dest).equals(false))
				 {
					 phyRegister.put(dest, true);
				 }
				 
				 
				 
				 if(phyRegister.get(dest).equals(true)&& dependentRegister.get(dest).equals(true))
				 {
					 if(depcount==2)
					 {
						 depcount--;
					 }
					 else if(depcount==1)
					 {
						 depcount--;
					 }
					 if( depcount==0 )  //stallDone.get(dest).equals(true) &&
						 {
							 setIsDecodeStageStalled(false);
						 }
				 }
		
			 }
			 }
			
			else if(writeBackStageList.get(0).getInstructionType().equals("MOVC")){
				 archRegisterFile.put(writeBackStageList.get(0).getDestination().getOperandName(), writeBackStageList.get(0).getDestination().getOperandValue());
				 String dest = writeBackStageList.get(0).getDestination().getOperandName();			 
				 
				 
				 if(phyRegister.get(dest).equals(false))
				 {
					 phyRegister.put(dest, true);
				 }
				 
				 
				 
				 if(phyRegister.get(dest).equals(true)&& dependentRegister.get(dest).equals(true))
				 {
					 if(depcount==2)
					 {
						 depcount--;
					 }
					 else if(depcount==1)
					 {
						 depcount--;
					 }
					 if( depcount==0 )  //stallDone.get(dest).equals(true) &&
						 {
							 setIsDecodeStageStalled(false);
						 }
				 }
			 
			 }
				 
			 
		}
		else
		{
			System.out.println("WriteBack Stage : Empty");
		}
	}
}

