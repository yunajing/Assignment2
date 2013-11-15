Cloud Computing - Assigment 2 
Team Member: yj2270 Yuna Jing, yf2289 Yangliu Feng

We implemented a video streaming and sharing application and hosted it on the cloud. Functions includes:
1. RDS database:
    --> Established a rds database to store details about the uploaded videos, including the name, upload timestamp, and a cumulative rating. 
    --> RDS is created and configured using code.    
2. Upload:
   --> Created a s3 bucket to store uploaded videos using code
   --> If a file is not .mp4 or .flv, it won't be uploaded and an error message will occur in the webpage.
   --> Created a upload button to upload new videos to the cloud.
3. Delete: 
   --> Created a trash button for each video to delete it. 
   --> If a video is deleted, it is no longer displayed in the webpage. 
4. List: 
   --> A list of videos is shown on the webpage, including all available videos in s3 bucket.
   --> When a video is uploaded/deleted, it will be added/removed from the list.
  --> The videos are listed according to their cumulative rating points, from highest to lowest. 
5. Rating:
   --> Users can rate each video listed on the webpage, the cumulative rating result will displayed in the webpage.
6. Stream: 
    --> Users are allowed to watch uploaed videos on the browser by clicking the play button for each one. 
    --> CloudFront is configured using code. 
    --> Created both distributions of Streaming(for Flash) and Downloading(for HTML5).
  
