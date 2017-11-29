package Metro;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Station {

	private int id;

	public void setId(int id) {
		this.id = id;
	}

	private LinkedList<Passanger> passlist;
	private List<Elevator> elevatorlist;
	private Lobby lobby;

	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}

	public Station(int id) {
		this.id = id;
		passlist = new LinkedList<Passanger>();
		elevatorlist = new ArrayList<Elevator>();
	}

	public List<Elevator> getElevatorlist() {
		return elevatorlist;
	}

	public Lobby getLobby() {
		return lobby;
	}

	public LinkedList<Passanger> getPasslist() {
		return passlist;
	}

	public int getId() {
		return id;
	}

}
