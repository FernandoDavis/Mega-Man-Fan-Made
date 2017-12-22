package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Platform;

public class NewGraphicsManager extends GraphicsManager {
	private BufferedImage megaManImgFlipped;
	private BufferedImage megaFallRImgFlipped;
	private BufferedImage megaFireRImgFlipped;
	private BufferedImage platformImg;
	private BufferedImage asteroidImg;
	private BufferedImage asteroidExplosionImg;
	private BufferedImage bigAsteroidExplosionImg;

	
	public NewGraphicsManager() {
		super();
		try {
			this.megaManImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3Flipped.png"));
			this.megaFallRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallRightFlipped.png"));
			this.megaFireRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireRightFlipped.png"));
			this.platformImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/platform.png"));
			this.asteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.asteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.bigAsteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BigAsteroid.png"));

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
		g2d.drawImage(asteroidImg, asteroid.x, asteroid.y, observer);
	}
	
	public void drawAsteroidExplosion2(Rectangle asteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg, asteroidExplosion.x, asteroidExplosion.y, observer);
	}

	public void drawBigAsteroidExplosion(Rectangle bigAsteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bigAsteroidExplosionImg, bigAsteroidExplosion.x, bigAsteroidExplosion.y, observer);
	}
}
