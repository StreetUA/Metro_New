package Metro;

import java.util.LinkedList;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

abstract class Elevator implements Runnable, PassangerExchange {

	private int id;

	public void setId(int id) {
		this.id = id;
	}

	private LinkedList<Passanger> passlist;
	protected Lobby lobby;
	protected Station station;

	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public Elevator() {
		passlist = new LinkedList<Passanger>();
	}

	public LinkedList<Passanger> getPasslist() {
		return passlist;
	}

	public int getId() {
		return id;
	}

}

class ElevatorFactory {

	public Elevator getElevator(Boolean elevatortype) {
		if (elevatortype == null)
			return null;
		if (elevatortype)
			return new ElevatorUp();
		if (!elevatortype)
			return new ElevatorDown();
		return null;
	}

	class ElevatorUp extends Elevator {
		Elevator elevator = this;

		@Override
		public void run() {
			do {
				try {
					Thread.sleep(100);
					exchange(elevator.lobby.getPasslist(), elevator.station.getPasslist(), true);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (true);
		}

	}

	class ElevatorDown extends Elevator {
		Elevator elevator = this;

		@Override
		public void run() {
			do {
				try {
					Thread.sleep(100);
					exchange(elevator.station.getPasslist(), elevator.lobby.getPasslist(), false);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (true);
		}

	}

}