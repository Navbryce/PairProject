
public class Coordinate {
	public int xVal, yVal;
	
	public Coordinate(int x, int y) {
		xVal = x;
		yVal = y;
	}
	
	public void setX(int x) {
		xVal = x;
	}
	
	public int getX() {
		return xVal;
	}
	
	public void setY(int y) {
		yVal = y;
	}
	
	public int getY() {
		return yVal;
	}
	
	public String toString() {
		return "(" + xVal + ", " + yVal + ")";
	}
}
