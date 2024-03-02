package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Control_Unit control_unit;
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			boolean Wb = MA_RW_Latch.isWb();
			int ldResult= MA_RW_Latch.getldResult();
			int aluOutput = MA_RW_Latch.getAluOutput();
			int rd = MA_RW_Latch.getOperand2();
			int writevalue;
			if(Wb){
				if(control_unit.isLd()){
					writevalue = ldResult;
				}
				else{
					writevalue = aluOutput;
				}
				RegisterFile registerFile = containingProcessor.getRegisterFile();
				registerFile.setValue(rd, writevalue);
			}
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
	}

}
