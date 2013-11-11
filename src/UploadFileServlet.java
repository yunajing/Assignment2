import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;


public class UploadServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final String  UPLOAD_DIR = "uploadVideos";
	private boolean isMultipart;
	private String uploadFileDir;
	private String uploadFilePath;
	private static RDSmanager rds;
	
	public UploadServlet(){
		super();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		isMultipart = ServletFileUpload.isMultipartContent(request);
		rds = new RDSmanager();
		
		if(isMultipart){
			AWSCredentials credentials = new PropertiesCredentials(
		   			 CreateS3Bucket.class.getResourceAsStream("AwsCredentials.properties"));
			
		    AmazonS3Client s3 = new AmazonS3Client(credentials);
		    
		    DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		    fileFactory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		    
		    ServletFileUpload fileUpload = new ServletFileUpload(fileFactory);
		    
		    //define the file directory to store uploaded videos
		    uploadFileDir = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
		    
		    System.out.println("uploadFileDir is: "+ uploadFileDir); 
		    
		    File uploadDirectory = new File(uploadFileDir);
		    if(!uploadDirectory.exists()){
		    	uploadDirectory.mkdir();
		    }
		    
		    try{
		    	List<FileItem> items = fileUpload.parseRequest(request);
		    	Iterator<FileItem> itr = items.iterator();

		    	while(itr.hasNext()){
		    		FileItem fileItem = itr.next();
		    		boolean isFormField = fileItem.isFormField();
		    		if(!isFormField){
		    			String uploadFileName = new File(fileItem.getName()).getName();
		    			if((!uploadFileName.contains(".mp4"))&&(!uploadFileName.contains(".flv"))){
		    				request.setAttribute("message", "Only mp4 or flv can be uploaded.");
		    				
		    			}else{
		    				uploadFilePath = uploadFileDir + File.separator + uploadFileName;
		    				File storeFile = new File(uploadFilePath);
		    				fileItem.write(storeFile);
		    				
		    				PutObjectRequest putObjectRequest = new PutObjectRequest("assignment2-video", uploadFileName, storeFile);
		    				CannedAccessControlList accessControlList = CannedAccessControlList.PublicReadWrite;
                            putObjectRequest.setCannedAcl(accessControlList);
		    				s3.putObject(putObjectRequest);
		    				
		    				request.setAttribute("message", "Video Uploaded Successfully");
		    				System.out.println("NAME: " +fileItem.getName());
		    				System.out.println("SIZE: " +fileItem.getSize());
		    				System.out.println("TYPE: " +fileItem.getContentType());
		    				
		    				//rds.setVideo(fileName, 0);
		    				
		    			}
		    			}
		    		}
		    		
		    	}catch (Exception ex) {
                    request.setAttribute("message", "Error Occurs"
                            + ex.getMessage());
		    	
		    }
		    getServletContext().getRequestDispatcher("/index.jsp").forward(
                    request, response);
		}
	}
}














