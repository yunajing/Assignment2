import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3Client;


public class DeleteFileServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static RDSmanager rds;
	
	public DeleteFileServlet(){
		super();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		rds = new RDSmanager();

		AWSCredentials credentials = new PropertiesCredentials(
	   			 DeleteFileServlet.class.getResourceAsStream("AwsCredentials.properties"));
		
	    AmazonS3Client s3 = new AmazonS3Client(credentials);
	    
	    String deleteFileName = request.getParameter("videoName").replaceAll("_", " ");//modification
	    if(deleteFileName != null){
	    	s3.deleteObject("assignment2-video", deleteFileName);
	    	rds.deletVideo(deleteFileName);
	    }
	    
	    getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
}
