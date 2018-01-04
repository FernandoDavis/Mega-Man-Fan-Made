package rbadia.voidspace.main;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.main.GameStatus;
import rbadia.voidspace.main.InputHandler;
import rbadia.voidspace.main.MainFrame;
import rbadia.voidspace.main.NewLevel2State;
import rbadia.voidspace.main.NewLevelLogic;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

public class Level3State extends NewLevel2State{

	private static final long serialVersionUID = 1L;
	protected int numPlatforms = 16;
	

	public Level3State(int level, MainFrame frame, GameStatus status, NewLevelLogic gameLogic,
			InputHandler inputHandler, NewGraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		// TODO Auto-generated constructor stub
	}

	protected int numPlatforms=16;

	public int getNumPlatforms() {return numPlatforms;}
	
	@Override
	protected void TransitionImage() {
		// Transition
		Graphics2D g2d = getGraphics2D();	
		((NewGraphicsManager) getGraphicsManager()).Transition2(g2d, getMainFrame(), this);
	}
	
	@Override
	public void doStart() {	
		super.doStart();
		setStartState(GETTING_READY);
		setCurrentState(getStartState());
		newPlatforms(getNumPlatforms());

	}
	



	@Override
	protected void drawAsteroid() {
		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		if((asteroid.getX() + asteroid.getWidth() >  0)){
			asteroid.translate(-asteroid.getSpeed(), 0);
			getGraphicsManager().drawAsteroid(asteroid, g2d, this);	
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){

				// draw a new asteroid
				lastAsteroidTime = currentTime;
				status.setNewAsteroid(false);
				asteroid.setLocation((int) (this.getWidth() - asteroid.getPixelsWide()),
						(rand.nextInt((int) (this.getHeight() - asteroid.getPixelsTall() - 32))));

				asteroid.setLocation(this.getWidth() - asteroid.getPixelsWide(),
						rand.nextInt(this.getHeight() - asteroid.getPixelsTall() - 32));
			}

			else{
				// draw explosion
				getGraphicsManager().drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}
	}
	
	@Override
	protected void drawPlatforms() {
		//draw platforms
		Graphics2D g2d = getGraphics2D();
		for(int i=0; i<getNumPlatforms(); i++){
			((NewGraphicsManager) getGraphicsManager()).drawPlatformLevel3(platforms[i], g2d, this, i);
		}
	}

	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		int j = 0;
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(0,0);
			if(i<getNumPlatforms()/2) platforms[i].setLocation(50+ i*50, getHeight()/2 + 160 - i*40);
			if(i>=getNumPlatforms()/2) {
				platforms[i].setLocation(this.getWidth()-100-j*50, getHeight()/2 + 160 - j*40);
				j++;
			}
		}	
		return platforms;
	}
	
	@Override
	protected void clearScreen() {
		// clear screen
		Graphics2D g2d = getGraphics2D();
		((NewGraphicsManager) getGraphicsManager()).BackgroundImageLevel3(g2d, getMainFrame(), this);
	}
	
	@Override
	protected void drawFloor() {
		//draw Floor
		Graphics2D g2d = getGraphics2D();
		for(int i=0; i<9; i++){
		((NewGraphicsManager) getGraphicsManager()).drawFloor3(floor[i], g2d, this, i);
		}
	}
	
	@Override
	protected void drawStars(int numberOfStars) {}
	
	@Override
	public boolean isLevelWon() {
		if(getInputHandler().isNPressed()) {
			return true;
		}
		return levelAsteroidsDestroyed >= 13; //+5 asteroids
	}
}
