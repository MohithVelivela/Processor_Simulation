package processor.pipeline;

import processor.Processor;
//import java.util.Arrays;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;

	Control_Unit control_unit;

	public String int_to_String(int num){
		String x = "";
		for(int i=31;i>=0;i--)
		{
			if(num%2 == 0){
				x = '0'+x;
			}
			else{
				x = '1' + x;
			}
			num=num/2;
		}
		return x;
	}
	public String int_to_string(int number) {
		StringBuilder binary = new StringBuilder();
		for (int i = 31; i >= 0; i--) {
			int bit = (number >> i) & 1;
			binary.append(bit);
		}
		return binary.toString();
	}
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, Control_Unit control_Unit)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.control_unit = control_Unit;
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			//TODO
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter(); //getting the PC value
			int newInstruction = IF_OF_Latch.getInstruction(); 
			
			// Collecting the instruction passed by IF from IF_OF_Latch
			OF_EX_Latch.setInstruction(newInstruction);
			String bit_instruction = "";
			bit_instruction = int_to_string(newInstruction); //Converting the Instruction in Integer to binary
			// Immediate Calculation
			String Immediate = bit_instruction.substring(15, 32);
			//System.out.println(bit_instruction);
			int Immediate_int = Integer.parseInt(Immediate,2);
			OF_EX_Latch.setImmediate(Immediate_int);
			// Branch Target Calculation
			String BranchTarget = bit_instruction.substring(0,22);
			int BranchTarget_int = Integer.parseInt(BranchTarget,2);
			//System.out.println(BranchTarget_int);
			OF_EX_Latch.setBranchTarget(BranchTarget_int);
			// Calculating Operands
			//Calculating the register for operand1 rs1 : 22 : 26 bits
			String Operand1 = bit_instruction.substring(5,10);
			int Operand1_Reg = Integer.parseInt(Operand1,2);

			//Calculating the registers for rd and rs2 for Operand2 : rd : 12:16, rs2 : 17 : 21
			String rd = bit_instruction.substring(15,20);
			String rs2 = bit_instruction.substring(10,15);
			int rd_int = Integer.parseInt(rd,2);
			int rs2_int = Integer.parseInt(rs2,2);
			int Operand2_Reg;
			if(control_unit.isSt()){
				Operand2_Reg = rd_int;
			}
			else{
				Operand2_Reg = rs2_int;
			}
			// Fetching Operand1 and Operand2 from Register File

			int Operand1_int = containingProcessor.getRegisterFile().getValue(Operand1_Reg);
			int Operand2_int = containingProcessor.getRegisterFile().getValue(Operand2_Reg);

			OF_EX_Latch.setOperand1(Operand1_int);
			OF_EX_Latch.setOperand2(Operand2_int);
			control_unit.setop2(Operand2_Reg);
			control_unit.setop1(Operand1_Reg);
			// Sending the Opcode for the Control Unit for Generating Signals
			String Opcode = bit_instruction.substring(0,5);
			int Opcode_int = Integer.parseInt(Opcode,2);
			/*if(Opcode_int != 0){
				System.out.println(Operand1_int);
				System.out.println(Operand2_int);
				System.out.println(Immediate_int);
				System.out.println(BranchTarget_int);
			}*/
			control_unit.setOpcode(Opcode);
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
			System.out.println(rd_int);
		}
	}

}
