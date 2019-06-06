

public class Snake {
    public int x[] = new int[Board.ALL_DOTS];
    public int y[] = new int[Board.ALL_DOTS];
    
    private int dots;
    
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    public void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= Board.DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += Board.DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= Board.DOT_SIZE;
        }

        if (downDirection) {
            y[0] += Board.DOT_SIZE;
        }
    }
    
    public boolean checkCollisionSnake() {

        for (int z = dots; z > 0; z--) {
            if ((x[0] == x[z]) && (y[0] == y[z])) {
                return true;
            }
        }
        
        if (y[0] >= Board.B_HEIGHT) {
            return true;
        }

        if (y[0] < 0) {
            return true;
        }

        if (x[0] >= Board.B_WIDTH) {
            return true;
        }

        if (x[0] < 0) {
            return true;
        }
        return false;
    }
    
    public boolean checkCollisionForOtherSnake(Snake oponent) {

        for (int z = dots; z >= 0; z--) {
            if ((x[0] == oponent.x[z]) && (y[0] == oponent.y[z])) {
                return true;
            }
        }
        

        return false;
    }
        
    public void initSnakeLocalisation(int rootPointX, int rootPointY) {
        for (int z = 0; z < getLength(); z++) {
            x[z] = rootPointX - z*Board.DOT_SIZE;
            y[z] = rootPointY;
        }
    }
    
    public boolean getLeftDirection() {
    	return leftDirection;
    }
    public void setLeftDirection(boolean bool) {
    	leftDirection = bool;
    }

    public boolean getRightDirection() {
    	return rightDirection;
    }
    public void setRightDirection(boolean bool) {
    	rightDirection = bool;
    }
    
    public boolean getUpDirection() {
    	return upDirection;
    }
    public void setUpDirection(boolean bool) {
    	upDirection = bool;
    }
    
    public boolean getDownDirection() {
    	return downDirection;
    }
    public void setDownDirection(boolean bool) {
    	downDirection = bool;
    }
    
    public int getLength() {
    	return dots;
    }
    
    public void setLength(int length) {
    	dots = length;
    }
    
    public void incrementLength() {
    	dots++;
    }
    
    public void printPositions(String text) {
    	System.out.print(text+"\t");
        for (int z = 0; z < getLength(); z++) {
            System.out.print("Point: "+z+" X: "+x[z]+" Y: "+y[z]+"\t");
        }
        System.out.println("");
    }
    
}
