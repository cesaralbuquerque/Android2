<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>

<%@page import="com.synergy.util.Util"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Synergy Chat</title>
<script src="flash/scripts/swfobject.js" language="JavaScript"></script>
</head>
<body>
<%
			String ssId = Util.toString(request.getParameter("ssId"));
		%>
<script type="text/javascript">
			var flashvars = {};
			
			//////////////////////////////////////////////////////////////////////////////////////////////////////
			// CUSTUMIZABLE PARAMETERS ///////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////////////////
			
			// video width
			var stageW = "100%";
			
			// video height		NOTE: you should include the control bar height
			var stageH = "100%";
			
			///////////////////////////////////////////////////////////////////////
			// PATHS //////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////
			flashvars.vidName="<%=ssId%>";
			flashvars.serverUrl="rtmp://64.71.230.10:1937/splitstream";
			flashvars.bufferTime=0;
			flashvars.events=1;
			flashvars.debug=1;
			flashvars.fitFiltered=0;
			flashvars.playerID="flashPlayer";
			flashvars.playerContainerID="paneSidebar&defaultCenterMessage=<FONT FACE='Arial' SIZE='20' >Connecting to Server...</font>";
			flashvars.defaultCenterMessageWidth=300;
			flashvars.defaultCenterMessageHTML=true;
			flashvars.defaultCenterImageURL="images/logo.png";
			flashvars.defaultCenterImagePosition=0;
			flashvars.showMouse=1;
			flashvars.forceObjectEncoding=-1;
			flashvars.showNotLive=0;
						
			var params = {};
			params.scale = "exactfit";
			params.allowfullscreen = "true";
			params.allowScriptAccess = "always";
			params.salign = "tl";
			params.wMode = "opaque";
			
			var attributes = {};
			swfobject.embedSWF("flash/SMLPlayer.swf", "flashDiv", stageW, stageH, "10.0.0", "flash/expressInstall.swf", flashvars, params, attributes);
		</script>
<table width="100%"  height="100%">
	<tr>
	<td align="center">
		<div id="flashDiv">
			<a href="http://www.adobe.com/go/getflashplayer">
				<img src="https://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" />
			</a>
		</div><br/>
	</td>
	</tr>
	</table>
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-10703188-1");
pageTracker._trackPageview();
} catch(err) {}</script>
<!-- Start Quantcast tag --> 
<script type="text/javascript"> _qoptions={ qacct:"p-f6guzos4cxKUQ" }; </script> <script type="text/javascript" src="http://edge.quantserve.com/quant.js"></script> <noscript> <img src="http://pixel.quantserve.com/pixel/p-f6guzos4cxKUQ.gif" style="display: none;" border="0" height="1" width="1" alt="Quantcast"/> </noscript> 
<!-- End Quantcast tag -->
		
</body>
</html>