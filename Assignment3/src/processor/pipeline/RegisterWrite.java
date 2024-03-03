package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Control_Unit control_unit;
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch, Control_Unit control_Unit)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.control_unit = control_Unit;
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			boolean Wb = MA_RW_Latch.isWb();
			if(Wb){
				int ldResult= MA_RW_Latch.getldResult();
				//System.out.println("Ld_res "+ldResult);
				int aluOutput = MA_RW_Latch.getAluOutput();
				//System.out.println("ALU_out "+aluOutput);
				int rd = control_unit.getrd();
				//System.out.println("Destination_reg  "+rd);
				int writevalue;
				if(control_unit.isLd()){
					writevalue = ldResult;
				}
				else{
					writevalue = aluOutput;
				}
				//System.out.println("Value_in_dest_reg  "+writevalue);
				RegisterFile registerFile = containingProcessor.getRegisterFile();
				//System.out.println(rd);
				//System.out.println(writevalue);
				registerFile.setValue(rd, writevalue);
			}
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
			if(control_unit.isEnd()){
				Simulator.setSimulationComplete(Wb);
			}
		}
	}

}
