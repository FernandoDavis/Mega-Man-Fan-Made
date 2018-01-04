package rbadia.voidspace.main;
import java.awt.Graphics2D;
import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Level very similar to LevelState1.  
 * Platforms arranged in triangular form. 
 * Asteroids travel at 225 degree angle
 */
public class NewLevel2State extends NewLevel1State {
	
	private static final long serialVersionUID = 2237698689621378249L;

	// Constructors
	public NewLevel2State(int level, MainFrame frame, GameStatus status, 
			LevelLogic gameLogic, InputHandler inputHandler,
			GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
	}
	
	protected void TransitionImage() {
		// Transition
		Graphics2D g2d = getGraphics2D();	
		((NewGraphicsManager) getGraphicsManager()).Transition(g2d, getMainFrame(), this);
	}
	
	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
		TransitionImage();
	}
	
	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(0,0);
			if(i<4)	platforms[i].setLocation(50+ i*50, getHeight()/2 + 140 - i*40);
			if(i==4) platforms[i].setLocation(50 +i*50, getHeight()/2 + 140 - 3*40);
			if(i>4){	
				int k=4;
				platforms[i].setLocation(50 + i*50, getHeight()/2 + 20 + (i-k)*40 );
				k=k+2;
			}
		}
		return platforms;
	}

	@Override
	protected void drawAsteroid2() {
		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		if((asteroid2.getX() + asteroid2.getPixelsWide() >  0)) {
			asteroid2.translate(asteroid2.getSpeed(), asteroid2.getSpeed());
			((NewGraphicsManager)getGraphicsManager()).drawAsteroid2(asteroid2, g2d, this);
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){

				lastAsteroidTime = currentTime;
				status.setNewAsteroid2(false);
				asteroid2.setLocation(0, 1+rand.nextInt(this.getHeight() - asteroid2.getPixelsTall() - 32));
			}
			else {
				// draw explosion
				((NewGraphicsManager) getGraphicsManager()).drawAsteroidExplosion2(asteroidExplosion, g2d, this);
			}
		}	
	}
	
	@Override
	protected void drawBigAsteroid() {
		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		if(bigAsteroid.getX() + bigAsteroid.getPixelsWide() > 0) {
			bigAsteroid.translate(0, bigAsteroid.getSpeed());
			((NewGraphicsManager)getGraphicsManager()).drawBigAsteroid(bigAsteroid, g2d, this);
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){

				//lastAsteroidTime = currentTime;
				status.setNewBigAsteroid(false);
				bigAsteroid.setLocation(rand.nextInt(this.getWidth()-bigAsteroid.getPixelsWide()-64), 0);
			}
			else {
				// draw explosion
				((NewGraphicsManager) getGraphicsManager()).drawBigAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}	
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
	protected void clearScreen() {
		// clear screen
		Graphics2D g2d = getGraphics2D();
		((NewGraphicsManager) getGraphicsManager()).BackgroundImageLevel2(g2d, getMainFrame(), this);
	}
	
	@Override
	public boolean isLevelWon() {
		if(getInputHandler().isNPressed()) {
			return true;
		}
		return levelAsteroidsDestroyed >= 8;
	}
	
}