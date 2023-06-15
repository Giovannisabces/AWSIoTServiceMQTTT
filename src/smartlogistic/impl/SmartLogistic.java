package smartlogistic.impl;


public class SmartLogistic {
	
	
	protected String id;
	public SmartLogistic() {id = null;}
	protected SmartLogisticNotifier subscriber  = null;
	public SmartLogistic(String id) {
		this.setId(id);
		this.subscriber = new SmartLogisticNotifier();
		this.subscriber.awsConnect();
		this.subscriber.subscribe("event/010/info");
	}
	

	 public String getId() {
		return id;
	}
	 
	 public void setId(String id) {
		this.id = id;
	}
	 
	
}
