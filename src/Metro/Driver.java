package Metro;

import java.util.Random;

public class Driver {

	private int id;
	public void setId(int id) {
		this.id = id;
	}

	private int xp;

	public Driver(int id) {
		this.id = id;
		xp = new Random().nextInt(5);
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getId() {
		return id;
	}

	public int getXp() {
		return xp;
	}

	public void addXp(int amount) {
		xp = xp + amount;
	}

}
