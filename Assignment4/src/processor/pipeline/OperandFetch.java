package processor.pipeline;

import generic.Operand;
import processor.Processor;
//import java.util.Arrays;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	Data_Interlock data_interlock;
	Control_Unit control_unit;
	IF_EnableLatchType IF_EnableLatch;

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
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, Control_Unit control_Unit, Data_Interlock data_Interlock, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.control_unit = control_Unit;
		this.data_interlock = data_Interlock;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performOF()
	{	
		boolean data_hazard = false;
		///System.out.println("data_hazard_bef" + data_hazard);
		System.out.println(IF_OF_Latch.isOF_enable());
		if(IF_OF_Latch.isOF_enable())
		{	
			
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter(); //getting the PC value
			int newInstruction = IF_OF_Latch.getInstruction(); 
			String bit_instruction = "";
			bit_instruction = int_to_string(newInstruction); //Converting the Instruction in Integer to binary
			String Opcode = bit_instruction.substring(0,5);
			String Operation_type = control_unit.map_operation_type.get(Opcode);
			String Operation = control_unit.map_operation_name.get(Opcode);
			//System.out.println(Operation_type);
			if(Operation_type == "r3"){
				String rs1 = bit_instruction.substring(5,10);
				String rs2 = bit_instruction.substring(10,15);
				String rd = bit_instruction.substring(15,20);
				int rs1_int = Integer.parseInt(rs1,2);
				int rs2_int = Integer.parseInt(rs2,2);
				int rd_int = Integer.parseInt(rd,2);
				int operand1 = containingProcessor.getRegisterFile().getValue(rs1_int);
				int operand2 = containingProcessor.getRegisterFile().getValue(rs2_int);
				if(data_interlock.get_reg_lock(rs1_int) || data_interlock.get_reg_lock(rs2_int))
				{
					data_hazard = true;
				}
				OF_EX_Latch.setInstruction(newInstruction);
				OF_EX_Latch.setOperand1(operand1);
				OF_EX_Latch.setOperand2(operand2);
				OF_EX_Latch.setrd(rd_int);
				data_interlock.set_reg_lock(rd_int,true);
				if(Operation.equals("div")){
					data_interlock.set_reg_lock(31,true);
				}
				/*System.out.println(Operation_type);
				System.out.println("Reg 1:"+rs1_int);
				System.out.println("Reg 2:" + rs2_int);
				System.out.println("Dest Reg:" + rd_int);
				System.out.println("Value of R1:"+operand1);
				System.out.println("Value of R2:" +operand2);
				//System.out.println("......................................");
				*/
			}
			else if(Operation_type == "r2i"){
				String rs1 = bit_instruction.substring(5,10);
				String rd = bit_instruction.substring(10,15);
				String Immediate = bit_instruction.substring(15,32);
				int Immediate_int;
				if (Immediate.charAt(0) == '1') {
					String twosComplement = "";
					for (int i = 0; i < Immediate.length(); i++) {
						twosComplement += (Immediate.charAt(i) == '0') ? '1' : '0';
					}
					Immediate_int = -(Integer.parseInt(twosComplement, 2) + 1);
				} else {
					Immediate_int = Integer.parseInt(Immediate, 2);
				}
				int rs1_int = Integer.parseInt(rs1,2);
				int rd_int = Integer.parseInt(rd,2);
				//int Immediate_int = Integer.parseInt(Immediate);
				int operand_rs1 = containingProcessor.getRegisterFile().getValue(rs1_int);
				int operand_rd = containingProcessor.getRegisterFile().getValue(rd_int);
				if(data_interlock.get_reg_lock(rs1_int)){

					data_hazard = true;
				}
				if((Operation.equals("beq") || Operation.equals("bgt") || Operation.equals("blt") || Operation.equals("bne") )&& (data_interlock.get_reg_lock(rd_int)))
				{
					data_hazard = true;
				}
				else{
				OF_EX_Latch.setInstruction(newInstruction);
				OF_EX_Latch.setOperand1(operand_rs1);
				OF_EX_Latch.setImmediate(Immediate_int);
				OF_EX_Latch.setrd(rd_int);
				OF_EX_Latch.setrd_store_value(operand_rd);
				data_interlock.set_reg_lock(rd_int,true);
				if(Operation.equals("beq") || Operation.equals("bgt") || Operation.equals("blt") || Operation.equals("bne") || Operation.equals("jmp") )
				{
					data_interlock.set_reg_lock(rd_int,true);
				}
				if(Operation.equals("divi")){
					data_interlock.set_reg_lock(31,true);
				}
				}
				/*System.out.println(Operation_type);
				System.out.println("Reg 1:"+rs1_int);
				System.out.println("Reg Dest:"+rd_int);
				System.out.println("Immediate Value"+Immediate_int);
				System.out.println("Value of Reg 1:" + operand_rs1);
				System.out.println("Store Register" +operand_rd);
				//System.out.println("......................................");
				*/
			

			}
			else{  //for operation_type == ri
				String rd = bit_instruction.substring(5,10);
				String Immediate = bit_instruction.substring(10,32);
				int Immediate_int;
				if (Immediate.charAt(0) == '1') {
					String twosComplement = "";
					for (int i = 0; i < Immediate.length(); i++) {
						twosComplement += (Immediate.charAt(i) == '0') ? '1' : '0';
					}
					Immediate_int = -(Integer.parseInt(twosComplement, 2) + 1);
				} else {
					Immediate_int = Integer.parseInt(Immediate, 2);
				}
				//System.out.println("......................................");
				int rd_int = Integer.parseInt(rd,2);
				int operand_rd = containingProcessor.getRegisterFile().getValue(rd_int);
				OF_EX_Latch.setrd(rd_int);
				OF_EX_Latch.setImmediate(Immediate_int);
				data_interlock.set_reg_lock(rd_int,true);
			}

			OF_EX_Latch.setOpcode(Opcode);
			OF_EX_Latch.setNOP(data_hazard);
			//System.out.println("data_hazard_aft" + data_hazard);
			if(data_hazard){
				IF_EnableLatch.setIF_enable(false);
			}
			else{
			control_unit.setOpcode(Opcode);
			IF_OF_Latch.setOF_enable(true);
			OF_EX_Latch.setEX_enable(true);
			IF_EnableLatch.setIF_enable(true);

			}
			}

	}

}
