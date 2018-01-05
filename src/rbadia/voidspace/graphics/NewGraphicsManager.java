package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import rbadia.voidspace.main.MainFrame;
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
	private BufferedImage asteroidImg2;
	private BufferedImage asteroidExplosionImg2;
	private BufferedImage bigAsteroid;
	private BufferedImage bigAsteroidExplosionImg;
	private BufferedImage floorImg2;
	private BufferedImage floorImg3;
	private BufferedImage floorImg4;
	private BufferedImage bossImg;
	private BufferedImage bossFallRImg;
	private BufferedImage bossFireRImg;
	private BufferedImage bossImgFlipped;
	private BufferedImage bossFallRImgFlipped;
	private BufferedImage bossFireRImgFlipped;
	private BufferedImage bossExplosionImg;
	private BufferedImage bossBulletImg;
	private BufferedImage bossBulletImgFlipped;
	private BufferedImage platformLevel3;
	private BufferedImage platformLevel4;
	private ImageIcon  transition;
	private ImageIcon  transition2;
	private ImageIcon  transition3;
	private ImageIcon backgroundImageMenu;
	private ImageIcon backgroundImageLevel1;
	private ImageIcon backgroundImageLevel2;
	private ImageIcon backgroundImageLevel3;
	private ImageIcon backgroundImageLevel4;
	
	public NewGraphicsManager() {
		super();
		try {
			this.megaManImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3Flipped.png"));
			this.megaFallRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallRightFlipped.png"));
			this.megaFireRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireRightFlipped.png"));
			this.asteroidImg2 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.bigAsteroid = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BigAsteroid.png"));
			this.asteroidExplosionImg2 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.bigAsteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigAsteroidExplosion.png"));
			this.floorImg2 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/floorMoon.png"));
			this.floorImg3 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/floorLv3.png"));
			this.floorImg4 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/VolcanoesFloor.png"));
			this.platformLevel3 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/platform.png"));
			this.platformLevel4 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/VolcanoPlatforms.png"));
			this.transition = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/transition.png"));
			this.transition2 = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/transition2.png"));
			this.transition3 = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/transition3.png"));
			this.backgroundImageMenu = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/MenuImage.png"));
			this.backgroundImageLevel1 = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/BackgroundLV1.png"));
			this.backgroundImageLevel2 = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/BackgroundLv2.png"));
			this.backgroundImageLevel3 = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/BackgroundLV3.gif"));
			this.backgroundImageLevel4 = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/Volcano.gif"));
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			this.bossImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/LeftNormalBoss.png"));
			this.bossFallRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/LeftFallingBoss.png"));
			this.bossFireRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/LeftShootingBoss.png"));
			this.bossImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/RightNormalBoss.png"));
			this.bossFallRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/RightFallingBoss.png"));
			this.bossFireRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/RightShootingBoss.png"));
			this.bossExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigAsteroidExplosion.png"));
			this.bossBulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/RightBossBullets.png"));
			this.bossBulletImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/LeftBossBullets.png"));
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
	
	public void drawFloor3 (Floor floor, Graphics2D g2d, ImageObserver observer, int i){
		g2d.drawImage(floorImg3, floor.x, floor.y, observer);				
	}
	
	public void drawFloor4 (Floor floor, Graphics2D g2d, ImageObserver observer, int i){
		g2d.drawImage(floorImg4, floor.x, floor.y, observer);				
	}
	
	public void BacgroundImageMenu (Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(backgroundImageMenu.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
	}
	
	public void BackgroundImageLevel1(Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(backgroundImageLevel1.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
	}
	
	public void BackgroundImageLevel2(Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(backgroundImageLevel2.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
	}
	public void BackgroundImageLevel3(Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(backgroundImageLevel3.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
	}
	public void BackgroundImageLevel4(Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(backgroundImageLevel4.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
	}
	
	public void Transition (Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(transition.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
    }
	
	public void Transition2 (Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(transition2.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
    }
	
	public void Transition3 (Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(transition3.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
    }
	
	public void drawPlatformLevel3 (Platform platform, Graphics2D g2d, ImageObserver observer, int i){
		g2d.drawImage(platformLevel3, platform.x , platform.y, observer);	
	}
	
	public void drawPlatformLevel4 (Platform platform, Graphics2D g2d, ImageObserver observer, int i){
		g2d.drawImage(platformLevel4, platform.x , platform.y, observer);	
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
	
	public void drawBossBulletFlipped(BossBullets bossBullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossBulletImgFlipped, bossBullet.x, bossBullet.y, observer);
	}
	
	public void drawBossExplosion(Rectangle bossExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossExplosionImg, bossExplosion.x, bossExplosion.y, observer);
	}
}

package rbadia.voidspace.graphics;

import java.awt.Graphics2D;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import rbadia.voidspace.main.MainFrame;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Boss;
import rbadia.voidspace.model.BossBullets;
import rbadia.voidspace.model.Floor;
import rbadia.voidspace.model.MegaMan;

import rbadia.voidspace.model.Platform;
import rbadia.voidspace.model.Ship;


public class NewGraphicsManager extends GraphicsManager {
	private BufferedImage megaManImgFlipped;
	private BufferedImage megaFallRImgFlipped;
	private BufferedImage megaFireRImgFlipped;
	private BufferedImage asteroidImg2;
	private BufferedImage asteroidExplosionImg2;
	private BufferedImage bigAsteroid;
	private BufferedImage bigAsteroidExplosionImg;
	private BufferedImage floorImg2;
	private BufferedImage floorImg3;
	private BufferedImage floorImg4;
	private BufferedImage bossImg;
	private BufferedImage bossFallRImg;
	private BufferedImage bossFireRImg;
	private BufferedImage bossImgFlipped;
	private BufferedImage bossFallRImgFlipped;
	private BufferedImage bossFireRImgFlipped;
	private BufferedImage bossExplosionImg;
	private BufferedImage bossBulletImg;
	private BufferedImage platformLevel3;
	private BufferedImage platformLevel4;
	private ImageIcon transition;
	private ImageIcon transition2;
	private ImageIcon transition3;
	private ImageIcon backgroundImageMenu;
	private ImageIcon backgroundImageLevel1;
	private ImageIcon backgroundImageLevel2;
	private ImageIcon backgroundImageLevel3;
	private ImageIcon backgroundImageLevel4;
	private BufferedImage bossShip;
	private BufferedImage ship;

	public NewGraphicsManager() {
		super();
		try {
			this.megaManImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3Flipped.png"));
			this.megaFallRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallRightFlipped.png"));
			this.megaFireRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireRightFlipped.png"));
			this.asteroidImg2 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.bigAsteroid = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BigAsteroid.png"));
			this.asteroidExplosionImg2 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.bigAsteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigAsteroidExplosion.png"));
			this.floorImg2 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/floorMoon.png"));
			this.floorImg3 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/floorLv3.png"));
			this.floorImg4 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/VolcanoesFloor.png"));
			this.platformLevel3 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/platform.png"));
			this.platformLevel4 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/VolcanoPlatforms.png"));
			this.transition = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/transition.png"));
			this.transition2 = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/transition2.png"));
			this.transition3 = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/transition3.png"));
			this.backgroundImageMenu = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/MenuImage.png"));
			this.backgroundImageLevel1 = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/BackgroundLV1.png"));
			this.backgroundImageLevel2 = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/BackgroundLv2.png"));
			this.backgroundImageLevel3 = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/BackgroundLV3.gif"));
			this.backgroundImageLevel4 = new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/Volcano.gif"));
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			this.bossImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3Flipped.png"));
			this.bossFallRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallRightFlipped.png"));
			this.bossFireRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireRightFlipped.png"));
			this.bossImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3.png"));
			this.bossFallRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallRight.png"));
			this.bossFireRImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireRight.png"));
			this.bossExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigAsteroidExplosion.png"));
			this.bossBulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bigBullet.png"));
			this.bossShip = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/enemyShip.png"));
			this.ship = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/enemyShip2.png"));
			
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
	
	public void drawFloor3 (Floor floor, Graphics2D g2d, ImageObserver observer, int i){
		g2d.drawImage(floorImg3, floor.x, floor.y, observer);				
	}
	
	public void drawFloor4 (Floor floor, Graphics2D g2d, ImageObserver observer, int i){
		g2d.drawImage(floorImg4, floor.x, floor.y, observer);				
	}
	
	public void BacgroundImageMenu (Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(backgroundImageMenu.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
	}
	
	public void BackgroundImageLevel1(Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(backgroundImageLevel1.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
	}
	
	public void BackgroundImageLevel2(Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(backgroundImageLevel2.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
	}
	public void BackgroundImageLevel3(Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(backgroundImageLevel3.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
	}
	public void BackgroundImageLevel4(Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(backgroundImageLevel4.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
	}
	
	public void Transition (Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(transition.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
    }
	
	public void Transition2 (Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(transition2.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
    }
	
	public void Transition3 (Graphics2D g2d,MainFrame frame, ImageObserver observer){		
		g2d.drawImage(transition3.getImage(), 0, 0,frame.getWidth(), frame.getHeight(), observer);
    }
	
	public void drawPlatformLevel3 (Platform platform, Graphics2D g2d, ImageObserver observer, int i){
		g2d.drawImage(platformLevel3, platform.x , platform.y, observer);	
	}
	
	public void drawPlatformLevel4 (Platform platform, Graphics2D g2d, ImageObserver observer, int i){
		g2d.drawImage(platformLevel4, platform.x , platform.y, observer);	
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

	public void drawBossShip (Ship ship, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(this.bossShip, ship.x, ship.y, observer);	
	}
	
	public void drawShip (Ship ship, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(this.ship, ship.x, ship.y, observer);	
	}	
}

