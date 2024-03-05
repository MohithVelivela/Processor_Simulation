package processor.pipeline;
import processor.Processor;
import processor.memorysystem.MainMemory;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	Control_Unit control_unit;
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch,Control_Unit control_Unit)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.control_unit = control_Unit;
	}
	
	public void performMA()
	{
		boolean Wb=true;
		int op2 = EX_MA_Latch.getOperand2();
		int aluOutput = EX_MA_Latch.getAluOutput();
			MA_RW_Latch.setAluOutput(aluOutput);
		if(EX_MA_Latch.isMA_enable()){
			String opcode = control_unit.getOpcode();
			String Operation = control_unit.map_operation_name.get(opcode);
			
			if(Operation.equals("load")){
				//System.out.println("Value of Address"+aluOutput);
				int load = containingProcessor.getMainMemory().getWord(aluOutput);
				//System.out.println("Value at Address"+load);
				MA_RW_Latch.setldResult(load);
			}
			else if(Operation.equals("store")){
				Wb=false;
				int r1 = EX_MA_Latch.getOperand1();
				MainMemory memory = containingProcessor.getMainMemory();
				memory.setWord(aluOutput,r1);

			}
			else if(Operation.equals("beq") || Operation.equals("blt") || Operation.equals("bgt") || Operation.equals("bne") || Operation.equals("jmp")){Wb=false;}
		}
		EX_MA_Latch.setMA_enable(false);//try changing to small e(this.)
		MA_RW_Latch.setRW_enable(true);
		MA_RW_Latch.setWB(Wb);
		MA_RW_Latch.setOperand2(op2);
		//System.out.println("aluOutput in MA" + aluOutput);
	}
}
