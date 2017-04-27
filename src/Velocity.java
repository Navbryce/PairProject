import java.util.Random;


public class Velocity {
	private int velocityX;
	private int velocityY;
	private Random random = new Random();
	
	public Velocity(int xVelocity, int yVelocity){
		velocityX = xVelocity;
		velocityY = yVelocity;
	}
	public Velocity(){
		//Magnitudes, not directions
		int maxVelocity=20; 
		int minVelocity=8;
		
		velocityX = random.nextInt(maxVelocity-minVelocity) + minVelocity;
		velocityY = random.nextInt(maxVelocity-minVelocity) + minVelocity;
		
		if(random.nextInt(2)==0){
			velocityX=velocityX*-1;
		}
		if(random.nextInt(2)==0){
			velocityY=velocityY*-1;
		}
		
		
	}
	public int getVelocityX(){
		return velocityX;
	}
	public int getVelocityY(){
		return velocityY;
	}
	public void setVelocityX(int newVelocity){
		velocityX = newVelocity;
	}
	public void setVelocityY(int newVelocity){
		velocityY = newVelocity;
	}
}
