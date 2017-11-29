package Metro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Train implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9192876288196049783L;
	private List<Wagon> wagonlist;
	private Driver driver;

	public List<Wagon> getWagonlist() {
		return wagonlist;
	}

	private int id;

	public void setId(int id) {
		this.id = id;
	}

	public Train() {
		wagonlist = new ArrayList<Wagon>();
	}

	public int getId() {
		return id;
	}

	public boolean addWagon(Wagon wagon) {

		if (checkSize())
			return false;

		if (wagonlist.isEmpty()) {
			wagonlist.add(wagon);
			return true;
		}

		switch (wagon.getClass().getSimpleName()) {
		case "MainWagon":
			if (wagonlist.get(0).getClass().getSimpleName().equals(wagon.getClass().getSimpleName())) {
				if (wagonlist.get(wagonlist.size() - 1).getClass().getSimpleName().equals(wagon.getClass().getSimpleName())) {
					return false;
				} else {
					wagonlist.add(wagon);
					return true;
				}

			} else {
				wagonlist.add(0, wagon);
				return true;
			}

		case "MiddleWagon":
			byte i = 0;
			for (Wagon w : wagonlist) {
				if (w.getClass().getSimpleName().equals("MiddleWagon"))
					i++;
			}
			if (i < 3) {
				wagonlist.add(wagonlist.size() - 1, wagon);
				return true;
			}
		
		default:
			return false;
		}
	}

	public boolean checkSize() {
		if (wagonlist.isEmpty())
			return false;
		return wagonlist.size() == 5;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

}
