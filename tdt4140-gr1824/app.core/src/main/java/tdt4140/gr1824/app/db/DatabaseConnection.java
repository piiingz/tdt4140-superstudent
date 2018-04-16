package tdt4140.gr1824.app.db;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javax.sql.DataSource;

public class DatabaseConnection {
	
	// Sets up a connection to the database
	static final String db_name = "jdbc:mysql://mysql.stud.ntnu.no:3306/ingviun_superstudent?useSSL=false";
	
	public static DataSource getMySQLDataSource() {
		MysqlDataSource dataSource = null;
		
		try {
			dataSource = new MysqlDataSource();
			dataSource.setURL(db_name);
			dataSource.setUser("espesorh_db");
			dataSource.setPassword("DB168");
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		return dataSource;
	}
}
