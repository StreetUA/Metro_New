package Metro;

public class Metro {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
		Depo depo = Depo.getDepo();
		depo.buildLines().buildStations().buildLobbys().buildElevators().buildDriverList().buildTrainList().giveOutTrainsToLines();
		
		depo.metroRun();
		depo.trainsRun();
				
	}

}
