package processor.pipeline;

import processor.Processor;

public class InstructionFetch {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;

	}
	
	public void performIF()
	{
		IF_EnableLatch.setIF_enable(true);
		if(IF_EnableLatch.isIF_enable())
		{
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			IF_OF_Latch.setInstruction(newInstruction);
			// Add here about the branch taken from Execute Unit later
			boolean b = EX_IF_Latch.getBranchTaken();
			int branch = EX_IF_Latch.getBranchtarget();
			System.out.println("currentPC" + currentPC);
			System.out.println("branch"+branch);
			System.out.println("Logic" + b);
			if(b){
				currentPC = branch;
			}
			else{
				currentPC +=1;
			}
			IF_EnableLatch.setIF_enable(false);
			IF_OF_Latch.setOF_enable(true);
			containingProcessor.getRegisterFile().setProgramCounter(currentPC);
			System.out.println("currentPC after update" + currentPC);
		}
	}

}
