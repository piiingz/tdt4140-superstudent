package tdt4140.gr1824.app.core;

public class User {
	
	private final int id;
	private StayLog staylog;
	
	public User(int id) {
		this.id = id;
	}
	
	public void setStayLog(Area area) {
		this.staylog = new StayLog(area, this.id);
	}
	
	public StayLog getStayLog() {
		return this.staylog;
	}
	
	public void stopStayLog() {
		this.staylog.stopStayLog();
	}

	public int getId() {
		return id;
	}
	
	public Area getArea() {
		return this.staylog.getArea();
	}
	

}
