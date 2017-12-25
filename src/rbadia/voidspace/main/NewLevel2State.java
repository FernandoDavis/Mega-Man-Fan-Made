package rbadia.voidspace.main;

import java.awt.Graphics2D;
import java.util.List;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.graphics.NewGraphicsManager;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.sounds.SoundManager;

public class NewLevel2State extends Level2State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1195331220121706608L;

	public NewLevel2State(int level, MainFrame frame, GameStatus status, NewLevelLogic gameLogic,
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
			megaMan.setDirection(0);
		}
		
		else if(((Gravity()==false) && (Fire()==false) && (Fire2()==false)) && megaMan.getDirection() == 180){
			((NewGraphicsManager) getGraphicsManager()).drawMegaManFlipped(megaMan, g2d, this);
			megaMan.setDirection(180);
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
	protected boolean Fire(){
		MegaMan megaMan = this.getMegaMan();
		List<Bullet> bullets = this.getBullets();
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if((bullet.getX() <= megaMan.getX() + megaMan.getWidth() + 60) && 
					(bullet.getX() >= megaMan.getX() - 60)){
				return true;
			}
		}
		return false;
	}

	
	@Override
	public void fireBullet(){
		if(megaMan.getDirection()==180) 
		{
			Bullet bullet2 = new Bullet (megaMan.x,megaMan.y + megaMan.width/2 - Bullet.HEIGHT+2);
			bullet2.setDirection(megaMan.getDirection());
			bullets.add(bullet2);
		}
		else {
		Bullet bullet = new Bullet(megaMan.x + megaMan.width - Bullet.WIDTH/2,
					   megaMan.y + megaMan.width/2 - Bullet.HEIGHT +2);
		bullet.setDirection(megaMan.getDirection());
		bullets.add(bullet);
		this.getSoundManager().playBulletSound();
	}
	}
	
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
	}

	protected void drawAsteroid2() {
		// TODO Auto-generated method stub
		
	};
}