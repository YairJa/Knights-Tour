
public class ChessBoard {

	private final int size;
    protected final ChessSpot[][] points;
    private final int startRow;
    private final int startCol;
    private final int[] dx = {2,2,1,1,-1,-1,-2,-2};
    private final int[] dy = {1,-1,2,-2,2,-2,1,-1};
    
	public ChessBoard(int row, int col, int size) {
		this.startRow=row;this.startCol=col; 
		this.points= new ChessSpot[size][size];
		this.size=size;
        
		// create all points
		for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                points[i][j] = new ChessSpot(i, j);
            }
        }
		
	       // initialize neighbors for each point
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
            	ChessSpot p = points[i][j];
                for (int k = 0; k < 8; k++) {
                    int ni = i + dx[k];
                    int nj = j + dy[k];
                    if (isValid(ni, nj)) {
                        p.addNeighbor(points[ni][nj]);
                    }
                }
                // initialize rem neighbors same as static at start
                p.resetDynamicNeighbors();
            }
        }
    }
	
	public void resetBoard() {  // reset Board ChessSpots fields, allows calling KnightsTour multiple times without error.
		for(int i=0;i<this.size;i++) {
			for(int j=0; j< this.size; j++) {
				this.points[i][j].resetSpot();
			}
		}
	}
	
	  private boolean isValid(int r, int c) { // verify ChessSpot possible coordinates are in ChessBoard limits
	        return r >= 0 && r < size && c >= 0 && c < size;
	    }
	
	 
	  public void KnightsTour() {
		  resetBoard();
		  int step=1;
		  ChessSpot curr = points[startCol][startRow];
		  curr.stepForward(step, null);
		  ChessSpot prev;
		  while (step<this.size*this.size) { // while board aint full
			  prev= curr;
			  curr = curr.randomNextStep(); //search for next step
			  
			  if (curr==null) { // next step is dead-end
				  curr=prev.getPrev();
				  prev.stepBackward();
				  step--;
				  
			  }
			  else {
				  step++;
				  curr.stepForward(step, prev);
			  }

		  }

	  }
	  
		@Override
		public String toString() { // prints ChessBoard by step values of its ChessSpots
			StringBuilder sb = new StringBuilder();
		    for (int i = 0; i < this.size; i++) {
		        for (int j = 0; j < this.size; j++) {
		            sb.append(String.format("%2s ", points[i][j])); 
		        }
		        sb.append("\n");
		    }
		    return sb.toString();		
		    }
	  



}
