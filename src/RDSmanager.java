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
			dbName = "ebdb";
			userName = "jingfeng";
			password = "jingfeng2";
			hostname = "aa1bn93m9zk08nc.cyamhzmhppdf.us-east-1.rds.amazonaws.com";
			port = "3306";
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
	
	public float getObjectRating(String vName) {
		try {
			String queryToSelect = "select rating from VIDEOINFO where vName='"
					+ vName + "'";
			float rating = 0;
			if (connection != null) {
				PreparedStatement preparedStatement = connection.prepareStatement(queryToSelect);
				ResultSet result = preparedStatement.executeQuery();

				while (result.next()) {
					rating = result.getInt(1);
					System.out.println(rating);
				}
			}
			return rating;
		} catch (SQLException e) {
			System.out.println("Error occurs when getting rating"
					+ e.getMessage());
			e.printStackTrace();
			return 1;
		}

	}
	
	public boolean addVideo(String Vname, int rating){
		try{
			String query = "select * from VIDEOINFO";
			int videoNum = 0;
			if (connection!=null){
				//System.out.println("connection established");
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet result = preparedStatement.executeQuery();

				while (result.next()) {
					videoNum++;
				}
				videoNum++;
				query = "Insert into VIDEOINFO(vID,vName,rating,count)VALUES("+videoNum+",'"+Vname + "',"+rating +",0);";
				PreparedStatement preparedStatement1 = connection.prepareStatement(query);
				preparedStatement1.executeUpdate();
				return true;
			}
			
			return false;
		}
		catch (SQLException e) {
			System.out.println("Error occurs when Adding new Video " + Vname + " "
					+ e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public int[] rateVideo(String Vname,int rating){
		try{
			int[] result = new int[2];
			String query = "select * from VIDEOINFO where Vname='"+Vname+"'";
			if (connection!=null){
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultset = preparedStatement.executeQuery();

				if (resultset.next()) {
					result[0] = resultset.getInt(3);
					result[1] = resultset.getInt(2);
				}
			}
			return result;
		}
		catch (SQLException e) {
			System.out.println("Error occurs when Rating Video " + Vname
					+ e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}

