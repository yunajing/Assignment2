<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.amazonaws.*" %>
<%@ page import="com.amazonaws.auth.*" %>
<%@ page import="com.amazonaws.services.ec2.*" %>
<%@ page import="com.amazonaws.services.ec2.model.*" %>
<%@ page import="com.amazonaws.services.s3.*" %>
<%@ page import="com.amazonaws.services.s3.model.*" %>
<%@ page import="com.amazonaws.services.dynamodbv2.*" %>
<%@ page import="com.amazonaws.services.dynamodbv2.model.*" %>

<%!// Share the client objects across threads to
    // avoid creating new clients for each web request
    private AmazonEC2      ec2;
    private AmazonS3        s3;
    private RDSmanager     rds;%>

<%
    /*
     * AWS Elastic Beanstalk checks your application's health by periodically
     * sending an HTTP HEAD request to a resource in your application. By
     * default, this is the root or default resource in your application,
     * but can be configured for each environment.
     *
     * Here, we report success as long as the app server is up, but skip
     * generating the whole page since this is a HEAD request only. You
     * can employ more sophisticated health checks in your application.
     */
    if (request.getMethod().equals("HEAD")) return;
%>

<%
    if (ec2 == null) {
        AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
        ec2    = new AmazonEC2Client(credentialsProvider);
        s3     = new AmazonS3Client(credentialsProvider);
        rds = new RDSmanager();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <title>My Youtube</title>
    <link rel="stylesheet" href="styles/styles.css" type="text/css" media="screen">
</head>
<body>
<h3>Video Upload:</h3>
Select a file to upload: <br />
<form action="UploadFileServlet" method="post"
                        enctype="multipart/form-data">
<input type="file" name="file" size="50" />
<br />
<input type="submit" value="Upload File" />
</form>

		<h2>List of the videos</h2>
		<table >
			<%
				String p ="https://s3.amazonaws.com/";
						
						String bucket_name = "assignment2-video";
						LinkedList<String> videos = rds.addVideo();
						if( videos != null) {
							for(int i1 =0; i1 < videos.size(); i1++)
							{
								String url =p + bucket_name+"/"+ videos.get(i1).replace(" ","+");
			%>

			<tr>

				<% if(videos.get(i1).contains(".mp4") || videos.get(i1).contains(".flv"))%>
				<td>
					<p>
						<b><font face="Calibri" size="5" ><%=videos.get(i1)  %></font></b></p>
				</td>

				
				<% }
				} %>
				</table>
<br/>
<br/>
<br/>
</body>
</html>