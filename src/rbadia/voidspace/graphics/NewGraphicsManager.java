package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.MegaMan;
import rbadia.voidspace.model.Ship;

public class NewGraphicsManager extends GraphicsManager {
	private BufferedImage megaManImgFlipped;
	private BufferedImage megaFallRImgFlipped;
	private BufferedImage megaFireRImgFlipped;
	private BufferedImage ship;
	
	public NewGraphicsManager() {
		super();
		try {
			this.megaManImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaMan3Flipped.png"));
			this.megaFallRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFallRightFlipped.png"));
			this.megaFireRImgFlipped = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/megaFireRightFlipped.png"));
			this.ship = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/ship.png"));
			
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
	
	public void drawShip (Ship ship, Graphics2D g2d, ImageObserver observer){
		g2d.drawImage(this.ship, ship.x, ship.y, observer);	
	}
	
}
