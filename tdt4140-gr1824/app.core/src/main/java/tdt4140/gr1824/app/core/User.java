package tdt4140.gr1824.app.core;

public class User {
	
	private final int id;
	private Area area;
	
	public User(int id) {
		this.id = id;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public int getId() {
		return id;
	}
	
	

}
