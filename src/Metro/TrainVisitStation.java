package Metro;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class TrainVisitStation implements PassangerExchange {
	private Train train;
	private Station station;
	private List<Thread> threadlist;
	CountDownLatch finish;
	int passin;
	int passout;

	public TrainVisitStation(Train train, Station station) {
		super();
		this.train = train;
		this.station = station;
		threadlist = new ArrayList<Thread>();

		passin = 0;
		passout = 0;
	}

	public void passOut() {

		finish = new CountDownLatch(train.getWagonlist().size());

		threadlist.clear();

		Runnable timer = () -> {
			try {
				Thread.sleep(1000);
				for (Thread thread : threadlist) {
					thread.interrupt();
				}
			} catch (InterruptedException e) {

			}
		};

		for (Wagon wagon : train.getWagonlist()) {

			int passremain = new Random().nextInt(wagon.getPasslist().size());

			Runnable tvs = () -> {
				try {
					while (!Thread.currentThread().isInterrupted() && wagon.getPasslist().size() > passremain) {
						Thread.sleep(100);
						exchange(station.getPasslist(), wagon.getPasslist(), true);
					}
					finish.countDown();
				} catch (InterruptedException e) {
				}
			};

			threadlist.add(new Thread(tvs));

		}

		for (Thread thread : threadlist) {
			thread.start();
		}

		new Thread(timer).start();

	}

	public void passIn() {

		finish = new CountDownLatch(train.getWagonlist().size());

		threadlist.clear();

		Runnable timer = () -> {
			try {
				Thread.sleep(5000);
				for (Thread thread : threadlist) {
					thread.interrupt();
				}
			} catch (InterruptedException e) {
			}
		};

		for (Wagon wagon : train.getWagonlist()) {

			Runnable tvs = () -> {
				try {
					while (!Thread.currentThread().isInterrupted()
							&& wagon.getPasslist().size() < wagon.getFullload()) {
						Thread.sleep(100);
						wagon.getPasslist().add(station.getPasslist().getFirst());
						exchange(wagon.getPasslist(), station.getPasslist(), false);						
					}
					finish.countDown();
				} catch (InterruptedException e) {
				}
			};

			threadlist.add(new Thread(tvs));

		}

		for (Thread thread : threadlist) {
			thread.start();
		}

		new Thread(timer).start();

	}

}
