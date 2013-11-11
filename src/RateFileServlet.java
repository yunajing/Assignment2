import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RateFileServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static RDSmanager rds;
	
	public RateFileServlet(){
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		rds = new RDSmanager();
		
		String rateFileName = request.getParameter("rateVideoName").replaceAll("_", " ");//modification
		
		int rate = 0;
		
		if(rateFileName != null){
			
			if (request.getParameter("rating")!= null){
				rate = Integer.parseInt(request.getParameter("rating"));	
			}
			
			rds.rateVideo(rateFileName, rate);
			
		}
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
}






