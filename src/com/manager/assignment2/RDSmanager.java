package com.manager.assignment2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

import com.amazonaws.services.cloudfront.AmazonCloudFrontClient;
import com.amazonaws.services.cloudfront.model.CreateDistributionRequest;
import com.amazonaws.services.cloudfront.model.DistributionConfig;
import com.amazonaws.services.cloudfront.model.DistributionList;
import com.amazonaws.services.cloudfront.model.Origins;

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
			dbName = "assignment2";
			userName = "jingfeng";
			password = "jingfeng2";
			hostname = "edb.cyamhzmhppdf.us-east-1.rds.amazonaws.com";
			port = "3306";
			Class.forName("com.mysql.jdbc.Driver");
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
					rating = result.getFloat(1);
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
	
	public LinkedList<String> getVideo() {
		try {
			
			String queryToSelectInOrder = "select DISTINCT vName, uploadtime from VIDEOINFO order by rating desc";
			if (connection != null) {
				PreparedStatement preparedStatement = connection.prepareStatement(queryToSelectInOrder);
				ResultSet resultVideoSet = preparedStatement.executeQuery();
				LinkedList<String> videos = new LinkedList<String>();

				while (resultVideoSet.next()) {
					videos.add(resultVideoSet.getString(1));
				}

				return videos;
			}
			return null;
		} catch (SQLException e) {
			System.out.println("Error occurs when getting video information "
					+ e.getMessage());
			e.printStackTrace();
			return null;
		}

	}
	
	public LinkedList<String> getTime() {
		try {
			
			String queryToSelectInOrder = "select DISTINCT vName, uploadtime from VIDEOINFO order by rating desc";
			if (connection != null) {
				PreparedStatement preparedStatement = connection.prepareStatement(queryToSelectInOrder);
				ResultSet resultVideoSet = preparedStatement.executeQuery();
				LinkedList<String> times = new LinkedList<String>();

				while (resultVideoSet.next()) {
					String str = ""+resultVideoSet.getTimestamp(2);
					times.add(str);
				}

				return times;
			}
			return null;
		} catch (SQLException e) {
			System.out.println("Error occurs when getting video information "
					+ e.getMessage());
			e.printStackTrace();
			return null;
		}

	}
	
	public boolean addVideo(String Vname, int rating){
		try{
			String query = "select vID from VIDEOINFO";
			int videoNum = 0;
			if (connection!=null){
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet result = preparedStatement.executeQuery();

				while (result.next()) {
					videoNum = result.getInt(1);
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
	
	public boolean rateVideo(String Vname,int rating){
		try{
			int count = 0;
			float currentRating = 0;
			String query = "select * from VIDEOINFO where Vname='"+Vname+"'";
			if (connection!=null){
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultset = preparedStatement.executeQuery();

				if (resultset.next()) {
					count = resultset.getInt(3);
					currentRating = resultset.getFloat(4);
				}
				float sum = currentRating * count + rating;
				float newRating = sum/(count+1);
				count++;
				query = "update VIDEOINFO SET rating ="
						+ newRating
						+ ", count="
						+ count
						+ " where vName='" + Vname + "'";
				PreparedStatement preparedStatement1 = connection.prepareStatement(query);
				preparedStatement1.executeUpdate();
				return true;
			}
			return false;
		}
		catch (SQLException e) {
			System.out.println("Error occurs when Rating Video " + Vname + "\n Detailed Information:"
					+ e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteVideo(String Vname){
		try{
			if (connection!=null){
				String query = "Delete from VIDEOINFO where vname='"+Vname+"'";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.executeUpdate();
				return true;
			}
			return false;
		}catch (SQLException e) {
			System.out.println("Error occurs when Deleting Video " + Vname+"\n  Detailed Information:"
					+ e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	public void createCloudFront(){
		AmazonCloudFrontClient cloudfront = new AmazonCloudFrontClient();

		// Create a new CloudFront Distribution
		DistributionList dl=new DistributionList();
		DistributionConfig df=new DistributionConfig();
		df.withOrigins(new Origins());
		CreateDistributionRequest rq=new CreateDistributionRequest(df);
		cloudfront.createDistribution(rq);
		// List existing CloudFront Distributions
		System.out.println("Distributions: " + cloudfront.listDistributions(null));
	}
}
