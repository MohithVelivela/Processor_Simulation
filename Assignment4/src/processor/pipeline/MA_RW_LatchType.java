package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	int aluOutput;
	int LdResult;
	boolean Wb;
	int op2;
	String Opcode;
	boolean nop;
	public MA_RW_LatchType()
	{
		RW_enable = false;
		nop = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}
	public void setAluOutput(int x){
		this.aluOutput = x;
	}
	public int getAluOutput(){
		return aluOutput;
	}
	public void setldResult(int x){
		this.LdResult = x;
	}
	public int getldResult(){
		return LdResult;
	}
	public void setWB(boolean b){
		this.Wb = b;
	}
	public boolean isWb(){
		return Wb;
	}
	public int getrd(){
		return op2;
	}
	public void setrd(int x){
		this.op2 = x;
	}
	public void setOpcode(String opcode){
		this.Opcode = opcode;
	}
	public String getOpcode(){
		return Opcode;
	}

	public void setNOP(boolean x){
		this.nop = x;
	}
	public boolean getNOP(){
		return nop;
	}
	
}
