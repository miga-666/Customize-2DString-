//package squaretracing;
 

public class Point {

    public static final int DIR_NORTH = 0;
    public static final int DIR_EAST = 1;
    public static final int DIR_SOUTH = 2;
    public static final int DIR_WEST = 3;
    private int x;
    private int y;
    private int direction;
 
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = DIR_NORTH;
    }

    public Point(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
 
    public Point(Point refPoint) {
        this.x = refPoint.x;
        this.y = refPoint.y;
        this.direction = refPoint.direction;
    }
 
    public void faceRight() {
 
        direction = (direction + 1) % 4;
 
    }
 
    private void faceLeft() {
        if (direction == 0) {
            direction = 3;
        } else {
            direction--;
        }
    }
 
    private void goForward() {
        if (direction == DIR_NORTH) {
            y = y - 1;
        }
        if (direction == DIR_EAST) {
            x = x + 1;
        }
        if (direction == DIR_SOUTH) {
            y = y + 1;
        }
        if (direction == DIR_WEST) {
            x = x - 1;
        }
 
    }

    public void advanceToLeft() {
        faceLeft();
        goForward();
    }
 
    public void advanceToRight() {
        faceRight();
        goForward();
    }
 
    public Point getClone() {
        return new Point(this);
    }
 
    public int getDirection() {
        return direction;
    }
 
    public int getX() {
        return x;
    }
 
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
 
}