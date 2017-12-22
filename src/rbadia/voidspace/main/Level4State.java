package rbadia.voidspace.main;
import java.awt.Graphics2D;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.main.GameStatus;
import rbadia.voidspace.main.InputHandler;
import rbadia.voidspace.main.MainFrame;
import rbadia.voidspace.main.NewLevel2State;
import rbadia.voidspace.main.NewLevelLogic;
import rbadia.voidspace.model.Platform;
import rbadia.voidspace.sounds.SoundManager;

public class Level4State extends NewLevel2State{

	private static final long serialVersionUID = 1L;
	protected int numPlatforms = 10;

	public Level4State(int level, MainFrame frame, GameStatus status, NewLevelLogic gameLogic,
			InputHandler inputHandler, NewGraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		// TODO Auto-generated constructor stub
	}
	
	public int getNumPlatforms() {return numPlatforms;}
	

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
		if((asteroid.getX() + asteroid.getPixelsWide() >  0)) {
			asteroid.translate(-asteroid.getSpeed(), asteroid.getSpeed()/2);
			getGraphicsManager().drawAsteroid(asteroid, g2d, this);	
		}
		else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){

				asteroid.setLocation(this.getWidth() - asteroid.getPixelsWide(),
						rand.nextInt(this.getHeight() - asteroid.getPixelsTall() - 32));
			}
			else {
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
			getGraphicsManager().drawPlatform2(platforms[i], g2d, this, i);
		}
	}
	
	@Override
	public Platform[] newPlatforms(int n){
		platforms = new Platform[n];
		int j = 0;
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(0,0);
			if(i<3) platforms[i].setLocation(0 , getHeight()/2 + 40 - i*40);
//			if(i==3)platforms[i].setLocation(50+ i*50, getHeight()/2 + 140 - i*40);
//			if(i==4) platforms[i].setLocation(50 +i*50, getHeight()/2 + 140 - 3*40);
//			if(i>4){	
//				int k=4;
//				platforms[i].setLocation(50 + i*50, getHeight()/2 + 20 + (i-k)*40 );
//				k=k+2;
//			}
//			if(i == 6) {
//				platforms[i].setLocation(this.getWidth()-100-j*50, getHeight()/2 + 160 - j*40);
//				j++;
//			}
		}	
		return platforms;
	}
	
	@Override
	public boolean isLevelWon() {
		if(getInputHandler().isNPressed()) {
			return true;
		}
		return levelAsteroidsDestroyed >= 8; //+5 asteroids
	}
}
