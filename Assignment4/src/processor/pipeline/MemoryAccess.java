package processor.pipeline;
import processor.Processor;
import processor.memorysystem.MainMemory;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	Control_Unit control_unit;
	Data_Interlock data_interlock;
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch,Control_Unit control_Unit, Data_Interlock data_Interlock)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.control_unit = control_Unit;
		this.data_interlock = data_Interlock;
	}
	
	public void performMA()
	{
		boolean Wb=true;
		//int op2 = EX_MA_Latch.getOperand2();
		int aluOutput = EX_MA_Latch.getAluOutput();
		MA_RW_Latch.setAluOutput(aluOutput);
		System.out.println(EX_MA_Latch.isMA_enable());
		if(EX_MA_Latch.isMA_enable()){
			String opcode = EX_MA_Latch.getOpcode();
			MA_RW_Latch.setOpcode(opcode);
			String Operation = control_unit.map_operation_name.get(opcode);

			if(EX_MA_Latch.getNOP()){
				MA_RW_Latch.setNOP(true);
			}


			else{
			if(Operation.equals("load")){
				//System.out.println("Value of Address"+aluOutput);
				//System.out.println("Hellow");
				int load = containingProcessor.getMainMemory().getWord(aluOutput);
				//System.out.println("Value at Address"+load);
				MA_RW_Latch.setldResult(load);
			}
			else if(Operation.equals("store")){
				Wb=false;
				int r1 = EX_MA_Latch.getOperand1();
				containingProcessor.getMainMemory().setWord(aluOutput, r1);
				//System.out.println("Stored " + r1 + "at" + aluOutput);
				//System.out.println("Contents at" + aluOutput+ " =" +containingProcessor.getMainMemory().getWord(aluOutput) );
			}
			else if(Operation.equals("beq") || Operation.equals("blt") || Operation.equals("bgt") || Operation.equals("bne") || Operation.equals("jmp") || Operation.equals("end")){
				//System.out.println("Hellow");
				Wb=false;}

		EX_MA_Latch.setMA_enable(false);//try changing to small e(this.)
		MA_RW_Latch.setRW_enable(true);
		MA_RW_Latch.setWB(Wb);
		MA_RW_Latch.setrd(EX_MA_Latch.getrd());
		}
		}
		
		//System.out.println(EX_MA_Latch.getrd());
		//System.out.println("aluOutput in MA" + aluOutput);
		//System.out.println(Wb);
	}
}
