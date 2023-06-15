package smartlogistic.starter;

import smartlogistic.impl.SmartLogistic;


public class SmartLogisticStarter {

	public static void main(String[] args) {

		SmartLogistic sc1 = new SmartLogistic("VIP Logistic and Events");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}

}
