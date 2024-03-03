package processor.pipeline;

public class EX_IF_LatchType {

	boolean IF_Enable;

	int Branchtarget;
	boolean IsBranchTaken;
	
	public EX_IF_LatchType()
	{
		IF_Enable = false;
	}
	public void setIF_Enable(boolean value){
		IF_Enable = value;
	}
	public void setBranchtarget(int branchtarget){
		Branchtarget = branchtarget;
	}
	public int getBranchtarget(){
		return Branchtarget;
	}

	public void setBranchTaken(boolean branchTaken){
		IsBranchTaken = branchTaken;
	}

	public boolean getBranchTaken()
	{
		return IsBranchTaken;
	}

	}


