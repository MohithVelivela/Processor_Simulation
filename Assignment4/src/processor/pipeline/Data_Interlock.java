package processor.pipeline;
import processor.Processor;
import java.util.*;
public class Data_Interlock {
    Processor containingProcessor;
    public Data_Interlock(Processor containingProcessor){
        this.containingProcessor = containingProcessor;
    }

    public boolean[] bit_vector = new boolean[32];
    
    {
    //System.out.println(bit_vector[1]);
    }

    public void set_reg_lock(int index, boolean lock){
        this.bit_vector[index] = lock;
    }

    public boolean get_reg_lock(int index){
        return bit_vector[index];
    }
 
}