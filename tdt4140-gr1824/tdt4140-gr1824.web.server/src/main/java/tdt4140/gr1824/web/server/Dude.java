package tdt4140.gr1824.web.server;

public class Dude {
	
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(int age) {
		this.age = age;
	}

	private int age;
	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}
	
	@Override
	public String toString() {
		return "Dude [name=" + name + ", age=" + age + "]";
	}
	

}
