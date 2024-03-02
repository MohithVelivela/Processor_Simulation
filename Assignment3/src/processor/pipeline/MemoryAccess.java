package processor.pipeline;

import generic.Instruction;
import processor.Processor;
import processor.memorysystem.MainMemory;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	Control_Unit control_Unit;
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA()
	{
		//TODO
		boolean Wb=true;
		int op2 = EX_MA_Latch.getOperand2();
		if(EX_MA_Latch.isMA_enable()){
			String opcode = control_Unit.getOpcode();
			int aluOutput = EX_MA_Latch.getAluOutput();
			MA_RW_Latch.setAluOutput(aluOutput);
			if(opcode.equals("load")){
				int load = containingProcessor.getMainMemory().getWord(aluOutput);
				MA_RW_Latch.setldResult(load);
			}
			else if(opcode.equals("store")){
				Wb=false;
				int r1 = EX_MA_Latch.getOperand1();
				MainMemory memory = containingProcessor.getMainMemory();
				memory.setWord(aluOutput,r1);

			}
		}
		EX_MA_Latch.setMA_enable(false);//try changing to small e(this.)
		MA_RW_Latch.setRW_enable(true);
		MA_RW_Latch.setWB(Wb);
		MA_RW_Latch.setOperand2(op2);
	}

}
