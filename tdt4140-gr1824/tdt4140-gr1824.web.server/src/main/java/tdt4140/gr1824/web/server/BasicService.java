package tdt4140.gr1824.web.server;

import java.sql.*;

public class BasicService {
	
	Connection con = null;
	
	public BasicService() {
		String url = "jdbc:mysql://mysql.stud.ntnu.no:3306/ingviun_superstudent?useSSL=false";
		String username = "espesorh_db";
		String password = "DB168";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
