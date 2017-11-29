package Metro;

import java.util.LinkedList;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public class Lobby implements Runnable {

	private int id;

	private LinkedList<Passanger> passlist;

	public Lobby(int id) {
		this.id = id;
		passlist = new LinkedList<Passanger>();
	}

	public LinkedList<Passanger> getPasslist() {
		return passlist;
	}

	public int getId() {
		return id;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		Runnable lobby = () -> {
			do {
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				synchronized (passlist) {
					passlist.add(new Passanger());
					passlist.getLast().setDirection(false);
					if (passlist.getFirst().isDirection()) {
						passlist.removeFirst();
					}
				}
				;
			} while (true);
		};

		new Thread(lobby).start();

	}

}
