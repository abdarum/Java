public class Food {
	
	private final int RAND_POS = 29;
    
	public int locateApple_x(int apple_x) {

        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * Board.DOT_SIZE));
        
        return apple_x;
    }
	
	public int locateApple_y(int apple_y) {

		int r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * Board.DOT_SIZE));
        
        return apple_y;
        
    }

	public boolean checkApple(int apple_x, int apple_y, int x[], int y[]) {

	        if ((x[0] == apple_x) && (y[0] == apple_y)) {
	            return true;
	        }
	        return false;
	    }
}
