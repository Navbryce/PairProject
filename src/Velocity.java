import java.util.Random;


public class Velocity {
	private double velocityX;
	private double velocityY;
	private Random random = new Random();
	
	public Velocity(double xVelocity, double yVelocity){
		velocityX = xVelocity;
		velocityY = yVelocity;
	}
	public Velocity(){
		//Magnitudes, not directions
		double maxVelocity=20; 
		double minVelocity=5;
		
		velocityX = random.nextDouble()*(maxVelocity-minVelocity) + minVelocity;
		velocityY = random.nextDouble()*(maxVelocity-minVelocity) + minVelocity;
		
		if(random.nextInt(1)==0){
			velocityX=velocityX*-1;
		}
		if(random.nextInt(1)==0){
			velocityY=velocityY*-1;
		}
		
		
	}
}
