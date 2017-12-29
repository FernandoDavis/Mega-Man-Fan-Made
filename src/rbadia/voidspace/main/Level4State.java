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
	
	private static final long serialVersionUID = 386347600800582004L;
	
	protected int numPlatforms = 26;

	public Level4State(int level, MainFrame frame, GameStatus status, NewLevelLogic gameLogic,
			InputHandler inputHandler, NewGraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
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
		int k = 0;
		for(int i=0; i<n; i++){
			this.platforms[i] = new Platform(0,0);
		
			if(i <= 8) 
			{
				platforms[i].setLocation(0, getHeight()/2 + 160 - i*40); //Platforms in the left side
			}
			if(i > 8 && i <= 16) 
			{
				platforms[i].setLocation(this.getWidth()/2-22, getHeight()/2 + 160 - j*40);//Platforms in the center
				j++;
			}
			if(i > 16) 
			{
				platforms[i].setLocation(this.getWidth()-44, getHeight()/2 + 160 - k*40);//Platforms in the right side
				k++;	
			}

		}
		
		//platforms[8].setLocation(0, getHeight()/2 + 160 - 8*40);
		return platforms;
	}
	
	@Override
	public boolean isLevelWon() {
		if(getInputHandler().isNPressed()) {
			return true;
		}
		return levelAsteroidsDestroyed >= 18; //+5 asteroids
	}
}
