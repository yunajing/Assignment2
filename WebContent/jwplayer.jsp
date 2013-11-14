<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mytube</title>
<script type="text/javascript"
	src="https://s3.amazonaws.com/assignment2-jwplayer/jwplayer/jwplayer.js">
	</script>
</head>
<body>
<script>
jwplayer("myplayer").setup({
        'file': "rtmp://s3fzodeyzk00fl.cloudfront.net/cfx/st/<%= request.getAttribute("name")%>",
        'height': 270,
		'provider': "rtmp",
        'streamer': "rtmp://s3fzodeyzk00fl.cloudfront.net/cfx/st",
        'modes': [{
       		     type: "flash",
           	     src: "https://s3.amazonaws.com/assignment2-jwplayer/jwplayer/jwplayer.flash.swf"
              
        },
		{
            type: "html5",
            config: {
			file: "http://dyiqwq9e0df2f.cloudfront.net/<%= request.getAttribute("name")%>",
                provider: "video"
            }
		}
		],
        'width': 480
    });
</script>

</body>
</html>