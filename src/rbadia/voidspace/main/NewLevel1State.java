package rbadia.voidspace.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.BossBullets;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.Floor;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

public class NewLevel1State extends Level1State {
	
	private static final long serialVersionUID = 105819711182316485L;
	
	protected Asteroid asteroid2;
	protected Asteroid bigAsteroid;
	protected Boss boss;
	protected int fireBossBullets = 0;
	protected boolean bossLeftCorner = false;
	protected Rectangle bossExplosion;
	protected List<BossBullets> bossBullets;
	protected int bossLifes = 25;
	protected long lastBossLifeTime = 0;
	public static final int NEW_BOSS = 10;
	protected static final int NEW_BOSS_DELAY = 500;
	
	public NewLevel1State(int level, MainFrame frame, GameStatus status, NewLevelLogic gameLogic,
			InputHandler inputHandler, NewGraphicsManager graphicsMan, SoundManager soundMan) {
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
		bossBullets = new ArrayList<BossBullets>();

		GameStatus status = this.getGameStatus();

		status.setGameOver(false);
		status.setNewAsteroid(false);
		status.setNewAsteroid2(false);
		status.setNewBigAsteroid(false);
		status.setNewBoss(false);

		// init the life and the asteroid
		newMegaMan();
		newFloor(this, 9);
		newPlatforms(getNumPlatforms());
		newAsteroid(this);
		newAsteroid2(this);
		BigAsteroid(this);
		newBoss();
		

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
		BackgroundImageMenu();
		getGameLogic().drawInitialMessage();
	};
	
	protected void BackgroundImageMenu() {
		// BackgroundImageMenu
		Graphics2D g2d = getGraphics2D();	
		((NewGraphicsManager) getGraphicsManager()).BacgroundImageMenu(g2d, getMainFrame(), this);
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

		this.clearScreen();
		this.drawStars(50);
		this.drawFloor();
		this.drawPlatforms();
		this.drawMegaMan();
		this.drawAsteroid();
		this.drawBullets();
		this.drawBigBullets();
		this.checkBullletAsteroidCollisions();
		this.checkBullletBigAsteroidCollisions();
		this.checkBigBulletAsteroidCollisions();
		this.checkMegaManAsteroidCollisions();
		this.checkAsteroidFloorCollisions();

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
		}
		
		else if(((Gravity()==false) && (Fire()==false) && (Fire2()==false)) && megaMan.getDirection() == 180){
			((NewGraphicsManager) getGraphicsManager()).drawMegaManFlipped(megaMan, g2d, this);
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
	public void fireBullet(){ 
		Bullet bullet = new Bullet(megaMan.x + megaMan.width - Bullet.WIDTH/2, 
		megaMan.y + megaMan.width/2 - Bullet.HEIGHT +2);
		bullet.setDirection(megaMan.getDirection()); 
		bullets.add(bullet); 
		this.getSoundManager().playBulletSound();
	};
	
	@Override 
	public void fireBigBullet(){ 
		//BigBullet bigBullet = new BigBullet(megaMan); 
		int xPos = megaMan.x + megaMan.width - BigBullet.WIDTH / 2; 
		int yPos = megaMan.y + megaMan.width/2 - BigBullet.HEIGHT + 4; 
		BigBullet  bigBullet = new BigBullet(xPos, yPos); 
		bigBullet.setDirection(megaMan.getDirection()); 
		bigBullets.add(bigBullet); 
		this.getSoundManager().playBulletSound(); 
	}; 
	
	@Override
	protected boolean Fire(){
		MegaMan megaMan = this.getMegaMan();
		List<Bullet> bullets = this.getBullets();
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if((bullet.getX() > megaMan.getX() + megaMan.getWidth()) && 
					(bullet.getX() <= megaMan.getX() + megaMan.getWidth() + 60)){
				return true;
			}
			
			else if((bullet.getX() > megaMan.getX()) && 
					(bullet.getX() <= megaMan.getX() + megaMan.getWidth() + 60)){
				return true;
			}
		}
		return false;
	};
	
	@Override
	protected boolean Fire2(){
		MegaMan megaMan = this.getMegaMan();
		List<BigBullet> bigBullets = this.getBigBullets();
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if((bigBullet.getX() > megaMan.getX() + megaMan.getWidth()) && 
					(bigBullet.getX() <= megaMan.getX() + megaMan.getWidth() + 60)){
				return true;
			}
			
			if((bigBullet.getX() > megaMan.getX()) && 
					(bigBullet.getX() <= megaMan.getX() + megaMan.getWidth() + 60)){
				return true;
			}
		}
		return false;
	};
	
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
			asteroid2.translate(-asteroid2.getSpeed()*2, asteroid2.getSpeed()/2);
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
			bigAsteroid.translate(-bigAsteroid.getSpeed(), bigAsteroid.getSpeed()/64);
			((NewGraphicsManager)getGraphicsManager()).drawBigAsteroid(bigAsteroid, g2d, this);
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){

				lastAsteroidTime = currentTime;
				status.setNewBigAsteroid(false);
				bigAsteroid.setLocation(this.getWidth() - bigAsteroid.getPixelsWide(),
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
			if((bigAsteroid.intersects(bigBullet))){
				// increase asteroids destroyed count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 300);
				removeAsteroid(bigAsteroid);
				levelAsteroidsDestroyed++;
				damage=0;
				// remove bullet
				bigBullets.remove(i);
				break;
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
	protected void clearScreen() {
		// clear screen
		Graphics2D g2d = getGraphics2D();
		((NewGraphicsManager) getGraphicsManager()).BackgroundImageLevel1(g2d, getMainFrame(), this);
	}
	
	@Override
	protected void drawFloor() {
		//draw Floor
		Graphics2D g2d = getGraphics2D();
		for(int i=0; i<9; i++){
		((NewGraphicsManager) getGraphicsManager()).drawFloor2(floor[i], g2d, this, i);
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	protected void drawBoss() {
		//draw one of three possible MegaMan poses according to situation
		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		if(!status.isNewBoss()){
///////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////Working in Boss Movements///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////
		   bossTargetMegaman();
		   moveBoss(getBoss());
		   Timer timer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fireBossBullet();
				}});
		   if(lastBossLifeTime < 23) 
		   {
			   
			   timer.start();
		   }
///////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////
			if(((bossGravity() == true) || ((bossGravity() == true) && (bossFire() == true))) && boss.getDirection() == 180){
				((NewGraphicsManager) getGraphicsManager()).drawBossFallRFlipped(boss, g2d, this);
			}
			else if(((bossGravity() == true) || ((bossGravity() == true) && (bossFire() == true))) && boss.getDirection() != 180){
				((NewGraphicsManager) getGraphicsManager()).drawBossFallR(boss, g2d, this);
			}
		}
		
		if(((bossFire() == true) && (bossGravity()==false)) && boss.getDirection() == 180){
			((NewGraphicsManager) getGraphicsManager()).drawBossFireRFlipped(boss, g2d, this);
		}		
		else if(((bossFire() == true) && (bossGravity()==false)) && boss.getDirection() != 180){
			((NewGraphicsManager) getGraphicsManager()).drawBossFireR(boss, g2d, this);
		}

		if(((bossGravity()==false) && (bossFire()==false)) && boss.getDirection() == 180){
			((NewGraphicsManager) getGraphicsManager()).drawBossFlipped(boss, g2d, this);
			//boss.setDirection(180);
		}
		
		else if((bossGravity()==false) && (bossFire()==false) && boss.getDirection() != 180){
			((NewGraphicsManager) getGraphicsManager()).drawBoss(boss, g2d, this);
			//boss.setDirection(0);
		}
	}

	public boolean touchLeftScreen(Boss boss){
		if(boss.getX() <= Boss.WIDTH){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean touchRightScreen(Boss boss){
		if(boss.getX() >= getWidth()-Boss.WIDTH-4){
			return true;
		}
		else{
			return false;
		}
	}
	public void bossTargetMegaman() 
	{
		//Boss always target Megaman
		if(megaMan.getX()<=boss.getX())
		{
			boss.setDirection(180);
		}
	   if( megaMan.getX()>=boss.getX())
		{
			boss.setDirection(0);
		}
	}
	
	public void moveBoss(Boss boss) 
	{		
		if(!touchLeftScreen(getBoss())) 
		{
			if(!bossLeftCorner) 
			{
				moveBossLeft();
			}
			else 
			{
				moveBossRight();
			}
		}
		else 
		{	
			bossLeftCorner = true;
			moveBossRight(); 
		}
		if(touchRightScreen(getBoss()))
		{
			bossLeftCorner = false;
			moveBossLeft();
		}
	}
	public Boss newBoss(){
		this.boss = new Boss((getWidth() - Boss.WIDTH), (getHeight() - Boss.HEIGHT - Boss.Y_OFFSET)/2);
		return boss;
	}
	
	public void bossLife() 
	{
		Graphics2D g2d = getGraphics2D();
		bossExplosion = new Rectangle(boss.x, boss.y, boss.getPixelsWide(), boss.getPixelsTall());
		
		Timer timer = new Timer(NEW_BOSS_DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeBoss(boss);
			}});
		
		if(getBossLife()<=0) 
		{
			((NewGraphicsManager) getGraphicsManager()).drawBossExplosion(bossExplosion, g2d, this);
			timer.start();
		}
	}
	
	public void setBossLife(int newLifes) 	{ this.bossLifes = newLifes; }
	
	public int getBossLife() { return this.bossLifes; }
	
	public Boss getBoss() { return boss; }
	
	public List<BossBullets> getBossBullets()		{ return this.bossBullets; }
	
	public void doNewBoss() {
		setCurrentState(NEW_BOSS);
	}
	
	public void removeBoss(Boss boss){
		// "remove" Boss
		bossExplosion = new Rectangle(
				boss.x,
				boss.y,
				boss.getPixelsWide(),
				boss.getPixelsTall());
		boss.setLocation(-boss.getPixelsWide()*2, -boss.getPixelsTall()*2);
		this.getGameStatus().setNewBoss(true);
		// play asteroid explosion sound
		this.getSoundManager().playAsteroidExplosionSound();
	}
	
	protected void checkBossAsteroidCollisions() {
		if(asteroid.intersects(boss)){
			removeAsteroid(asteroid);
		}
	}
	
	protected void checkBossAsteroidCollisions2() {
		if(asteroid2.intersects(boss)){
			removeAsteroid(asteroid2);
		}
	}
	
	protected void checkBossBigAsteroidCollisions() {
		if(bigAsteroid.intersects(boss)){
			removeAsteroid(bigAsteroid);
		}
	}
	
	protected void checkMegaManBulletBossCollisions() {
		
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if((boss.intersects(bullet))){
				//Boss Life decrease -1
				setBossLife(getBossLife()-1);
				bossLife();
				lastBossLifeTime++;
				// remove bullet
				bullets.remove(i);
				break;
			}	
		}
	}
	
	protected void checkMegaManBigBulletBossCollisions() {
		
		for(int i=0; i<bigBullets.size(); i++){
			BigBullet bigBullet = bigBullets.get(i);
			if((boss.intersects(bigBullet))){
				//Boss Life decrease -2
				setBossLife(getBossLife()-2);
				bossLife();
				lastBossLifeTime++;
				// remove bullet
				bigBullets.remove(i);
				break;
			}	
		}
	}
	
	protected void checkBossBulletMegaManCollisions() {
		
		GameStatus status = getGameStatus();
		
		for(int i=0; i<bossBullets.size(); i++){
			BossBullets bossBullet = bossBullets.get(i);
			if((megaMan.intersects(bossBullet))){
				//MegaMan Life decrease -2
				status.setLivesLeft(status.getLivesLeft() - 2);
				// remove bullet
				bossBullets.remove(i);
				break;
			}	
		}
	}
	
	protected boolean bossFire(){
		Boss boss = this.getBoss();
		List<BossBullets> bossBullet = this.getBossBullets();
		for(int i=0; i<bossBullet.size(); i++){
			BossBullets bossBullets = bossBullet.get(i);
			if((bossBullets.getX() <= boss.getX() + boss.getWidth() + 60) && 
					(bossBullets.getX() >= boss.getX() - 60)){
				return true;
				}
			}
			return false;
		}
	
	public void fireBossBullet(){
			if(boss.getDirection()==180) 
			{
				BossBullets bossBullet2 = new BossBullets (boss.x, boss.y + boss.width/2 - BossBullets.HEIGHT+2);
				bossBullet2.setDirection(boss.getDirection());
				bossBullets.add(bossBullet2);
				this.getSoundManager().playBulletSound();
				fireBossBullets++;
			}
			else 
			{
				int xPos = boss.x + boss.width - BossBullets.WIDTH / 2;
				int yPos = boss.y + boss.width/2 - BossBullets.HEIGHT + 4;
				BossBullets bossBullet = new BossBullets(xPos, yPos);
				bossBullet.setDirection(boss.getDirection());
				bossBullets.add(bossBullet);
				this.getSoundManager().playBulletSound();
				fireBossBullets++;
			}
		}
	
	protected void drawBossBullets() {
		List<BossBullets> bossBullets = this.getBossBullets();
		Graphics2D g2d = getGraphics2D();
		for(int i=0; i<bossBullets.size(); i++){
			BossBullets bossBullet = bossBullets.get(i);
			if(fireBossBullets <= 3 ) {
			((NewGraphicsManager) getGraphicsManager()).drawBossBullet(bossBullet, g2d, this);}
			else {fireBossBullets =0;}
			
			boolean remove = moveBossBullet(bossBullet);
			if(remove){
				bossBullets.remove(i);
				i--;
			}
		}
	}
	
	public boolean moveBossBullet(BossBullets bossBullet){
		if(bossBullet.getX() + bossBullet.getSpeed() > 0 && bossBullet.getDirection() == 180){
			bossBullet.translate(-bossBullet.getSpeed(), 0);
			return false;
		}
		else if(bossBullet.getX() + bossBullet.getSpeed() + bossBullet.getWidth() < getWidth() && bossBullet.getDirection() == 0){
			bossBullet.translate(bossBullet.getSpeed(), 0);
			return false;
		}
		else{
			return true;
		}
	}
	
	
	protected boolean bossGravity(){
		boss = this.getBoss();
		Floor[] floor = this.getFloor();

		for(int i=0; i<9; i++){
			if((boss.getY() + boss.getHeight() -17 < this.getHeight() - floor[i].getHeight()/2) 
					&& bossFall() == true){

				boss.translate(0 , 2);
				return true;
			}
		}
		return false;
	}
	
	public boolean bossFall(){
		boss = this.getBoss(); 
		Platform[] platforms = this.getPlatforms();
		for(int i=0; i<getNumPlatforms(); i++){
			if((((platforms[i].getX() < boss.getX()) && (boss.getX()< platforms[i].getX() + platforms[i].getWidth()))
					|| ((platforms[i].getX() < boss.getX() + boss.getWidth()) 
							&& (boss.getX() + boss.getWidth()< platforms[i].getX() + platforms[i].getWidth())))
					&& boss.getY() + boss.getHeight() == platforms[i].getY()){
				return false;
			}
		}
		return true;
	}
	
	public void moveBossUp(){
		if(boss.getY() - boss.getSpeed() >= 0){
			boss.translate(0, -boss.getSpeed()*2);
		}
	}
	
	public void moveBossDown(){
		for(int i=0; i<9; i++){
			if(boss.getY() + boss.getSpeed() + boss.height < getHeight() - floor[i].getHeight()/2){
				boss.translate(0, 2);
			}
		}
	}
	
	public void moveBossLeft(){
		if(boss.getX() - boss.getSpeed() >= 0){
			boss.translate(-boss.getSpeed()/2, 0);
		}
	}

	public void moveBossRight(){
		if(boss.getX() + boss.getSpeed() + boss.width < getWidth()){
			boss.translate(boss.getSpeed()/2, 0);
		}
	}	
	
	public void speedUpBoss() {
		boss.setSpeed(boss.getDefaultSpeed() * 2 +1);
	}

	public void slowDownBoss() {
		boss.setSpeed(boss.getDefaultSpeed());
	}
}