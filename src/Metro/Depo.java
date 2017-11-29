package Metro;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class Depo {

	private List<Line> linelist;
	private LinkedList<Train> trainlist;
	private PriorityBlockingQueue<Driver> driverlist;

	private static Depo obj = new Depo();

	private Depo() {

	}

	public static Depo getDepo() {
		return obj;
	}

	public Depo buildLines() {
		linelist = new ArrayList<Line>();

		Line line1 = new Line(1);
		Line line2 = new Line(2);
		Line line3 = new Line(3);

		linelist.add(line1);
		linelist.add(line2);
		linelist.add(line3);

		return this;
	}

	public Depo buildStations() {
		for (Line line : linelist) {
			for (byte i = 0; i < 10; i++) {
				line.getStationlist().add(new Station((i + 1) * 100 + line.getId()));
			}
		}

		return this;
	}

	public Depo buildLobbys() {
		int i = 1;
		for (Line line : linelist) {
			for (Station station : line.getStationlist()) {
				station.setLobby(new Lobby(i * 10000 + station.getId() * 100 + line.getId()));
				i++;
			}
		}
		return this;
	}

	public Depo buildElevators() {
		ElevatorFactory elevatroFactory = new ElevatorFactory();
		for (Line line : linelist) {
			for (Station station : line.getStationlist()) {
				for (byte i = 0; i < 2; i++) {
					Elevator elevator = elevatroFactory.getElevator(true);
					elevator.setLobby(station.getLobby());
					elevator.setStation(station);
					elevator.setId((i + 1) * 10000 + station.getId() * 100 + line.getId());
					station.getElevatorlist().add(elevator);
				}
				for (byte i = 0; i < 2; i++) {
					Elevator elevator = elevatroFactory.getElevator(false);
					elevator.setLobby(station.getLobby());
					elevator.setStation(station);
					elevator.setId(i + 1);
					station.getElevatorlist().add(elevator);
				}
			}
		}
		return this;
	}

	Comparator<Driver> comparator = new Comparator<Driver>() {
		@Override
		public int compare(Driver d1, Driver d2) {
			if (d1.getXp() > d2.getXp()) {
				return -1;
			}
			if (d1.getXp() < d2.getXp()) {
				return 1;
			}
			return 0;
		}
	};

	public Depo buildDriverList() {
		driverlist = new PriorityBlockingQueue<Driver>(20, comparator);
		for (int i = 0; i < 20; i++) {
			driverlist.add(new Driver(i + 1));
		}

		// for (int i=0;i<20;i++){
		// System.out.print(driverlist.poll().getXp() + " ");
		// }
		// System.out.println();

		return this;
	}

	private List<Wagon> buildWagonList() {
		List<Wagon> wagonlist = new LinkedList<Wagon>();
		WagonFactory wagonfactory = new WagonFactory();
		for (int i = 0; i < 100; i++) {
			wagonlist.add(wagonfactory.getWagon(Math.random() < 0.5));
			wagonlist.get(wagonlist.size() - 1).setId(i + 1);
		}
		return wagonlist;
	}

	public void addWagonToTrainlist(Wagon w) {
		boolean check = false;
		for (Train t : trainlist) {
			if (t.addWagon(w)) {
				check = true;
				break;
			}
		}
		if (!check) {
			trainlist.add(new Train());
			addWagonToTrainlist(w);
		}
	}

	private void removeIncomleteTrains() {
		for (Train t : trainlist) {
			if (!t.checkSize()) {
				trainlist.remove(t);
				removeIncomleteTrains();
				break;
			}
		}
	}

	public Depo buildTrainList() {
		trainlist = new LinkedList<Train>();
		List<Wagon> wagonlist = this.buildWagonList();
		trainlist.add(new Train());
		do {
			Wagon w = wagonlist.remove(0);
			addWagonToTrainlist(w);
		} while (!wagonlist.isEmpty());

		removeIncomleteTrains();

		for (Train train : trainlist) {
			train.setId(trainlist.indexOf(train) + 1);
		}

		return this;
	}

	public Depo giveOutTrainsToLines() {
		while (!trainlist.isEmpty()) {
			for (Line line : linelist) {
				if (!trainlist.isEmpty()) {
					line.getTrainlist().add(trainlist.removeFirst());
				}
			}
		}
		return this;
	}
	
	private static void writeFile(List<Train> list) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("..//Metro_New//trains.trn"));
			for (Train train : list) {
				out.writeObject(train);
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static List readFile() {
		List <Train> trainlist = new LinkedList<Train>();
				
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("..//Metro_New//trains.trn"));
			while (true) {
				try {
					trainlist.add((Train)in.readObject());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EOFException e) {
					break;
				} catch (ClassCastException e){
					continue;
				}
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return trainlist;
	
	}

	public void metroRun() {
		for (Line line : linelist) {
			for (Station station : line.getStationlist()) {
				Thread lobbythread = new Thread(station.getLobby());
				lobbythread.start();
				for (Elevator elevator : station.getElevatorlist()) {
					Thread elevatorthread = new Thread(elevator);
					elevatorthread.start();
				}
			}
		}
	}

	public void trainsRun() {
		for (Line line : linelist) {

			Runnable trainToLine = () -> {
				do {
					try {
						TrainMovingLine tmv = new TrainMovingLine(line.getTrainlist().poll(), driverlist.poll(), line);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} while (true);
			};
			
			new Thread(trainToLine).start();

		}
	}

}