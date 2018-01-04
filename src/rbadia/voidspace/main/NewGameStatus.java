package rbadia.voidspace.main;

public class NewGameStatus extends GameStatus {

	private boolean newShip;
	private long shipsDestroyed = 0;
	
	public synchronized boolean isNewShip() {
		return newShip;
	}

	public synchronized void setNewShip(boolean newShip) {
		this.newShip = newShip;
	}
	
	/**
	 * Returns the number of ships destroyed. 
	 * @return the number of ships destroyed
	 */
	public synchronized long getShipsDestroyed() {
		return shipsDestroyed;
	}

	public synchronized void setShipsDestroyed(long shipsDestroyed) {
		this.shipsDestroyed = shipsDestroyed;
	}
}
