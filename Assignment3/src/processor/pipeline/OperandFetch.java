package processor.pipeline;

import processor.Processor;
import java.util.Arrays;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;

	Control_Unit control_unit;

	public boolean[] inttoBooleanArray(int num){
		boolean[] bits = new boolean[32];
		for(int i=31;i>=0;i--)
		{
			bits[i] = (num % 2 == 1);
			num/=2;

		}
		return bits;
	}

	public int BooleanArraytoInt(boolean[] bits, int size)
	{
		int n = size;
		int num = 0;
		int multiplier = 1;
		for (int i = n-1; i >= 0; i--) {
			if (bits[i]) {
				num += multiplier;
			}
			multiplier *= 2;
		}
		return num;
	}
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			//TODO
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter(); //getting the PC value
			int newInstruction = IF_OF_Latch.getInstruction(); // Collecting the instruction passed by IF from IF_OF_Latch
			OF_EX_Latch.setInstruction(newInstruction);
			boolean[] bit_instruction = new boolean[32];
			bit_instruction = inttoBooleanArray(newInstruction); //Converting the Instruction in Integer to binary
			// Immediate Calculation
			boolean[] Immediate = Arrays.copyOfRange(bit_instruction, 0, 17);
			int Immediate_int = BooleanArraytoInt(Immediate,17);
			OF_EX_Latch.setImmediate(Immediate_int);
			// Branch Target Calculation
			boolean[] BranchTarget = Arrays.copyOfRange(bit_instruction,0,22);
			int BranchTarget_int = BooleanArraytoInt(BranchTarget,22);
			OF_EX_Latch.setBranchTarget(BranchTarget_int);
			// Calculating Operands
			//Calculating the register for operand1 rs1 : 22 : 26 bits
			boolean[] Operand1 = Arrays.copyOfRange(bit_instruction,22,27);
			int Operand1_Reg = BooleanArraytoInt(Operand1,5);

			//Calculating the registers for rd and rs2 for Operand2 : rd : 12:16, rs2 : 17 : 21
			boolean[] rd = Arrays.copyOfRange(bit_instruction,12,17);
			boolean[] rs2 = Arrays.copyOfRange(bit_instruction, 17,22);
			int rd_int = BooleanArraytoInt(rd,5);
			int rs2_int = BooleanArraytoInt(rs2,5);
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

			// Sending the Opcode for the Control Unit for Generating Signals
			boolean[] Opcode = Arrays.copyOfRange(bit_instruction,27,32);
			int Opcode_int = BooleanArraytoInt(Opcode,5);
			control_unit.setOpcode(Opcode_int);
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
