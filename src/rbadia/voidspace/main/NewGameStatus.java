package rbadia.voidspace.main;

public class NewGameStatus extends GameStatus {

	private boolean newShip;
	
	public synchronized boolean isNewShip() {
		return newShip;
	}

	public synchronized void setNewShip(boolean newShip) {
		this.newShip = newShip;
	}
}
