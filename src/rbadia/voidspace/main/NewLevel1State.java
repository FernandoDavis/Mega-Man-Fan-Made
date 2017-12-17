package rbadia.voidspace.main;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.sounds.SoundManager;

public class NewLevel1State extends Level1State {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4781697848857966373L;

	public NewLevel1State(int level, MainFrame frame, GameStatus status, NewLevelLogic gameLogic,
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
}