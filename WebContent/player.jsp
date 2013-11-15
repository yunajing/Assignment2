<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<HTML>
<HEAD>
<TITLE><%= request.getAttribute("name")%></TITLE>	

<!--  This HTML file plays an .mp4 or .flv media file using JW Player 6.
The following code is from the Longtail Video Setup Wizard at 
http://www.longtailvideo.com/support/jw-player-setup-wizard.
-->

<!-- Call the JW Player JavaScript file, jwplayer.js. 
Replace DOWNLOAD-DISTRIBUTION-DOMAIN-NAME with the domain name of your 
CloudFront download distribution, for example, d1234.cloudfront.net 
(begins with "d"). This causes a browser to download the JW Player file 
before streaming begins.
-->

<script type='text/javascript' src="https://s3.amazonaws.com/assignment2-jwplayer/jwplayer/jwplayer.js"></script>

</HEAD>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         ml class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title><%= request.getAttribute("name")%></title>
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
<BODY>

<!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href="index.jsp">Back to Main</a>
        </div>
      </div><!--/.navbar-collapse -->
    </div>

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
      <div class="container">
        <h3>You are now watching <%= request.getAttribute("name")%></h3>
        Presented to you by Amazon Cloudfront and Jwplayer <br />
        <div id='mediaplayer'></div>
		<script type="text/javascript">
   			jwplayer('mediaplayer').setup({
      		file: "rtmp://s3fzodeyzk00fl.cloudfront.net/cfx/st/<%= request.getAttribute("name")%>",
      		width: "720",
      		height: "480"
   			});
		</script>
      </div>
    </div>
    <footer>
        <p>&copy; yj2270 yf2289</p>
      </footer>



</BODY>
</HTML>