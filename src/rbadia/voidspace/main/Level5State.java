package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

public class Level5State extends Level3State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8738640199070011353L;

	public Level5State(int level, MainFrame frame, NewGameStatus status, NewLevelLogic gameLogic,
			InputHandler inputHandler, NewGraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
	}
	
	protected int numPlatforms=20;
	public int getNumPlatforms() {return numPlatforms;}
	protected Ship bossShip;
	protected Ship ship1;
	protected Ship ship2;
	protected Ship ship3;
	protected Ship ship4;
	protected Ship[] ships = {ship1, ship2, ship3, ship4};
	protected List<Bullet> shipBullets;
	protected List<BigBullet> shipBigBullets;
	protected boolean shipUp = false;
	protected int levelBossShipHits = 0;
	protected int[] levelShipHits = new int[4];
	protected int counter = 0;
	protected Rectangle shipExplosion;
	
	public Ship getBossShip() {
		return bossShip;
	}

	/*
	 * If boss health is depleted, all 4 minions are ddead and the number of asteroids destroyed is 18, the level is won
	 */
	@Override
	public boolean isLevelWon() {
		if(getInputHandler().isNPressed()) {
			return true;
		}
		return counter >= 4 && levelBossShipHits >= 45 && levelAsteroidsDestroyed >= 18;
	};
	
	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
		//for loop to create 4 ship objects.
		for(int i = 0; i<4; i++) {
			ships[i] = newShip();
		}
		newBossShip();
		newPlatforms(getNumPlatforms());
		GameStatus status = this.getGameStatus();
		//Displays both asteroid score and ship score
		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed() + ((NewGameStatus) status).getShipsDestroyed()));
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
		
		this.clearScreen();
		drawNewBossShip();
		if(counter>=2) {
			moveShipVertically(bossShip);
		}
		else {
			bossShip.y = 2*(getHeight()/5);
		}
		for(int i = 0; i<4; i++) {
			drawNewShip(ships[i]);
			if(i<2) {
				ships[i].y = i*(getHeight()/5);
			}
			else if(i>=2) {
				ships[i].y = (i+1)*(getHeight()/5);
			}
		}		
		checkBulletBossShipCollisions();
		checkBulletShipCollisions(ships);
		checkBigBulletBossShipCollisions();
		checkBigBulletShipCollisions(ships);
		this.drawStars(50);
		this.drawFloor();
		this.drawPlatforms();
		this.drawMegaMan();
		this.drawAsteroid();
		this.drawAsteroid2();
		this.drawBigAsteroid();
		this.drawBullets();
		this.drawBigBullets();
		this.checkBullletAsteroidCollisions();
		this.checkBullletAsteroidCollisions2();
		this.checkBullletBigAsteroidCollisions();
		this.checkBigBulletAsteroidCollisions();
		this.checkBigBulletBigAsteroidCollisions();
		this.checkMegaManAsteroidCollisions();
		this.checkMegaManAsteroidCollisions2();
		this.checkMegaManBigAsteroidCollisions();
		this.checkAsteroidFloorCollisions();
		this.checkAsteroidFloorCollisions2();
		this.checkBigAsteroidFloorCollisions();
		
		// update asteroids destroyed (score) label  
		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed() + ((NewGameStatus) status).getShipsDestroyed()));
		// update lives left label
		getMainFrame().getLivesValueLabel().setText(Integer.toString(status.getLivesLeft()));
		//update level label
		getMainFrame().getLevelValueLabel().setText(Long.toString(status.getLevel()));
	};
	
	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		int j = 0;
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(0,0);
			if(i<10) platforms[i].setLocation(0, getHeight() - i*40);
			if(i>=10) {
				platforms[i].setLocation(this.getWidth()-100-j*50, getHeight()/2 + 160 - j*40);
				j++;
			}
		}	
		return platforms;
	};
	
	/*
	 * Creates boss ship object
	 */
	public Ship newBossShip(){
		this.bossShip = new Ship((getWidth() - Ship.WIDTH) / 2, (getHeight() - Ship.HEIGHT - Ship.Y_OFFSET) / 2);
		bossShip.y = (int) (getHeight()/2 - bossShip.getHeight());
		bossShip.x = (int) (getWidth() - bossShip.getWidth());
		return bossShip;
	}
	
	/*
	 * Creates minion ship object
	 */
	public Ship newShip(){
		Ship ship = new Ship((getWidth() - Ship.WIDTH) / 2, (getHeight() - Ship.HEIGHT - Ship.Y_OFFSET) / 2);
		ship.y = (int) (getHeight() - ship.getHeight());
		ship.x = (int) (getWidth() - ship.getWidth());
		return ship;
	}
	
	/*
	 * Draws boss ships
	 */
	protected void drawNewBossShip() {
		Graphics2D g2d = getGraphics2D();
		((NewGraphicsManager) getGraphicsManager()).drawBossShip(bossShip, g2d, this);
	}
	
	/*
	 * Draws regular minion ships
	 */
	protected void drawNewShip(Ship ship) {
		Graphics2D g2d = getGraphics2D();
		((NewGraphicsManager) getGraphicsManager()).drawShip(ship, g2d, this);
	}
	
	/*
	 * Uses a boolean variable to check if going up or down and resets direction accordingly
	 */
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
	
	/*
	 * Verifies if ship object touches the upper screen
	 */
	protected boolean touchUpperScreen(Ship ship){
		if(ship.getY() <= 0) {
			return true;
		}  
		else {
			return false;
		}
	}
	
	/*
	 * Verifies if ship object touches bottom screen
	 */
	protected boolean touchBottomScreen(Ship ship){
		if(ship.getY() >= getHeight()-ship.getHeight()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * Checks boss-bullet and minion-bullet collision and removes health from boss/minion according to ammo type
	 */
	protected void checkBulletBossShipCollisions(){
		GameStatus status = getGameStatus();
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(bossShip.intersects(bullet)){
				levelBossShipHits++;
				bullets.remove(i);
				if(levelBossShipHits>=45) {
					((NewGameStatus) status).setShipsDestroyed(((NewGameStatus) status).getShipsDestroyed() + 2000);
					removeShip(bossShip);
				}
				break;
			}
		}
	}
	
	protected void checkBulletShipCollisions(Ship[] ships) {
		GameStatus status = getGameStatus();
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
				for(int j = 0; j<levelShipHits.length; j++) {
					if(ships[j].intersects(bullet)){
						levelShipHits[j]++;
						bullets.remove(i);
					
					if(levelShipHits[j]>=15) {
						((NewGameStatus) status).setShipsDestroyed(((NewGameStatus) status).getShipsDestroyed() + 500);
						counter++;
						removeShip(ships[j]);
					}	
					break;
				}
			}
		}
	}
	
	protected void checkBigBulletBossShipCollisions() {
		GameStatus status = getGameStatus();
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if(bossShip.intersects(bigBullet)){
				levelBossShipHits+=15;
				bigBullets.remove(i);
				if(levelBossShipHits>=45) {
					((NewGameStatus) status).setShipsDestroyed(((NewGameStatus) status).getShipsDestroyed() + 2000);
					removeShip(bossShip);
				}
				break;
			}
		}
	}
	
	protected void checkBigBulletShipCollisions(Ship[] ships) {
		GameStatus status = getGameStatus();
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
				for(int j = 0; j<levelShipHits.length; j++) {
					if(ships[j].intersects(bigBullet)){
						levelShipHits[j]+=15;
						bigBullets.remove(i);
					if(levelShipHits[j]>=15) {
						((NewGameStatus) status).setShipsDestroyed(((NewGameStatus) status).getShipsDestroyed() + 500);
						counter++;
						removeShip(ships[j]);
					}	
					break;
				}
			}
		}
	}
	
	public void removeShip(Ship ship){
		shipExplosion = new Rectangle(
				ship.x,
				ship.y,
				ship.getPixelsWide(),
				ship.getPixelsTall());
		ship.setLocation(100000, 1000000);
		this.getSoundManager().playShipExplosionSound();
	}
}
