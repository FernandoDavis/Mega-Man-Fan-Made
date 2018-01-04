package rbadia.voidspace.main;

import javax.sound.sampled.Clip;

public class NewLevelLogic extends LevelLogic {
	private long lastExchangeTime;
	private long lastBigBulletTime;
	private long score;
	private long deduction;
	private int stack= 0;
	int musicBoolean = 1; //1 means stop, 0 means play
	@Override
	public void handleKeysDuringPlay(InputHandler ih, LevelState levelState) {
		super.handleKeysDuringPlay(ih, levelState);
		if(ih.isMPressed()){
			if(musicBoolean == 1) {
				MegaManMain.audioClip.stop();
				musicBoolean = 0;
			}
			
			else if(musicBoolean == 0) {
				MegaManMain.audioClip.start();
				MegaManMain.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
				musicBoolean = 1;
			}
		}
		
		/*
		 * If E key is pressed, checks if user has enough points to assign power up life one up
		 */
		if(ih.isEPressed()) {
			NewGameStatus status = (NewGameStatus) getLevelState().getGameStatus();
			score = status.getAsteroidsDestroyed() + ((NewGameStatus) status).getShipsDestroyed();
			deduction = 1500; 
			if(score >= deduction){
				if(status.getAsteroidsDestroyed() == 1500) {
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed()-deduction);
				}
				else if(status.getShipsDestroyed() == 1500) {
					status.setShipsDestroyed(status.getShipsDestroyed()-deduction);
				}
				else if(status.getAsteroidsDestroyed() < 1500) {
					deduction = deduction - status.getAsteroidsDestroyed();
					status.setShipsDestroyed(status.getShipsDestroyed()-deduction);
				}
				else if(status.getShipsDestroyed() < 1500) {
					deduction = deduction - status.getShipsDestroyed();
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed()-deduction);
				}
				
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastExchangeTime > 1000)){
					lastExchangeTime = currentTime;
					status.setLivesLeft(status.getLivesLeft() + 1);
				}
			}
		}
		
		/*
		 * If Q key is pressed, checks if user has enough points to assign BigBullet power up
		 */
		if(ih.isQPressed()) {
			NewGameStatus status = (NewGameStatus) getLevelState().getGameStatus();
			score = status.getAsteroidsDestroyed() + ((NewGameStatus) status).getShipsDestroyed();
			deduction = 1000; 
			if(stack==0 && score >= deduction){
				if(status.getAsteroidsDestroyed() == 1000) {
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed()-deduction);
				}
				else if(status.getShipsDestroyed() == 1000) {
					status.setShipsDestroyed(status.getShipsDestroyed()-deduction);
				}
				else if(status.getAsteroidsDestroyed() < 1000) {
					deduction = deduction - status.getAsteroidsDestroyed();
					status.setShipsDestroyed(status.getShipsDestroyed()-deduction);
				}
				else if(status.getShipsDestroyed() < 1000) {
					deduction = deduction - status.getShipsDestroyed();
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed()-deduction);
				}
				stack++;
			}
			else if(stack>= 1){
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastBigBulletTime) > 1000){
					lastBigBulletTime = currentTime;
					getLevelState().fireBigBullet();
					stack--;
				}
			}
		}
	}
}