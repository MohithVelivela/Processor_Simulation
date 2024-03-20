package processor.pipeline;

import processor.Processor;

public class InstructionFetch {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	Data_Interlock data_interlock;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch, Data_Interlock data_Interlock)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.data_interlock = data_Interlock;

	}
	
	public void performIF()
	{
		//IF_EnableLatch.setIF_enable(true);
		System.out.println(IF_EnableLatch.isIF_enable());
		if(IF_EnableLatch.isIF_enable())
		{
			if(EX_IF_Latch.IsBranchTaken){
				int newPC = EX_IF_Latch.getBranchtarget();
				containingProcessor.getRegisterFile().setProgramCounter(newPC);
			}
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			System.out.println("Current:" + currentPC);
			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			IF_OF_Latch.setInstruction(newInstruction);
			// Add here about the branch taken from Execute Unit later
			IF_EnableLatch.setIF_enable(false);
			IF_OF_Latch.setOF_enable(true);
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			System.out.println("Next:" + (currentPC + 1));
		}
	}

}
