<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@page import="com.synergy.util.SynergyConfig"%>
<%@page import="com.synergy.app.SessionUtil"%>

<%@page import="com.synergy.util.CipherUtil"%><html
	xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<title>VMail Player</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

<style type="text/css">
html,body,#containerA,#containerB {
	height: 100%;
}

body {
	margin: 0;
	padding: 0;
	overflow: hidden;
}
</style>
<link rel="stylesheet" type="text/css" media="all"
	href="wp-content/themes/synergychat-public/style.css" />
<!-- TRUEedit 1.0 by Zachary Segal: http://www.illproductions.com -->
<!-- WP-Cufon Plugin 1.6.8 START  -->
<script type="text/javascript"
	src="wp-content/plugins/wp-cufon/js/cufon-yui.js"></script>


<!-- WP-Cufon Fonts found  -->
<script
	src="wp-content/plugins/fonts/Droid_Sans_400-Droid_Sans_700.font.js"
	type="text/javascript"></script>



<!-- WP-Cufon Plugin Replacements -->
<script type="text/javascript">
	Cufon.replace('.nav li a', {
		fontFamily :'Droid Sans',
		hover :true
	});

	Cufon
			.replace(
					'.nav li a:hover,.nav li .active a,.content-top-heading .why-syn,h1,h2,h3,h4,.why-syn,.text,.tm-text,.start-text,.head-links,.statement,.permonth ,.price,.main-text,.content-text-h,.bullet-last,.bullet-security,.bullet ,.support-heading,.monthly-payment,.annual-payment,.start-description li,.contact-h,.label,.registration-form,.email-bar,.contact-h,li.product,li.product,li.users ,.total,.bullet-first,.conatct-link ,.content-top-heading .head-links ul li ,.cloud li ,p',

					{
						fontFamily :'Droid Sans',
						hover :true
					});
</script>
<!-- WP-Cufon END  -->
<style type="text/css" media="print">
#wpadminbar {
	display: none;
}
</style>

<%
	String sessionId = SessionUtil.getSessionId(request);
	String encVideoId = (String) request.getAttribute("encVideoId");
	String videoId = null;
	try {
		String[] dec = CipherUtil.decrypt(encVideoId).split(",");
		if (dec.length < 3) {
			throw new IllegalArgumentException(
					"Invalid URL: Video not found in our server.");
		}
		videoId = dec[1];
	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException("Error decrypting: "
				+ e.getMessage());
	}
%>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
<script src="http://cdn.jquerytools.org/1.2.6/all/jquery.tools.min.js"></script>
<script src="http://static.flowplayer.org/js/global-0.54.js?v=054 "></script>
<script type="text/javascript" src="flash/scripts/swfobject.js"></script>
<script type="text/javascript" src="js/synergy.js"></script>
<script type="text/javascript">
			var flashvars = {};
			
			//////////////////////////////////////////////////////////////////////////////////////////////////////
			// CUSTUMIZABLE PARAMETERS ///////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////////////////
			
			// video width
			var stageW = 530;
			
			// video height		NOTE: you should include the control bar height
			var stageH = 400;
			
			///////////////////////////////////////////////////////////////////////
			// PATHS //////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////

			flashvars.videoKey = "<%=encVideoId%>";
			flashvars.sessionId = "<%=sessionId%>";
			flashvars.appUrl="<%=SynergyConfig.instance().getRed5RtmpServer()%>";
			flashvars.appUrlv="<%=SynergyConfig.instance().getWowzaRtmpServer()%>";
						
			var params = {};
			params.scale = "noscale";
			params.allowfullscreen = "true";
			params.salign = "tl";
			params.bgcolor = "000000";
			params.wmode = "transparent";
			
			var attributes = {};
			//swfobject.embedSWF("http://www.synergychat.com:9091/flash/video-player_1.swf", "flashDiv", stageW, stageH, "9.0.0", false, flashvars, params, attributes);
			swfobject.embedSWF("flash/MainPlayback_1.swf", "SynergyChatFlash", stageW, stageH, "9.0.0", "flash/expressInstall.swf", flashvars, params, attributes);
		</script>

</head>
<body
	class="page page-id-88 page-template page-template-default logged-in admin-bar">

<div>

<div class="logo" style="display: none"><a
	href="http://www.synergychat.com/"><img
	src="wp-content/themes/synergychat-public/images/logo.gif" alt="" /></a></div>

<!--end of div header--></div>
<!--end of top-wrap-->
<br />
<div class="wrap"
	style="background-color: #E4E4E4; border: 2px solid black; width: 1034px">

<table>
	<tr>
		<td valign="top" align="center">
			<div style="height:25px;background-color:#FEFEFE; width:101%"></div>
			<a class="player" id="player"
			style="width: 500px; height: 340px; display: block"> </a> <script
			language="JavaScript">
			$f("player", "flowplayer/flowplayer-3.2.7.swf",
				{
					clip: {
						url: '<%=videoId%>.flv',
						provider: 'rtmp',
						autoPlay: true,
						autoBuffering: true
					},
					plugins: {
						rtmp: {
						url: 'flowplayer/flowplayer.rtmp-3.2.3.swf',
						netConnectionUrl: '<%=SynergyConfig.instance().getWowzaRtmpServer()%>'
					}
				}
			});
</script>
		</td>
		<td align="center" valign="top">
		<div id="SynergyChatFlash"><a
			href="http://www.adobe.com/go/getflashplayer"> <img
			src="https://www.adobe.com/images/shared/download_buttons/get_flash_player.gif"
			alt="Get Adobe Flash player" /> </a></div>
		<br />
		</td>
	</tr>
</table>
<div id="ssContainer"></div>
<!--end of wrap--></div>
<div style="text-align: right; font-size: 6pt;" class="wrap">
Powered by:<a target="_blank" href="http://www.synergychat.com"><img alt="" src="wp-content/themes/synergychat-public/images/logo.gif" style="width: 75px; height: 15px;padding-top:3px"></a>
</div>

<script type="text/javascript">
	var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl."
			: "http://www.");
	document
			.write(unescape("%3Cscript src='"
					+ gaJsHost
					+ "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
	try {
		var pageTracker = _gat._getTracker("UA-10703188-1");
		pageTracker._trackPageview();
	} catch (err) {
	}
</script>

</body>
</html>