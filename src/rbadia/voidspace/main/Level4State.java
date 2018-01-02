package rbadia.voidspace.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

public class Level4State extends Level3State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8738640199070011353L;

	public Level4State(int level, MainFrame frame, GameStatus status, NewLevelLogic gameLogic,
			InputHandler inputHandler, NewGraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		// TODO Auto-generated constructor stub
	}
	
	protected Ship bossShip;
	protected boolean shipUp = false;
	protected int levelBossShipHits = 0;
	protected Rectangle shipExplosion;
	
	public Ship getBossShip() {
		return bossShip;
	}

	@Override
	public boolean isLevelWon() {
		if(getInputHandler().isNPressed()) {
			return true;
		}
		return levelBossShipHits >= 5 && levelAsteroidsDestroyed >= 3;
	};
	
	@Override
	public void doStart() {	

		setStartState(START_STATE);
		setCurrentState(getStartState());
		// init game variables
		bullets = new ArrayList<Bullet>();
		bigBullets = new ArrayList<BigBullet>();
		//numPlatforms = new Platform[5];

		GameStatus status = this.getGameStatus();

		status.setGameOver(false);
		status.setNewAsteroid(false);

		// init the life and the asteroid
		newMegaMan();
		newBossShip();
		newFloor(this, 9);
		newPlatforms(getNumPlatforms());
		newAsteroid(this);

		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		lastLifeTime = -NEW_MEGAMAN_DELAY;

		bigFont = originalFont;
		biggestFont = null;

		// Display initial values for scores
		getMainFrame().getDestroyedValueLabel().setForeground(Color.BLACK);
		getMainFrame().getLivesValueLabel().setText(Integer.toString(status.getLivesLeft()));
		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed()));
		getMainFrame().getLevelValueLabel().setText(Long.toString(status.getLevel()));

	}
	
	@Override
	public void updateScreen(){
		Graphics2D g2d = getGraphics2D();
		GameStatus status = this.getGameStatus();

		// save original font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		clearScreen();
		drawStars(50);
		drawFloor();
		drawPlatforms();
		drawMegaMan();
		drawNewBossShip();
		moveShipVertically(bossShip);
		drawAsteroid();
		drawBullets();
		drawBigBullets();
		checkBullletAsteroidCollisions();
		checkBullletShipCollisions();
		checkBigBulletAsteroidCollisions();
		checkMegaManAsteroidCollisions();
		checkAsteroidFloorCollisions();

		// update asteroids destroyed (score) label  
		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed()));
		// update lives left label
		getMainFrame().getLivesValueLabel().setText(Integer.toString(status.getLivesLeft()));
		//update level label
		getMainFrame().getLevelValueLabel().setText(Long.toString(status.getLevel()));
	}
	
	public Ship newBossShip(){
		this.bossShip = new Ship((getWidth() - Ship.WIDTH) / 2, (getHeight() - Ship.HEIGHT - Ship.Y_OFFSET) / 2);
		bossShip.y = (int) (getHeight()/2 - bossShip.getHeight());
		bossShip.x = (int) (getWidth() - bossShip.getWidth());
		return bossShip;
	}
	
	protected void drawNewBossShip() {
		Graphics2D g2d = getGraphics2D();
		((NewGraphicsManager) getGraphicsManager()).drawShip(bossShip, g2d, this);
	}
	
	protected void moveShipVertically(Ship ship) {	
		if(touchUpperScreen(ship)){
			shipUp = false;
			ship.translate(0, ship.getSpeed());
		}
		else if(touchBottomScreen(ship)) {
			shipUp = true;
			ship.translate(0, -ship.getSpeed());
		}
		else if(!touchUpperScreen(ship) && !touchBottomScreen(ship)){
			if(shipUp) {
				ship.translate(0, -ship.getSpeed());
			}
			else if(!shipUp) {
				ship.translate(0, ship.getSpeed());
			}
		}
		
		
	}
	
	protected boolean touchUpperScreen(Ship ship){
		if(ship.getY() <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	protected boolean touchBottomScreen(Ship ship){
		if(ship.getY() >= getHeight()-ship.getHeight()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	protected void checkBullletShipCollisions() {
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(bossShip.intersects(bullet)){
				levelBossShipHits++;
				bullets.remove(i);
				if(levelBossShipHits>=5) {
					removeShip(bossShip);
				}
				break;
			}
		}
	}
	
	public void removeShip(Ship ship){
		shipExplosion = new Rectangle(
				ship.x,
				ship.y,
				ship.getPixelsWide(),
				ship.getPixelsTall());
		ship.setLocation(-ship.getPixelsWide(), -ship.getPixelsTall());
		this.getSoundManager().playShipExplosionSound();
	}
	
}
