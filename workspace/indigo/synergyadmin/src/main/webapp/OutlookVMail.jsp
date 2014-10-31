<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>

<%@page import="com.synergy.util.Util"%>

<%@page import="com.synergy.util.SynergyConfig"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.synergy.service.*"%>

<%@page import="com.synergy.model.Subscriber"%>
<%@page import="com.synergy.app.SessionUtil"%><html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Synergy Chat</title>
<script type="text/javascript" src="flash/scripts/swfobject.js"></script>
<script type="text/javascript" src="js/synergy.js"></script>
<style>
body { margin: 0px; overflow:hidden }
</style>
</head>
<body>

<script type="text/javascript">
			var flashvars = {};
			
			//////////////////////////////////////////////////////////////////////////////////////////////////////
			// CUSTUMIZABLE PARAMETERS ///////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////////////////
			
			// video width
			var stageW = "500";
			
			// video height		NOTE: you should include the control bar height
			var stageH = "400";
			
			///////////////////////////////////////////////////////////////////////
			// PATHS //////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////
			flashvars.sessionId = "<%=request.getParameter("SESSIONID")%>";
			flashvars.appUrl="<%=SynergyConfig.instance().getRed5RtmpServer()%>";
			flashvars.appUrlv="<%=SynergyConfig.instance().getWowzaRtmpServer()%>";
						
			var params = {};
			params.scale = "default";
			params.allowfullscreen = "false";
			params.salign = "tl";
			params.bgcolor = "000000";
			
			var attributes = {};
			swfobject.embedSWF("flash/outLookVMail_1.swf", "SynergyChatFlash", stageW, stageH, "9.0.0", "flash/expressInstall.swf", flashvars, params, attributes);
		</script>
		
	</head>
	<body bgcolor="#efefef">
	
	<table width="100%"  height="100%">
	<tr>
	<td align="center">
		<div id="SynergyChatFlash">
			<a href="http://www.adobe.com/go/getflashplayer">
				<img src="https://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" />
			</a>
		</div><br/>
	</td>
	</tr>
	</table>
<div id="ssContainer"></div>
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-10703188-1");
pageTracker._trackPageview();
} catch(err) {}</script>

</body>
</html>