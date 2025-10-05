
public class ChessBoard {

	private final int size=8;
    protected final ChessSpot[][] points;
    private final int startRow;
    private final int startCol;
    private final int[] dx = {2,2,1,1,-1,-1,-2,-2};
    private final int[] dy = {1,-1,2,-2,2,-2,1,-1};
    
	public ChessBoard(int row, int col) {
		this.startRow=row;this.startCol=col; 
		points= new ChessSpot[size][size];
        
		// create all points
		for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                points[r][c] = new ChessSpot(r, c);
            }
        }
		
	       // initialize neighbors for each point
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
            	ChessSpot p = points[r][c];
                for (int k = 0; k < 8; k++) {
                    int nr = r + dx[k];
                    int nc = c + dy[k];
                    if (isValid(nr, nc)) {
                        p.allNeighbors.add(points[nr][nc]);
                    }
                }
                // initialize rem neighbors same as static at start
                p.resetDynamicNeighbors();
            }
        }
    }
	
	  private boolean isValid(int r, int c) { // verify ChessSpot possible coordinates are in ChessBoard limits
	        return r >= 0 && r < size && c >= 0 && c < size;
	    }
	
	 
	  public void KnightsTour() {
		  int step=1;
		  ChessSpot curr = points[startCol][startRow];
		  curr.stepForward(step, null);
		  ChessSpot prev;
		  while (step<64) {
			  prev= curr;
			  curr = curr.randomNextStep();
			  
			  if (curr==null) {
				  curr=prev.prev;
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
		    for (int r = 0; r < size; r++) {
		        for (int c = 0; c < size; c++) {
		            sb.append(String.format("%2s ", points[r][c])); 
		        }
		        sb.append("\n");
		    }
		    return sb.toString();		
		    }
	  

	  

}
