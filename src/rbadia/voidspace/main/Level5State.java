package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.BossBullets;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.MegaMan;
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
	protected List<Bullet> shipBullets1;
	protected List<Bullet> shipBullets2;
	protected List<Bullet> shipBullets3;
	protected List<Bullet> shipBullets4;
	protected List<BigBullet> shipBigBullets;
	protected List<ArrayList<Bullet>> arrayOfShipBullets = new ArrayList<ArrayList<Bullet>>();
	protected boolean shipUp = false;
	protected int levelBossShipHits = 0;
	protected int[] levelShipHits = new int[4];
	protected int counter = 0;
	protected int firingCounter = 0;
	protected long lastShipBossLifeTime = 0;
	protected Rectangle shipExplosion;
	
	public Ship getBossShip() {
		return bossShip;
	}

	/*
	 * If boss health is depleted, all 4 minions are dead and the number of asteroids destroyed is 18, the level is won
	 */
	@Override
	public boolean isLevelWon() {
		if(getInputHandler().isNPressed()) {
			return true;
		}
		return counter >= 4 && levelBossShipHits >= 45 && levelAsteroidsDestroyed >= 23;
	};
	
	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
		shipBullets = new ArrayList<Bullet>();
		shipBigBullets = new ArrayList<BigBullet>();
		shipBullets1 = new ArrayList<Bullet>();
		shipBullets2 = new ArrayList<Bullet>();
		shipBullets3 = new ArrayList<Bullet>();
		shipBullets4 = new ArrayList<Bullet>();
		arrayOfShipBullets.add((ArrayList<Bullet>) shipBullets1);
		arrayOfShipBullets.add((ArrayList<Bullet>) shipBullets1);
		arrayOfShipBullets.add((ArrayList<Bullet>) shipBullets1);
		arrayOfShipBullets.add((ArrayList<Bullet>) shipBullets1);
		
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
		this.drawStars(50);
		this.drawFloor();
		this.drawPlatforms();
		this.drawMegaMan();
		this.drawAsteroid();
		this.drawAsteroid2();
		this.drawBigAsteroid();
		this.drawBullets();
		this.drawBigBullets();
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
		drawShipBullets(shipBullets1);
		drawShipBullets(shipBullets2);
		drawShipBullets(shipBullets3);
		drawShipBullets(shipBullets4);
		drawNewBossShip();
		drawBossShipBullets();
		drawBossShipBigBullets();
		this.checkBulletShipCollisions(ships);
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
		checkBulletBossShipCollisions();
		checkBulletShipCollisions(ships);
		checkBigBulletBossShipCollisions();
		checkBigBulletShipCollisions(ships);
		checkBossShipBulletMegaManCollisions();
		checkBossShipBigBulletMegaManCollisions();
		checkShipBulletMegaManCollisions(shipBullets1);
		checkShipBulletMegaManCollisions(shipBullets2);
		checkShipBulletMegaManCollisions(shipBullets3);
		checkShipBulletMegaManCollisions(shipBullets4);
		
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
		Timer timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(counter<4) {
					fireShipBossBullet();
					fireShipBullet(shipBullets1, ships[0]);
					fireShipBullet(shipBullets2, ships[1]);
					fireShipBullet(shipBullets3, ships[2]);
					fireShipBullet(shipBullets4, ships[3]);
				}
				
				else {
					fireShipBossBigBullet();
					fireShipBullet(shipBullets1, ships[0]);
					fireShipBullet(shipBullets2, ships[1]);
					fireShipBullet(shipBullets3, ships[2]);
					fireShipBullet(shipBullets4, ships[3]);
				}
			
			}});
		 timer.start();
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
	
	/*
	 * Checks if bullets hit (minions/array)
	 */
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
	
	/*
	 * Checks if BigBullet hit the boss ship.
	 */
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
	
	/*
	 * Checks if BigBullet hit a ship (minions/array)
	 */
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
	
	/*
	 * Removes ships after being destroyed by "transporting them" out of frame. 
	 * The location was a bit further (2x) to prevent player being able to hit after being destroyed.
	 */
	public void removeShip(Ship ship){
		shipExplosion = new Rectangle(
				ship.x,
				ship.y,
				ship.getPixelsWide(),
				ship.getPixelsTall());
		ship.setLocation(1000000, 1000000);
		this.getSoundManager().playShipExplosionSound();
	}
	
	/*
	 * Fires boss regular bullet every few seconds
	 */
	public void fireShipBossBullet(){
		if(firingCounter>=1000000000) {//prevents it from going over int cap
			firingCounter = 0;
		}
		if(firingCounter % 80000 == 0){
			int xPos = bossShip.x - Bullet.WIDTH / 2;
			int yPos = bossShip.y + bossShip.width/2 - Bullet.HEIGHT + 4;
			Bullet shipBullet = new Bullet(xPos, yPos);
			shipBullets.add(shipBullet);
			this.getSoundManager().playBulletSound();
			firingCounter++;
		}
		else {
			firingCounter++;
		}
	}
	
	/*
	 * Fires boss big bullet every few seconds
	 */
	public void fireShipBossBigBullet(){
		if(firingCounter>=1000000000) {//prevents it from going over int cap
			firingCounter = 0;
		}
		if(firingCounter % 80000 == 0){
			int xPos = bossShip.x - Bullet.WIDTH / 2;
			int yPos = bossShip.y + bossShip.width/2 - Bullet.HEIGHT + 4;
			BigBullet shipBigBullet = new BigBullet(xPos, yPos);
			shipBigBullets.add(shipBigBullet);
			this.getSoundManager().playBulletSound();
			firingCounter++;
		}
		else {
			firingCounter++;
		}
	}
	
	/*
	 * Moves regular bullets from a stationary position to the left
	 */
	public boolean moveShipBullet(Bullet bullet){
		if(bullet.getX() + bullet.getSpeed() > 0){
			bullet.translate(-bullet.getSpeed(), 0);
			return false;
		}
		else{
			return true;
		}
	}
	
	/*
	 * Moves big bullets from a stationary position to the left
	 */
	public boolean moveShipBigBullet(BigBullet bigBullet){
		if(bigBullet.getX() + bigBullet.getSpeed() > 0){
			bigBullet.translate(-bigBullet.getSpeed(), 0);
			return false;
		}
		else{
			return true;
		}
	}
	
	/*
	 * Checks if boss hits megaman with regular bullets, removes one life
	 */
	protected void checkBossShipBulletMegaManCollisions() {
		GameStatus status = getGameStatus();
		for(int i=0; i<shipBullets.size(); i++){
			Bullet bullet = shipBullets.get(i);
			if(bullet.intersects(megaMan)){
				status.setLivesLeft(status.getLivesLeft() - 1);
				damage=0;
				shipBullets.remove(i);
				break;
			}
		}
	}
	
	/*
	 * Checks if boss hits megaman with big bullets, removes 2 lives
	 */
	protected void checkBossShipBigBulletMegaManCollisions() {
		GameStatus status = getGameStatus();
		for(int i=0; i<shipBigBullets.size(); i++){
			BigBullet bigBullet = shipBigBullets.get(i);
			if(bigBullet.intersects(megaMan)){
				status.setLivesLeft(status.getLivesLeft() - 2);
				damage=0;
				shipBigBullets.remove(i);
				break;
			}
		}
	}
	
	/*
	 * Draw boss ship regular bullets
	 */
	protected void drawBossShipBullets() {
		Graphics2D g2d = getGraphics2D();
		for(int i=0; i<shipBullets.size(); i++){
			Bullet bullet = shipBullets.get(i);
			getGraphicsManager().drawBullet(bullet, g2d, this);
			boolean remove =   this.moveShipBullet(bullet);
			if(remove){
				shipBullets.remove(i);
				i--;
			}
		}
	}

	/*
	 * Draw boss ship big bullets
	 */
	protected void drawBossShipBigBullets() {
		Graphics2D g2d = getGraphics2D();
		for(int i=0; i<shipBigBullets.size(); i++){
			BigBullet bigBullet = shipBigBullets.get(i);
			getGraphicsManager().drawBigBullet(bigBullet, g2d, this);
			boolean remove =   this.moveShipBigBullet(bigBullet);
			if(remove){
				shipBigBullets.remove(i);
				i--;
			}
		}
	}
	
	/*
	 * Draw the minion ship bullets
	 */
	protected void drawShipBullets(List<Bullet> shipBullets) {
		Graphics2D g2d = getGraphics2D();
		for(int i=0; i<shipBullets.size(); i++){
			Bullet bullet = shipBullets.get(i);
			getGraphicsManager().drawBullet(bullet, g2d, this);
			boolean remove =   this.moveShipBullet(bullet);
			if(remove){
				shipBullets.remove(i);
				i--;
			}
		}
	}
	
	
//	public void fireShipBullet(Ship[] ships){
//		if(firingCounter>=1000000000) {//prevents it from going over int cap
//			firingCounter = 0;
//		}
//		if(firingCounter % 80000 == 0){
//			for(int i = 0; i<ships.length; i++) {
//				int xPos = ships[i].x - Bullet.WIDTH / 2;
//				int yPos = ships[i].y + ships[i].width/2 - Bullet.HEIGHT + 4;
//				Bullet shipBullet = new Bullet(xPos, yPos);
//				arrayOfShipBullets.get(i).add(shipBullet);
//				this.getSoundManager().playBulletSound();
//			}
//		}
//	}
	
	/*
	 * Makes ship/minions fire
	 */
	public void fireShipBullet(List<Bullet> shipBullets, Ship ship){
		if(firingCounter>=1000000000) {//prevents it from going over int cap
			firingCounter = 0;
		}
		if(firingCounter % 120000 == 0){
			int xPos = ship.x - Bullet.WIDTH / 2;
			int yPos = ship.y + ship.width/2 - Bullet.HEIGHT + 4;
			Bullet shipBullet = new Bullet(xPos, yPos);
			shipBullets.add(shipBullet);
			this.getSoundManager().playBulletSound();
		}
	}
	
	/*
	 * Checks if minion ships hit megaman
	 */
	protected void checkShipBulletMegaManCollisions(List<Bullet> shipBullets) {
		GameStatus status = getGameStatus();
		for(int i=0; i<shipBullets.size(); i++){
			Bullet bullet = shipBullets.get(i);
			if(bullet.intersects(megaMan)){
				status.setLivesLeft(status.getLivesLeft() - 1);
				damage=0;
				shipBullets.remove(i);
				break;
			}
		}
	}
}
