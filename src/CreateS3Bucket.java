import java.io.IOException;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;


public class CreateS3Bucket {
	
	public static void main(String[] args) throws IOException{
		AWSCredentials credentials = new PropertiesCredentials(
	   			 CreateS3Bucket.class.getResourceAsStream("AwsCredentials.properties"));
		
	        AmazonS3Client s3 = new AmazonS3Client(credentials);
	        
	        String bucketname = "assignment2-video";
	        List <Bucket> bucketnames = s3.listBuckets();
			
			for (Bucket bucket : bucketnames){
				if (bucketname.equalsIgnoreCase(bucket.getName())){
					System.out.println("S3 bucket to store videos: " + bucketname);
					return;
				}
			}

			s3.createBucket(bucketname);
			System.out.println("Successfully create S3 bucket to store videos: " + bucketname);
	}

}
