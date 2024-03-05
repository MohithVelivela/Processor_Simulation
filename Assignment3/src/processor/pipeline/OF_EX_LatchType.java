package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	int Immediate;

	int BranchTarget;

	int Operand1;
	int Operand2;
	int Instruction;
	int rd;
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}
	public int getInstruction(){
		return Instruction;
	}
	public void setInstruction(int instruction){
		Instruction = instruction;
	}
	public void  setImmediate(int immediate){Immediate = immediate;}

	public int getImmediate(){return Immediate;}

	public void setBranchTarget(int branchTarget) {BranchTarget = branchTarget;}

	public int getBranchTarget(){return BranchTarget;}

	public void setOperand1(int operand1) {Operand1 = operand1;}

	public int getOperand1(){return Operand1;}

	public void setOperand2(int operand2){Operand2 = operand2;}

	public int getOperand2(){return Operand2;}
	public void setrd(int x){
		this.rd = x;
	}
	public int getrd(){
		return rd;
	}
}
