package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BigBullet;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.BossBullets;
import rbadia.voidspace.model.Floor;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Platform;

public class NewGraphicsManager extends GraphicsManager {
	private BufferedImage megaManImgFlipped;
	private BufferedImage megaFallRImgFlipped;
	private BufferedImage megaFireRImgFlipped;
	private BufferedImage platformImg;
	private BufferedImage asteroidImg2;
	private BufferedImage asteroidExplosionImg2;
	private BufferedImage bigAsteroid;
	private BufferedImage bigAsteroidExplosionImg;
	private BufferedImage floorImg2;
	
	private BufferedImage bossImg;
	private BufferedImage bossFallRImg;
	private BufferedImage bossFireRImg;
	private BufferedImage bossImgFlipped;
	private BufferedImage bossFallRImgFlipped;
	private BufferedImage bossFireRImgFlipped;
	private BufferedImage bossExplosionImg;
	private BufferedImage bossBulletImg;

	
	public NewGraphicsManager() {
		super();
		try {
			this.megaManImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3Flipped.png"));
			this.megaFallRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallRightFlipped.png"));
			this.megaFireRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireRightFlipped.png"));
			this.platformImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/platform.png"));
			this.asteroidImg2 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.bigAsteroid = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BigAsteroid.png"));
			this.asteroidExplosionImg2 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.bigAsteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigAsteroidExplosion.png"));
			this.floorImg2 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigAsteroidExplosion.png"));////////////////////Cambiar esta mierda
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			this.bossImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3Flipped.png"));
			this.bossFallRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallRightFlipped.png"));
			this.bossFireRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireRightFlipped.png"));
			this.bossImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3.png"));
			this.bossFallRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallRight.png"));
			this.bossFireRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireRight.png"));
			this.bossExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigAsteroidExplosion.png"));
			this.bossBulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigBullet.png"));
			

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void drawMegaManFlipped (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaManImgFlipped, megaMan.x, megaMan.y, observer);	
	}

	public void drawMegaFallRFlipped (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaFallRImgFlipped, megaMan.x, megaMan.y, observer);	
	}

	public void drawMegaFireRFlipped (MegaMan megaMan, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(megaFireRImgFlipped, megaMan.x, megaMan.y, observer);	
	}
	
	public void drawPlatform2(Platform platform, Graphics2D g2d, ImageObserver observer, int i){
		g2d.drawImage(platformImg, platform.x , platform.y, observer);	
	}
	
	public void drawAsteroid2(Asteroid asteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidImg2, asteroid.x, asteroid.y, observer);
	}
	
	public void drawBigAsteroid(Asteroid asteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigAsteroid, asteroid.x, asteroid.y, observer);
	}
	
	public void drawAsteroidExplosion2(Rectangle asteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg2, asteroidExplosion.x, asteroidExplosion.y, observer);
	}

	public void drawBigAsteroidExplosion(Rectangle bigAsteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigAsteroidExplosionImg, bigAsteroidExplosion.x, bigAsteroidExplosion.y, observer);
	}
	
	public void drawFloor2 (Floor floor, Graphics2D g2d, ImageObserver observer, int i){
		g2d.drawImage(floorImg2, floor.x, floor.y, observer);				
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void drawBoss (Boss boss, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(bossImg, boss.x, boss.y, observer);	
	}

	public void drawBossFallR (Boss boss, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(bossFallRImg, boss.x, boss.y, observer);	
	}
	
	public void drawBossFallRFlipped (Boss boss, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(bossFallRImgFlipped, boss.x, boss.y, observer);	
	}

	public void drawBossFireR (Boss boss, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(bossFireRImg, boss.x, boss.y, observer);	
	}
	
	public void drawBossFlipped (Boss boss, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(bossImgFlipped, boss.x, boss.y, observer);	
	}

	public void drawBossFireRFlipped (Boss boss, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(bossFireRImgFlipped, boss.x, boss.y, observer);	
	}
	
	public void drawBossBullet(BossBullets bossBullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossBulletImg, bossBullet.x, bossBullet.y, observer);
	}
	
	public void drawBossExplosion(Rectangle bossExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossExplosionImg, bossExplosion.x, bossExplosion.y, observer);
	}
}
