package rbadia.voidspace.main;

import javax.sound.sampled.Clip;

public class NewLevelLogic extends LevelLogic {
	
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
		
	}
}