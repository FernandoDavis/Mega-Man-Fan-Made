package rbadia.voidspace.main;

import java.awt.Graphics2D;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.sounds.SoundManager;

public class NewLevel2State extends Level2State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1195331220121706608L;

	public NewLevel2State(int level, MainFrame frame, GameStatus status, NewLevelLogic gameLogic,
			InputHandler inputHandler, GraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isLevelWon() {
		if(getInputHandler().isNPressed()) {
			return true;
		}
		return levelAsteroidsDestroyed >= 3;
	}
	
	@Override
	protected void drawMegaMan() {
		//draw one of three possible MegaMan poses according to situation
		Graphics2D g2d = getGraphics2D();
		GameStatus status = getGameStatus();
		if(!status.isNewMegaMan()){
			if((Gravity() == true) || ((Gravity() == true) && (Fire() == true || Fire2() == true)) && !getInputHandler().isLeftPressed()){
				getGraphicsManager().drawMegaFallR(megaMan, g2d, this);
			}
			
			else if((Gravity() == true) || ((Gravity() == true) && (Fire() == true || Fire2() == true)) && getInputHandler().isLeftPressed()){
				((NewGraphicsManager) getGraphicsManager()).drawMegaFallRFlipped(megaMan, g2d, this);
			}
		}

		if((Fire() == true || Fire2()== true) && (Gravity()==false) && !getInputHandler().isLeftPressed()){
			getGraphicsManager().drawMegaFireR(megaMan, g2d, this);
		}
		
		else if((Fire() == true || Fire2()== true) && (Gravity()==false) && getInputHandler().isLeftPressed()){
			((NewGraphicsManager) getGraphicsManager()).drawMegaFireRFlipped(megaMan, g2d, this);
		}

		if((Gravity()==false) && (Fire()==false) && (Fire2()==false) && !getInputHandler().isLeftPressed()){
			getGraphicsManager().drawMegaMan(megaMan, g2d, this);
		}
		
		else if((Gravity()==false) && (Fire()==false) && (Fire2()==false) && getInputHandler().isLeftPressed()){
			((NewGraphicsManager) getGraphicsManager()).drawMegaManFlipped(megaMan, g2d, this);
		}
	}
}