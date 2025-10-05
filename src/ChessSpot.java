import java.util.ArrayList;
import java.util.*;

public class ChessSpot {

	
	protected final int row; // position usage
	protected final int col; // position usage
	protected boolean vis; // has knight passed this chessSpot in route
	protected ChessSpot prev; // previous chessSpot on knights route
	protected int step; // order of tour usage
	  final List<ChessSpot> allNeighbors = new ArrayList<>();
	    final List<ChessSpot> remNeighbors = new ArrayList<>();
	
	public ChessSpot(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.vis=false;
		this.step=-1;
		this.prev=null;
	}
	
	  void resetDynamicNeighbors() { // used when stepping back from a deadEnd
		  remNeighbors.clear();
	        for (ChessSpot nb : allNeighbors) {
	            if (!nb.vis) remNeighbors.add(nb);
	        }
	    }
	
	 public ChessSpot nextStep() { // choose the available neighbor with the least remaining available neighbors
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
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	@Override
	public String toString() { // prints ChessSpot by its step value for knights tour visuality
		return "["+Integer.toString(step)+"]" ;
	}
}
