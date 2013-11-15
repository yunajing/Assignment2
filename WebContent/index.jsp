<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.amazonaws.*" %>
<%@ page import="com.amazonaws.auth.*" %>
<%@ page import="com.amazonaws.services.ec2.*" %>
<%@ page import="com.amazonaws.services.ec2.model.*" %>
<%@ page import="com.amazonaws.services.s3.*" %>
<%@ page import="com.amazonaws.services.s3.model.*" %>
<%@ page import="com.amazonaws.services.dynamodbv2.*" %>
<%@ page import="com.amazonaws.services.dynamodbv2.model.*" %>
<%@ page import="java.util.*"%>
<%@ page import="com.manager.assignment2.RDSmanager"%>

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
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         ml class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">

        <link rel="stylesheet" href="styles/bootstrap.min.css">
        <style>
            body {
                padding-top: 50px;
                padding-bottom: 20px;
            }
        </style>
        <link rel="stylesheet" href="styles/bootstrap-theme.min.css">
        <link rel="stylesheet" href="styles/main.css">

        <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
        <title>MyTube</title>
        <link rel="stylesheet" href="styles/styles.css" type="text/css" media="screen">
        <script type="text/javascript" src="https://s3.amazonaws.com/assignment2-jwplayer/jwplayer/jwplayer.js"></script>
        <script type="text/javascript">jwplayer.key="IFhVDTGUAS2rMT6B2r9dmwEzCZv17bBbXti9RA==";</script>
    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand">My YouTube</a>
        </div>
      </div><!--/.navbar-collapse -->
    </div>

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
      <div class="container">
        <h3>Video Upload:</h3>
        Select a file to upload: <br />
        <form action="UploadFileServlet" method="post"
                        enctype="multipart/form-data">
          <input type="file" name="file" size="50" />
          <input type="submit" value="Upload File" height="42"/>
        </form>
      </div>
    </div>

    <div class="container">
        <h2>List of the videos</h2>
    <table >
      <%
        String p ="rtmp://s3fzodeyzk00fl.cloudfront.net/cfx/st/";
            
            String bucket_name = "assignment2-video";
            LinkedList<String> videos = rds.getVideo();
            LinkedList<String> times = rds.getTime();
            if( videos != null) {
              for(int i1 =0; i1 < videos.size(); i1++)
              {
                String url =p+videos.get(i1).replace(" ","+");
      %>

      <tr>
      <td>
        <% if(videos.get(i1).contains(".mp4") || videos.get(i1).contains(".flv"))%>
          <p><b><font face="Calibri" size="5" ><%=videos.get(i1)  %></font></b></p>
      </td>
      <td>
          <p><font face="Calibri" size="3" ><%="Uploaded at: "+times.get(i1)  %></font></p>
      </td>
      <td>
      <form action="PlayFileServlet" method="get" enctype="multipart/">
      <input type="image"  src="http://cdn.pastemagazine.com/www/articles/play-button-red-300x300.png" width="42" height="42" value="play"/>
        <input type="hidden" name = "name" value=<%= videos.get(i1).replaceAll(" ", "_")%>>
        
       </form>
      </td>
    
      <td><form action="DeleteFileServlet" method="post"
            enctype="multipart/">
            <input type="hidden" name="videoName"
              value=<%= videos.get(i1).replaceAll(" ", "_") %>>
              <input
              type="image" src="http://etc-mysitemyway.s3.amazonaws.com/icons/legacy-previews/icons/simple-red-square-icons-business/128745-simple-red-square-icon-business-trashcan.png" height="42" width="42" name="Delete" value="Submit">
      </form>
      </td>
      <td><p>
            &nbsp;&nbsp;
            Current Rating: <%=rds.getObjectRating(videos.get(i1))%>
            &nbsp;
      </p></td>
      <td><form action="RateFileServlet" method="post"
            enctype="multipart/">
            <input type="hidden" name="rateVideoName"
              value=<%= videos.get(i1).replaceAll(" ", "_") %>>
            <tr><td>Select Rating <input type = "radio"
              name="rating" value ="1" onchange="this.form.submit()"/>1
              <input type = "radio"
              name="rating" value ="2" onchange="this.form.submit()"/>2
              <input type = "radio"
              name="rating" value ="3" onchange="this.form.submit()"/>3
              <input type = "radio"
              name="rating" value ="4" onchange="this.form.submit()"/>4
              <input type = "radio"
              name="rating" value ="5" onchange="this.form.submit()"/>5
      </form></td>
        
        <% }
        } %>
        
    </table>
      </div>

      <hr>

      <footer>
        <p>&copy; yj2270 yf2289</p>
      </footer>
    </body>
</html>
