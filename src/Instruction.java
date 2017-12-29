import java.util.HashMap;
import java.util.Map;

public class Instruction {
	private String instruction;
	private String instructionType;
	private Operand source1;
	private Operand source2;
	private Operand literal;
	private Operand destination;
	private Boolean isMemExecuted = false;
	private Boolean isDecoded = false;
	private Boolean isALUExecuted = false;
	private Boolean isMul1Executed = false;
	private Boolean isMul2Executed = false;
	private Boolean isDiv1StageExecuted = false;
	private Boolean isDiv2StageExecuted = false;
	private Boolean isDiv3StageExecuted = false;
	private Boolean isDiv4StageExecuted = false;
	private int address;
	private Boolean isDependent = false;
	private Boolean psw = false;
	private Boolean isStalled = false;
	private Boolean branchFlag = false;
	

	public Instruction() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getInstructionType() {
		return instructionType;
	}
	public void setInstructionType(String instructionType) {
		this.instructionType = instructionType;
	}
	public Operand getSource1() {
		return source1;
	}
	public void setSource1(Operand source1) {
		this.source1 = source1;
	}
	public Operand getSource2() {
		return source2;
	}
	public void setSource2(Operand source2) {
		this.source2 = source2;
	}
	public Operand getDestination() {
		return destination;
	}
	public void setDestination(Operand destination) {
		this.destination = destination;
	}
	public Boolean getIsMemExecuted() {
		return isMemExecuted;
	}
	public void setIsMemExecuted(Boolean isMemExecuted) {
		this.isMemExecuted = isMemExecuted;
	}
	public Operand getLiteral() {
		return literal;
	}
	public void setLiteral(Operand literal) {
		this.literal = literal;
	}
	public void setIsMemoryAllocated(Boolean isMemExecuted) {
		this.isMemExecuted = isMemExecuted;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public Boolean getIsDecoded() {
		return isDecoded;
	}
	public void setIsDecoded(Boolean isDecoded) {
		this.isDecoded = isDecoded;
	}
	public Boolean getIsDependent() {
		return isDependent;
	}
	public void setIsDependent(Boolean isDependent) {
		this.isDependent = isDependent;
	}
	public Boolean getIsStalled() {
		return isStalled;
	}
	public void setIsStalled(Boolean isStalled) {
		this.isStalled = isStalled;
	}
	public Boolean getIsMul1Executed() {
		return isMul1Executed;
	}
	public void setIsMul1Executed(Boolean isMul1Executed) {
		this.isMul1Executed = isMul1Executed;
	}
	public Boolean getIsMul2Executed() {
		return isMul2Executed;
	}
	public void setIsMul2Executed(Boolean isMul2Executed) {
		this.isMul2Executed = isMul2Executed;
	}
	public Boolean getIsALUExecuted() {
		return isALUExecuted;
	}
	public void setIsALUExecuted(Boolean isALUExecuted) {
		this.isALUExecuted = isALUExecuted;
	}
	public Boolean getIsDiv1StageExecuted() {
		return isDiv1StageExecuted;
	}
	public void setIsDiv1StageExecuted(Boolean isDiv1StageExecuted) {
		this.isDiv1StageExecuted = isDiv1StageExecuted;
	}
	public Boolean getIsDiv2StageExecuted() {
		return isDiv2StageExecuted;
	}
	public void setIsDiv2StageExecuted(Boolean isDiv2StageExecuted) {
		this.isDiv2StageExecuted = isDiv2StageExecuted;
	}
	public Boolean getIsDiv3StageExecuted() {
		return isDiv3StageExecuted;
	}
	public void setIsDiv3StageExecuted(Boolean isDiv3StageExecuted) {
		this.isDiv3StageExecuted = isDiv3StageExecuted;
	}
	public Boolean getIsDiv4StageExecuted() {
		return isDiv4StageExecuted;
	}
	public void setIsDiv4StageExecuted(Boolean isDiv4StageExecuted) {
		this.isDiv4StageExecuted = isDiv4StageExecuted;
	}
	public Boolean getPsw() {
		return psw;
	}
	public void setPsw(Boolean psw) {
		this.psw = psw;
	}
	public Boolean getBranchFlag() {
		return branchFlag;
	}
	public void setBranchFlag(Boolean branchFlag) {
		this.branchFlag = branchFlag;
	}
	public String toString(){
		return instruction;
	}		
}
