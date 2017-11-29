package Metro;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public interface PassangerExchange {

	default void exchange(LinkedList<Passanger> passlist1, LinkedList<Passanger> passlist2, boolean direction)
			throws InterruptedException {
		synchronized (passlist2) {
			if (!passlist2.isEmpty()) {
				if (passlist2.getFirst().isDirection() == direction) {
					passlist1.add(passlist2.poll());
					passlist2.notifyAll();
				}
			}
		}
	}

}
