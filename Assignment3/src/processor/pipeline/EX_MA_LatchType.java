package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	int aluOutput;
	int op2;
	int op1;
	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}
	public void setAluOutput(int x){
		this.aluOutput=x;
	}
	public int getAluOutput(){
		return aluOutput;
	}
	public void setOperand2(int x){
		this.op2=x;
	}
	public int getOperand2(){
		return op2;
	}
	public void setOperand1(int x){
		this.op1=x;
	}
	public int getOperand1(){
		return op1;
	}
	
	

}
