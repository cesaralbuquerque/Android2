<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.synergy.service.AuthenticateService"%>
<%@page import="java.util.Date"%>
<%@page import="com.synergy.app.SessionUtil"%>

<%@page import="com.synergy.model.Subscriber"%>
<%@page import="com.synergy.model.Company"%><html dir="ltr" lang="en-US">

<head>

<meta charset="UTF-8" />

<title>Login | Synergychat</title>

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



</head>

<body
	class="page page-id-88 page-template page-template-default logged-in admin-bar">

<%
	response.setHeader("Cache-Control", "no-cache");
	String errorMessage = (String) request.getAttribute("errorMessage");
	String infoMessage = (String) request.getAttribute("infoMessage");
	WebApplicationContext springContext = (WebApplicationContext) request.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	AuthenticateService authService = (AuthenticateService) springContext.getBean("authenticateService");
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String remember = request.getParameter("remember");
	String reply = request.getParameter("reply");
	String activatedEmail = (String) request.getAttribute("activatedEmail");
	if (username != null && password != null) {
		long day = 1000 * 60 * 60 * 24;
		boolean isRemember = "on".equalsIgnoreCase(remember);
		if (isRemember) {
			//if remember, then set the cookie to 3 months
			day = day * 90;
		}
		final Date expiry = new Date(new Date().getTime() + day);
		String sessionId = authService.authenticateDate(username, password, expiry);
		if (sessionId != null) {
			Subscriber sub =  authService.validateSessionId(sessionId);
			if(sub!=null){
				if(Company.CONFIRMED.equals(sub.getCompany().getConfirmStatus())){
					if (isRemember) {
						//just create the cookie, if remember property is set
						Cookie cookie = new Cookie(SessionUtil.SYNERGY_CHAT_COOKIE, sessionId);
						cookie.setMaxAge((int) expiry.getTime());
						response.addCookie(cookie);
					}
					request.getSession().setAttribute(SessionUtil.SYNERGY_CHAT_SESSION_ID, sessionId);
					String par = "";
					if (reply != null && reply.length() > 0) {
						par = "?reply=" + reply;
					}
					request.getRequestDispatcher("/ui" + par).forward(request, response);
				}else{
					errorMessage = "This account has not been confirmed. Please check your email for the confirmation info.";
				}
			}else{
				errorMessage = "Invalid session.";
			}
		} else {
			errorMessage = "Invalid username or password.";
		}
	} else {
		username = (String) request.getAttribute("email");
		username = username == null ? "" : username;
	}
%>
<div class="wrap">

<div class="header">

<div class="logo"><a href="http://www.synergychat.com/"><img
	src="wp-content/themes/synergychat-public/images/logo.gif"
	alt="" /></a></div>
<div class="header-left">
<div class="top-links">

<ul>

	<li><a href="https://www.synergychat.com:8443/ui"> <img
		src="wp-content/themes/synergychat-public/images/bg-login.gif"
		alt="" /> </a></li>

	<li><span class="conatct-link"><a
		href="http://www.synergychat.com/contact-us">Contact Us</a></span></li>

</ul>

</div>

<ul class="nav">

	<div class="menu-topmenu-container">
	<ul id="menu-topmenu" class="menu">
		<li id="menu-item-258"
			class="menu-item menu-item-type-custom menu-item-object-custom menu-item-home menu-item-258"><a
			href="http://www.synergychat.com/">Home</a></li>
		<li id="menu-item-217"
			class="menu-item menu-item-type-post_type menu-item-object-page menu-item-217"><a
			href="http://www.synergychat.com/about-us-4/">About Us</a></li>
		<li id="menu-item-34"
			class="menu-item menu-item-type-post_type menu-item-object-page menu-item-34"><a
			href="http://www.synergychat.com/why-synergychat/">Why
		SynergyChat?</a></li>
		<li id="menu-item-33"
			class="menu-item menu-item-type-post_type menu-item-object-page menu-item-33"><a
			href="http://www.synergychat.com/product-services/">Product
		&#038; Services</a></li>
		<li id="menu-item-32"
			class="menu-item menu-item-type-post_type menu-item-object-page menu-item-32"><a
			href="http://www.synergychat.com/support/">Support</a></li>
	</ul>
	</div>
</ul>

</div>



</div>
<!--end of div header--></div>
<!--end of top-wrap-->
<div id="container">
<div id="content" role="main">


<div id="post-88" class="post-88 page type-page status-publish hentry">
<h1 class="entry-title"></h1>

<div class="entry-content">
<div class="content">
<div class="wrap">
<div class="content-mid">
<div class="content-top-heading" style="height:50px">
<div class="content-top-heading">
<div class="why-syn"></div>
<div class="head-links"><br />
<br />
</div>
<p><!--end of head links-->
</div>
</p>
</div>
<div class="content-top">
<div class="login">
<h1>Please Login</h1>
<div class="user">
<h2>Existing User Login Here</h2>
<form action="ui" method="post">
<div style="text-align:center;color:#0000FF">
<%if(activatedEmail!=null){ 
	username = activatedEmail;
%>
<%=infoMessage %>
<%} %>
</div>
<div style="text-align:center;color:#FF0000">
<%=(errorMessage==null?"":errorMessage)%></div>
<ul class="user-login">
	<li class="label">Email</li>
	<li><input id="username" name="username" value="<%=username%>"
		type="text" class="user-input" /></li>
	<li class="label">Password</li>
	<li><input id="password" name="password" type="password"
		class="user-input" /></li>
	<li class="label">&nbsp;</li>
	<li><input id="remember" name="remember" type="checkbox" />&nbsp;Remember
	Me<br/><a href="forgot-password">Forgot Password?</a></li>
	<li class="login">
	<input name="submit"
		src="https://www.synergychat.com:8443/wp-content/themes/synergychat-public/images/login.gif"
		type="image" style="cursor: hand" class="button" />
	</li>
	
</ul>
</form>
</div>
<div class="new-user">
<h2>New User Create Account</h2>
<ul class="user-login">
	<li class="label"><li>
	<li style="text-align:center;padding-left:20px">
	
	<h4><span>Sign up In 60 Seconds</span></h4>

	<a href="registration"><img
		src="https://www.synergychat.com:8443/wp-content/themes/synergychat-public/images/home-signup.gif"
		alt="" /></a></li>

</ul>
</div>
</p>
</div>
</p>
</div>
<div class="comtent-bottom">.</div>
</p>
</div>
</p>
</div>
<p><!--end of wrap-->
</div>
<p><!--end of content--></p>
</div>
<!-- .entry-content --></div>
<!-- #post-## --></div>
<!-- #content --></div>
<!-- #container -->

<div class="wrap">
<!--  
<div class="footer"><a href="#"><img
	src="https://www.synergychat.com:8443/wp-content/themes/synergychat-public/images/follow-on-twitter.gif"
	alt="" /></a></div>
</div>
-->
<!--end of footer-->


</body>
</html>