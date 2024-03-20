package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Control_Unit control_unit;
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	Data_Interlock data_interlock;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch, Control_Unit control_Unit, Data_Interlock data_Interlock)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.control_unit = control_Unit;
		this.data_interlock = data_Interlock;
	}
	
	public void performRW()
	{
		System.out.println(MA_RW_Latch.isRW_enable());
		if(MA_RW_Latch.isRW_enable())
		{	

			if(MA_RW_Latch.getNOP()){
				//continue;
			}
			else{
			boolean Wb = MA_RW_Latch.isWb();
			String opcode = MA_RW_Latch.getOpcode();
			String Operation =  control_unit.map_operation_name.get(opcode);
			System.out.println(Operation);
			//System.out.println(Wb);
			if(Wb){
				int ldResult= MA_RW_Latch.getldResult();
				//System.out.println("Ld_res "+ldResult);
				int aluOutput = MA_RW_Latch.getAluOutput();
				//System.out.println("ALU_out "+aluOutput);
				int rd = MA_RW_Latch.getrd();
				//System.out.println("Destination_reg  "+rd);
				int writevalue;
				if(Operation.equals("load")){
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
				//System.out.println("Written " + writevalue +"to register no."+rd);
				data_interlock.set_reg_lock(rd,false);
				if(Operation.equals("div") || Operation.equals("divi")){
					data_interlock.set_reg_lock(31,false);
				}

			}
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
			if(Operation.equals("end")){
				int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
				containingProcessor.getRegisterFile().setProgramCounter(currentPC-2);
				Simulator.setSimulationComplete(true);
			}
			/*	if(control_unit.isBeq()){
				System.out.println("Hi");
			}*/
		}
		}
	}

}
