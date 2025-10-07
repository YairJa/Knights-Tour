import java.util.*;

public class ChessSpot {

	
	private final int row; // position usage
	private final int col; // position usage
	private boolean vis; // has knight passed this chessSpot in route
	private ChessSpot prev; // previous chessSpot on knights route
	private int step; // order of tour usage
	private final Set<ChessSpot> allNeighbors = new HashSet<>();
	private final Set<ChessSpot> remNeighbors = new HashSet<>();
	
	public ChessSpot(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.vis=false;
		this.step=-1;
		this.prev=null;
	}
	
	public void resetSpot() {
		this.vis=false;
		this.step=-1;
		this.prev=null;
		this.remNeighbors.addAll(allNeighbors);
	}
	
	public void resetDynamicNeighbors() { // used when stepping back from a deadEnd
		  remNeighbors.clear();
	        for (ChessSpot nb : allNeighbors) {
	            if (!nb.vis) remNeighbors.add(nb);
	        }
	    }
	
	public int minNeighborCnt() { //helper function for generalNextStep
		  int min =9;
		  for (ChessSpot cs : this.remNeighbors) {
			  if(cs.remNeighbors.size()<min) {
				  min=cs.remNeighbors.size();
			  }
		  }
		  return min;
	  }
	  
	 public boolean earlyDetection() {
		  int cnt=0;
		  for (ChessSpot cs : this.remNeighbors) {
			  if(cs.remNeighbors.size()==0) {
				 cnt++;
			  }
		  }
		  return cnt>1;
	  }
	  
	 public int generalNextStep() { // Helper function for step search functions that uses early detection logics
		  int min =minNeighborCnt();
		  if(min==9) {
			  return -1;
		  }
		  if(((min==0) && (earlyDetection()))) {
			  return -1;
		  }
		  return min;
	  }
	  
	  public ChessSpot randomNextStep() { // partial Random model next step search using early detection logics
		  int min =generalNextStep ();
		  if (min==-1) {
			  return null;
		  }
		  List<ChessSpot> opt= new ArrayList<>();
		  for (ChessSpot cs : this.remNeighbors) {
			  if(cs.remNeighbors.size()==min) {
				 opt.add(cs);
			  }
		  }
		  Random random= new Random();
		  return opt.get(random.nextInt(opt.size()));
		  
	  }
	  
	  public ChessSpot randomNextStepB() { // partial Random model next step search without early detection logics
		  ChessSpot minCS=null; int min=9;
		  for (ChessSpot cs : this.remNeighbors) {
			  if(cs.remNeighbors.size()<min) {
				  min=cs.remNeighbors.size();
			  }
		  }
		  if(min==9) {
			  return null;
		  }
		  else {
			  List<ChessSpot> opt= new ArrayList<>();
			  for (ChessSpot cs : this.remNeighbors) {
				  if(cs.remNeighbors.size()==min) {
					 opt.add(cs);
				  }
			  }
			  Random random= new Random();
			  return opt.get(random.nextInt(opt.size()));
		  }
	  }
	 
	  public ChessSpot detNextStep() { // Deterministic next step search using early detection logics
		  int min =generalNextStep ();
		  if (min==-1) {
			  return null;
		  }
		  for (ChessSpot cs : this.remNeighbors) {
			  if(cs.remNeighbors.size()==min) {
				 return cs;
			  }
		  }
		  return null;
		  
	  }
	  
	  public ChessSpot detNextStepB() { // Deterministic next step search without early detection logics
		  ChessSpot minCS=null; int min=9;
		  for (ChessSpot cs : this.remNeighbors) {
			  if(cs.remNeighbors.size()<min) {
				  minCS=cs; min=cs.remNeighbors.size();
			  }
		  }
		  return minCS;
	  }
	  

	  
	  
	public void stepForward(int step,ChessSpot prev) { 
		this.step=step;
		this.vis=true;
		this.prev=prev;
		for(ChessSpot CS : this.remNeighbors) {
			CS.remNeighbors.remove(this);
		}
	}
	 
	public void stepBackward() { 
		this.vis=false; this.step=-1;
		resetDynamicNeighbors();
		for(ChessSpot CS : this.remNeighbors) {
			CS.remNeighbors.add(this);
		}
		
		this.prev.remNeighbors.remove(this);
		this.prev=null;
	}

	public ChessSpot getPrev() {
		return prev;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public void addNeighbor(ChessSpot CS) {
		this.allNeighbors.add(CS);
	}

	@Override
	public String toString() { // prints ChessSpot by its step value for knights tour visuality
		if(this.step>9) {
		return "["+Integer.toString(step)+"]" ;
		}
		else {
			return "["+"0"+Integer.toString(step)+"]" ;
		}
			}


		

}
