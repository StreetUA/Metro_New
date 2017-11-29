package Metro;

public class TrainMovingLine implements Runnable {
	private Train train;
	private Driver driver;
	private Line line;

	public TrainMovingLine(Train train, Driver driver, Line line) {
		super();
		this.train = train;
		this.driver = driver;
		this.line = line;
	}

	@Override
	public void run() {
		System.out.println("Train " + train.getId() + " is moving on line " + line.getId());

		driver.addXp(5);

		for (Station station : line.getStationlist()) {
			try {
				Thread.sleep(1000);

				System.out.println("Train " + train.getId() + " is on station " + station.getId());

				TrainVisitStation tvs = new TrainVisitStation(train, station);
				tvs.passOut();
				tvs.finish.await();
				tvs.passIn();
				tvs.finish.await();
				this.setPassTrue();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (Station station : line.getStationListRevers()) {
			try {
				Thread.sleep(1000);

				System.out.println("Train " + train.getId() + " is on station " + station.getId());

				TrainVisitStation tvs = new TrainVisitStation(train, station);
				tvs.passOut();
				tvs.finish.await();
				tvs.passIn();
				tvs.finish.await();
				this.setPassTrue();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setPassTrue() {
		for (Wagon wagon : train.getWagonlist()) {
			synchronized (wagon.getPasslist()) {
				if (!wagon.getPasslist().isEmpty()) {
					for (Passanger passanger : wagon.getPasslist()) {
						passanger.setDirection(true);
					}
				}
			}
		}
	}

}
