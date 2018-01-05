package rbadia.voidspace.model;

public class Ship extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1372118837970736302L;
	public static final int DEFAULT_SPEED = 2;
	public static final int Y_OFFSET = 0; // initial y distance of the ship from the bottom of the screen 
	
	public static final int WIDTH = 42;
	public static final int HEIGHT = 41;
	//public static final int speed = DEFAULT_SPEED;
	
	public Ship(int xPos, int yPos){
		super(xPos, yPos, WIDTH, HEIGHT);
		this.setSpeed(DEFAULT_SPEED);
	}
	
	public int gtInitialYOffset() {
		return Y_OFFSET;
	}
	
	/**
	 * Returns the default ship speed.
	 * @return the default ship speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}
