package rbadia.voidspace.model;

/**
 * Represents a bullet fired by a ship.
 */
public class BossBullets extends GameObject {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 2;
	public static final  int WIDTH = 16;
	public static final int HEIGHT = 16;
	
	public BossBullets(int xPos, int yPos) {
		super(xPos, yPos, WIDTH, HEIGHT);
		this.setSpeed(15);
	}
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}
