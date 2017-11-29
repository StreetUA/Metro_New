package Metro;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Line {

	private LinkedBlockingQueue<Train> trainlist;

	public LinkedBlockingQueue<Train> getTrainlist() {
		return trainlist;
	}

	private int id;

	private List<Station> stationlist;

	public Line(int id) {
		this.id = id;
		stationlist = new ArrayList<Station>();
		trainlist = new LinkedBlockingQueue<Train>();
	}

	public List<Station> getStationListRevers() {
		List<Station> reverslist = new ArrayList<Station>();
		for (Station station : stationlist) {
			reverslist.add(0, station);
		}
		return reverslist;
	}

	public List<Station> getStationlist() {
		return stationlist;
	}

	public int getId() {
		return id;
	}

}
