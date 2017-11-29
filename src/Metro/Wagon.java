package Metro;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

abstract class Wagon implements Cloneable {

	private int id;
	
	public void setId(int id) {
		this.id = id;
	}

	protected int fullload;
	private LinkedList<Passanger> passlist = new LinkedList<Passanger>();

	public int getFullload() {
		return fullload;
	}

	public LinkedList<Passanger> getPasslist() {
		return passlist;
	}

	@Override
	public Wagon clone() {
		Wagon cloned = null;
		try {
			cloned = (Wagon) super.clone();
			cloned.id = cloned.hashCode();
			return cloned;
		} catch (CloneNotSupportedException ex) {

		}
		return cloned;
	}

	public int getId() {
		return id;
	}
}

class WagonFactory {

	public Wagon getWagon(Boolean wagontype) {
		if (wagontype == null)
			return null;
		if (wagontype)
			return new MainWagon();
		if (!wagontype)
			return new MiddleWagon();
		return null;
	}

	class MainWagon extends Wagon {

		protected MainWagon() {
			super.fullload = 20;
		}

	}

	class MiddleWagon extends Wagon {

		protected MiddleWagon() {
			super.fullload = 30;
		}

	}

}