package rbadia.voidspace.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.sounds.SoundManager;

public class NewLevel1State extends Level1State {

	private static final long serialVersionUID = -4781697848857966373L;

	protected Asteroid asteroid2;
	protected Asteroid bigAsteroid;
	//Rectangle bigAsteroidExplosion;
	
	ImageIcon Img = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/BackLevel1.png"));
	
	public NewLevel1State(int level, MainFrame frame, GameStatus status, LevelLogic gameLogic,
			InputHandler inputHandler, GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		// TODO Auto-generated constructor stub
	}
	
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
		status.setNewAsteroid2(false);
		status.setNewBigAsteroid(false);

		// init the life and the asteroid
		newMegaMan();
		newFloor(this, 9);
		newPlatforms(getNumPlatforms());
		newAsteroid(this);
		newAsteroid2(this);
		BigAsteroid(this);

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
	public void doInitialScreen() {
		setCurrentState(INITIAL_SCREEN);
		clearScreen();
		getGameLogic().drawInitialMessage();
	};
	
	//@Override//////////////////////////////////////////////////Esta pintando el background del level 1
	protected void clearScreen2() {
		// clear screen
		Graphics2D g2d = getGraphics2D();
		g2d.drawImage(this.Img.getImage(), 0, 0,getWidth(), getHeight(), null);		
	}
	
	@Override
	public void doPlaying() {
		setCurrentState(PLAYING);
		updateScreen();
	};
	
	@Override
	public void doLevelWon(){
		setCurrentState(LEVEL_WON);
		getGameLogic().drawYouWin();
		repaint();
		LevelLogic.delay(3000);
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

		this.clearScreen2();///////////////////////Pintando el background del level 1
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
		getMainFrame().getDestroyedValueLabel().setText(Long.toString(status.getAsteroidsDestroyed()));
		// update lives left label
		getMainFrame().getLivesValueLabel().setText(Integer.toString(status.getLivesLeft()));
		//update level label
		getMainFrame().getLevelValueLabel().setText(Long.toString(status.getLevel()));
	}
	
	@Override
	protected void drawMegaMan() {
		//draw one of three possible MegaMan poses according to situation
		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		if(!status.isNewMegaMan()){
			if(((Gravity() == true) || ((Gravity() == true) && (Fire() == true || Fire2() == true))) && megaMan.getDirection() != 180){
				getGraphicsManager().drawMegaFallR(megaMan, g2d, this);
			}
			
			else if(((Gravity() == true) || ((Gravity() == true) && (Fire() == true || Fire2() == true))) && megaMan.getDirection() == 180){
				((NewGraphicsManager) getGraphicsManager()).drawMegaFallRFlipped(megaMan, g2d, this);
			}
		}

		if(((Fire() == true || Fire2()== true) && (Gravity()==false)) && megaMan.getDirection() != 180){
			getGraphicsManager().drawMegaFireR(megaMan, g2d, this);
		}
		
		else if(((Fire() == true || Fire2()== true) && (Gravity()==false)) && megaMan.getDirection() == 180){
			((NewGraphicsManager) getGraphicsManager()).drawMegaFireRFlipped(megaMan, g2d, this);
		}

		if((Gravity()==false) && (Fire()==false) && (Fire2()==false) && megaMan.getDirection() != 180){
			getGraphicsManager().drawMegaMan(megaMan, g2d, this);
			megaMan.setDirection(0);
		}
		
		else if(((Gravity()==false) && (Fire()==false) && (Fire2()==false)) && megaMan.getDirection() == 180){
			((NewGraphicsManager) getGraphicsManager()).drawMegaManFlipped(megaMan, g2d, this);
			megaMan.setDirection(180);
		}
	};
	
	@Override
	public boolean moveBullet(Bullet bullet){
		
		if(bullet.getX() + bullet.getSpeed() > 0 && bullet.getDirection() == 180){
			bullet.translate(-bullet.getSpeed(), 0);
			return false;
		}
		else if(bullet.getX() + bullet.getSpeed() + bullet.getWidth() < getWidth() && bullet.getDirection() == 0){
			bullet.translate(bullet.getSpeed(), 0);
			return false;
		}
		else{
			return true;
		}
	};
	
	@Override
	public boolean moveBigBullet(BigBullet bigBullet){
		if(bigBullet.getX() + bigBullet.getSpeed() > 0 && bigBullet.getDirection() == 180){
			bigBullet.translate(-bigBullet.getSpeed(), 0);
			return false;
		}
		else if(bigBullet.getX() + bigBullet.getSpeed() + bigBullet.getWidth() < getWidth() && bigBullet.getDirection() == 0){
			bigBullet.translate(bigBullet.getSpeed(), 0);
			return false;
		}
		else{
			return true;
		}
	};
	
	@Override
	protected boolean Fire(){
		MegaMan megaMan = this.getMegaMan();
		List<Bullet> bullets = this.getBullets();
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if((bullet.getX() <= megaMan.getX() + megaMan.getWidth() + 60) && 
					(bullet.getX() >= megaMan.getX() - 60)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected boolean Fire2(){
		MegaMan megaMan = this.getMegaMan();
		List<BigBullet> bigBullets = this.getBigBullets();
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if((bigBullet.getX() <= megaMan.getX() + megaMan.getWidth() + 60) && 
					(bigBullet.getX() >= megaMan.getX() - 60)){
				return true;
			}
		}
		return false;
	}

	
	@Override
	public void fireBullet(){
		if(megaMan.getDirection()==180) 
		{
			Bullet bullet2 = new Bullet (megaMan.x,megaMan.y + megaMan.width/2 - Bullet.HEIGHT+2);
			bullet2.setDirection(megaMan.getDirection());
			bullets.add(bullet2);
		}
		else {
		Bullet bullet = new Bullet(megaMan.x + megaMan.width - Bullet.WIDTH/2,
					   megaMan.y + megaMan.width/2 - Bullet.HEIGHT +2);
		bullet.setDirection(megaMan.getDirection());
		bullets.add(bullet);
		this.getSoundManager().playBulletSound();
	}
	}
	
	@Override
	public void fireBigBullet(){
		//BigBullet bigBullet = new BigBullet(megaMan);
		if(megaMan.getDirection()==180) 
		{
			BigBullet bigBullet2 = new BigBullet (megaMan.x, megaMan.y + megaMan.width/2 - BigBullet.HEIGHT+2);
			bigBullet2.setDirection(megaMan.getDirection());
			bigBullets.add(bigBullet2);
		}
		else 
		{
			int xPos = megaMan.x + megaMan.width - BigBullet.WIDTH / 2;
			int yPos = megaMan.y + megaMan.width/2 - BigBullet.HEIGHT + 4;
			BigBullet  bigBullet = new BigBullet(xPos, yPos);
			bigBullet.setDirection(megaMan.getDirection());
			bigBullets.add(bigBullet);
			this.getSoundManager().playBulletSound();
		}
	}
	
	@Override
	public void moveMegaManLeft(){
		if(megaMan.getX() - megaMan.getSpeed() >= 0){
			megaMan.translate(-megaMan.getSpeed(), 0);
			megaMan.setDirection(180);
		}
	};
	
	@Override
	public void moveMegaManRight(){
		if(megaMan.getX() + megaMan.getSpeed() + megaMan.width < getWidth()){
			megaMan.translate(megaMan.getSpeed(), 0);
			megaMan.setDirection(0);
		}
	};
	
	public Asteroid newAsteroid2 (NewLevel1State screen){
		int xPos = (int) (screen.getWidth() - Asteroid.WIDTH);
		int yPos = rand.nextInt((int)(screen.getHeight() - Asteroid.HEIGHT- 32));
		asteroid2 = new Asteroid(xPos, yPos);
		return asteroid2;
	}
	
	protected void drawAsteroid2() {
		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		if((asteroid2.getX() + asteroid2.getPixelsWide() >  0)) {
			asteroid2.translate(-asteroid2.getSpeed(), asteroid2.getSpeed()/2);
			((NewGraphicsManager)getGraphicsManager()).drawAsteroid2(asteroid2, g2d, this);
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){

				lastAsteroidTime = currentTime;
				status.setNewAsteroid2(false);
				asteroid2.setLocation(this.getWidth() - asteroid2.getPixelsWide(),
						rand.nextInt(this.getHeight() - asteroid2.getPixelsTall() - 32));
			}
			else {
				// draw explosion
				((NewGraphicsManager) getGraphicsManager()).drawAsteroidExplosion2(asteroidExplosion, g2d, this);
			}
		}	
	}
	
	public Asteroid BigAsteroid (NewLevel1State screen){
		int xPos = (int) (screen.getWidth() - Asteroid.WIDTH);
		int yPos = rand.nextInt((int)(screen.getHeight() - Asteroid.HEIGHT- 32));
		bigAsteroid = new Asteroid(xPos, yPos);
		return bigAsteroid;
	}
	
	protected void drawBigAsteroid() {
		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		if(bigAsteroid.getX() + bigAsteroid.getPixelsWide() > 0) {
			bigAsteroid.translate(-bigAsteroid.getSpeed(),bigAsteroid.getSpeed()/2);
			((NewGraphicsManager)getGraphicsManager()).drawBigAsteroid(bigAsteroid, g2d, this);
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){

				//lastAsteroidTime = currentTime;
				status.setNewBigAsteroid(false);
				bigAsteroid.setLocation(this.getWidth()-bigAsteroid.getPixelsWide(),
						   rand.nextInt(this.getHeight() - bigAsteroid.getPixelsTall() - 64));
			}
			else {
				// draw explosion
				((NewGraphicsManager) getGraphicsManager()).drawBigAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}	
	}

	
	protected void checkBullletAsteroidCollisions2() {
		GameStatus status = getGameStatus();
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(asteroid2.intersects(bullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 100);
				removeAsteroid(asteroid2);
				levelAsteroidsDestroyed++;
				damage=0;
				// remove bullet
				bullets.remove(i);
				break;
			}
		}
	}
	
	protected void checkBullletBigAsteroidCollisions() {
		GameStatus status = getGameStatus();
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(bigAsteroid.intersects(bullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 200);
				removeAsteroid(bigAsteroid);
				levelAsteroidsDestroyed++;
				damage=0;
				// remove bullet
				bullets.remove(i);
				break;
			}
		}
	}
	
	
	protected void checkBigBulletBigAsteroidCollisions() {
		GameStatus status = getGameStatus();
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if(bigAsteroid.intersects(bigBullet)){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 300);
				removeAsteroid(bigAsteroid);
				damage=0;
			}
		}
	}
	
	protected void checkMegaManAsteroidCollisions2() {
		GameStatus status = getGameStatus();
		if(asteroid2.intersects(megaMan)){
			status.setLivesLeft(status.getLivesLeft() - 1);
			removeAsteroid(asteroid2);
		}
	}
	
	protected void checkMegaManBigAsteroidCollisions() {
		GameStatus status = getGameStatus();
		if(bigAsteroid.intersects(megaMan)){
			if(status.getLivesLeft() > 1)
			status.setLivesLeft(status.getLivesLeft() - 2);
			else {status.setLivesLeft(0);}
			removeAsteroid(bigAsteroid);
		}
	}
	
	protected void checkAsteroidFloorCollisions2() {
		for(int i=0; i<9; i++){
			if(asteroid2.intersects(floor[i])){
				removeAsteroid(asteroid2);
			}
		}
	}
	
	protected void checkBigAsteroidFloorCollisions() {
		for(int i=0; i<9; i++){
			if(bigAsteroid.intersects(floor[i])){
				removeAsteroid(bigAsteroid);
			}
		}
	}
	
	@Override
	protected void drawFloor() {
		//draw Floor
		Graphics2D g2d = getGraphics2D();
		for(int i=0; i<9; i++){
			getGraphicsManager().drawFloor(floor[i], g2d, this, i);	
		//((NewGraphicsManager) getGraphicsManager()).drawFloor2(floor[i], g2d, this, i);	///////////Opcion para cambiar el floor, recordar camniar esta basura
		}
	}

	@Override
	public void doGameOver(){
		this.getGameStatus().setGameOver(true);
	}
	
	@Override
	public void doGameOverScreen(){
		setCurrentState(GAME_OVER_SCREEN);
		getGameLogic().drawGameOver();
		getMainFrame().getDestroyedValueLabel().setForeground(new Color(128, 0, 0));
		repaint();
		LevelLogic.delay(1500);
	}
	
	@Override
	public boolean isLevelWon() {
		if(getInputHandler().isNPressed()) {
			return true;
		}
		return levelAsteroidsDestroyed >= 3;
	};
	
}