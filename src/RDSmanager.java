import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

public class RDSmanager {
	String dbName;
	String userName;
	String password;
	String hostname;
	String port;
	String jdbcUrl;
	Connection connection;
	public RDSmanager(){
		init();
	}
	public void init(){
		try{
			dbName = System.getProperty("RDS_DB_NAME");
			userName = System.getProperty("RDS_PASSWORD");
			hostname = System.getProperty("RDS_HOSTNAME");
			port = System.getProperty("RDS_PORT");
			jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
			connection = DriverManager.getConnection(jdbcUrl);
		}
		catch (Exception e)
		{
				System.out
				.println("Error occurs when connecting to database: " + e.getMessage());
				e.printStackTrace();
		}
	}
}
