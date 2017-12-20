package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.util.List;

import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.sounds.SoundManager;

public class NewLevel1State extends Level1State {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4781697848857966373L;

	public NewLevel1State(int level, MainFrame frame, GameStatus status, NewLevelLogic gameLogic,
			InputHandler inputHandler, NewGraphicsManager graphicsMan, SoundManager soundMan) {
		super(level, frame, status, gameLogic, inputHandler, graphicsMan, soundMan);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isLevelWon() {
		if(getInputHandler().isNPressed()) {
			return true;
		}
		return levelAsteroidsDestroyed >= 3;
	};
	
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
}