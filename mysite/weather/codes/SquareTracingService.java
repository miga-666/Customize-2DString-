//package squaretracing;
 
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SquareTracingService {
    
    public List getContourPoints(int[][] srcImage) {
        int[][] image = srcImage.clone();
        clearBorder(image);
        List points = new LinkedList();
        Point startingPoint = getStartingPoint(image);
        Point currentPoint = startingPoint.getClone();
 
        do {
            if (image[currentPoint.getX()][currentPoint.getY()] == 1) {
                points.add(currentPoint.getClone().getX());
                points.add(currentPoint.getClone().getY());
                currentPoint.advanceToLeft();
            } else {
                currentPoint.advanceToRight();
            }
 
        } while (!startingPoint.equals(currentPoint));
        
        return points;
    }
 
    private Point getStartingPoint(int[][] image) {
 
        for (int y = image.length - 1; y >= 0; y--) {
 
            for (int x = image[y].length - 1; x >= 0; x--) {
 
                if (image[x][y] == 1) {
                    return new Point(x, y);
                }
 
            }
        }
 
        return null;
    }
 
    private void clearBorder(int[][] image) {
 
        for (int y = 0; y < image.length; y++) {
            for (int x = 0; x < image[y].length; x++) {
                if (y == 0 || x == 0 || y == image.length - 1 || x == image[y].length - 1) {
                    image[x][y] = 0;
                }
            }
        }
    }
}