package generic;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import processor.Clock;
import processor.Processor;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		try{
			InputStream x=new FileInputStream(assemblyProgramFile);
			DataInputStream y=new DataInputStream(x);
			int n=0;
			if(y.available()>0){
				n=y.readInt();
				processor.getRegisterFile().setProgramCounter(n);
			}
			for(int a=0;y.available()>0;a++){
				n=y.readInt();
				processor.getMainMemory().setWord(a, n);
			}
			processor.getRegisterFile().setValue(0, 0);
			processor.getRegisterFile().setValue(1, 65535);
			processor.getRegisterFile().setValue(2, 65535);
			y.close();
		}
		catch(IOException e){
			System.err.println(assemblyProgramFile);
		}
	}
	
	public static void simulate()
	{
		int instructions=0;
		int cycles=0;
		while(simulationComplete == false)
		{
			processor.getIFUnit().performIF();
			processor.getOFUnit().performOF();
			processor.getEXUnit().performEX();
			processor.getMAUnit().performMA();
			processor.getRWUnit().performRW();
			Clock.incrementClock();
			instructions++;
			cycles++;
		}
		
		// TODO
		// set statistics
		Statistics.setNumberOfInstructions(instructions);
		Statistics.setNumberOfCycles(cycles);
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
