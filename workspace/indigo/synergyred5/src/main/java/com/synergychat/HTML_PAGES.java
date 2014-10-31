package com.synergychat;

public class HTML_PAGES {

	public static String getVideoMessageEmail(String user, String company, String subject, String message, String link) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\">	<title>Synergy Chat</title>");
		sb.append("</head><body style=\"margin: 0px; background: rgb(255, 255, 255) url(img/vert-bg2.gi) repeat-x scroll 0% 0%; font-family: Arial,Helvetica,ans-serif; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial;\">");
		sb.append("<div style=\"border: 1px solid rgb(4, 56, 84); margin: 11px auto 0px; background: rgb(255, 255, 255) no-repeat scroll 0% 0%; width: 700px; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial;\">");
		sb.append("<table style=\"margin: 0px auto; border-collapse: collapse;\" border=\"0\">");
		sb.append("<tbody><tr style=\"height: 120px;\">");
		sb.append("<th style=\"background: rgb(255, 255, 255) none repeat scroll 0% 0%; width: 500px; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; font-size: 13px; line-height: 18px; font-weight: normal;\">");
		sb.append("<img style=\"margin-top: 0px;\" src=\"http://www.synergychat.com/img/syn-logo.gif\" alt=\"Synergy Chat\">");
		sb.append("</th></tr></tbody></table><img style=\"margin: 0px;\" src=\"http://www.synergychat.com/img/divider.gif\" alt=\"\"><table width=\"84%\" border=\"0\" align=\"center\" cellpadding=\"10\" style=\"margin-bottom:20px;\"><tr>");
		sb.append("<td style=\"border-bottom:1px solid #000000;\">The user <strong>").append(user).append("</strong> from the company <strong>");
		sb.append(company).append("</strong> sent you one video message using the <a href=\"http://www.synergychat.com/\"><em>SynergyChat</em></a> services.</td>");
		sb.append("</tr></table><table width=\"84%\" border=\"0\" align=\"center\"><tr><td width=\"31%\">Video Subject</td>");
		sb.append("<td width=\"69%\">").append(subject).append("</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td></tr><tr>");
		sb.append("<td>Video Description</td>");
		sb.append("<td>").append(message).append("</td></tr></table><table width=\"84%\" border=\"0\" align=\"center\"><tr><td>&nbsp;</td>");
		sb.append("</tr><tr><td><strong>Please use the following link to view the video :</strong></td></tr><tr><td>");
		sb.append("<a href=\"").append(link).append("\">").append(link).append("</a></td></tr><tr><td>&nbsp;</td></tr></table></div>");
		sb.append("<table width=\"55%\" border=\"0\" align=\"center\">  <tr>");
		sb.append("<td style=\"font-size:12px;\"><div align=\"center\">&copy; All Rights Reserved - Synergy Chat Services - 2011</div></td>");
		sb.append("</tr></table></body></html>");
		return sb.toString();
	}
	
	public static String getTextMessageEmail(String user, String company, String subject, String message) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\">	<title>Synergy Chat</title>");
		sb.append("</head><body style=\"margin: 0px; background: rgb(255, 255, 255) url(img/vert-bg2.gi) repeat-x scroll 0% 0%; font-family: Arial,Helvetica,ans-serif; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial;\">");
		sb.append("<div style=\"border: 1px solid rgb(4, 56, 84); margin: 11px auto 0px; background: rgb(255, 255, 255) no-repeat scroll 0% 0%; width: 700px; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial;\">");
		sb.append("<table style=\"margin: 0px auto; border-collapse: collapse;\" border=\"0\">");
		sb.append("<tbody><tr style=\"height: 120px;\">");
		sb.append("<th style=\"background: rgb(255, 255, 255) none repeat scroll 0% 0%; width: 500px; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; font-size: 13px; line-height: 18px; font-weight: normal;\">");
		sb.append("<img style=\"margin-top: 0px;\" src=\"http://www.synergychat.com/img/syn-logo.gif\" alt=\"Synergy Chat\">");
		sb.append("</th></tr></tbody></table><img style=\"margin: 0px;\" src=\"http://www.synergychat.com/img/divider.gif\" alt=\"\"><table width=\"84%\" border=\"0\" align=\"center\" cellpadding=\"10\" style=\"margin-bottom:20px;\"><tr>");
		sb.append("<td style=\"border-bottom:1px solid #000000;\">The user <strong>").append(user).append("</strong> from the company <strong>");
		sb.append(company).append("</strong> sent you one text message using the <a href=\"http://www.synergychat.com/\"><em>SynergyChat</em></a> services.</td>");
		sb.append("</tr></table><table width=\"84%\" border=\"0\" align=\"center\"><tr><td width=\"31%\">Subject:</td>");
		sb.append("<td width=\"69%\">").append(subject).append("</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td></tr><tr>");
		sb.append("<td>Message:</td>");
		sb.append("<td>").append(message).append("</td></tr></table><table width=\"84%\" border=\"0\" align=\"center\"><tr><td>&nbsp;</td>");
		sb.append("</tr><tr><td>&nbsp;</td></tr></table></div>");
		sb.append("<table width=\"55%\" border=\"0\" align=\"center\">  <tr>");
		sb.append("<td style=\"font-size:12px;\"><div align=\"center\">&copy; All Rights Reserved - Synergy Chat Services - 2011</div></td>");
		sb.append("</tr></table></body></html>");
		return sb.toString();
	}
	
	public static String getOfflineMessageEmail(String user, String group, String company, String subject, String message) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\">	<title>Synergy Chat</title>");
		sb.append("</head><body style=\"margin: 0px; background: rgb(255, 255, 255) repeat-x scroll 0% 0%; font-family: Arial,Helvetica,ans-serif; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial;\">");
		sb.append("<div style=\"border: 1px solid rgb(4, 56, 84); margin: 11px auto 0px; background: rgb(255, 255, 255) url(http://174.133.59.98/~technocr/percan/synergy/email/img/emailg.png) no-repeat scroll 0% 0%; width: 700px; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial;\">");
		sb.append("<table style=\"margin: 0px auto; border-collapse: collapse;\" border=\"0\">");
		sb.append("<tbody><tr style=\"height: 120px;\">");
		sb.append("<th style=\"background: rgb(255, 255, 255) none repeat scroll 0% 0%; width: 500px; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; font-size: 13px; line-height: 18px; font-weight: normal;\">");
		sb.append("<img style=\"margin-top: 0px;\" src=\"http://www.synergychat.com/img/syn-logo.gif\" alt=\"Synergy Chat\">");
		sb.append("</th></tr></tbody></table><img style=\"margin: 0px;\" src=\"http://www.synergychat.com/img/divider.gif\" alt=\"\"><table width=\"84%\" border=\"0\" align=\"center\" cellpadding=\"10\" style=\"margin-bottom:20px;\"><tr>");
		sb.append("<td style=\"border-bottom:1px solid #000000;\">The guest <strong>").append(user).append("</strong> sent to the group <strong>").append(group).append("</strong> from the company <strong>");
		sb.append(company).append("</strong>, the below offline message using the <a href=\"http://www.synergychat.com/\"><em>SynergyChat</em></a> services.</td>");
		sb.append("</tr></table><table width=\"84%\" border=\"0\" align=\"center\"><tr><td width=\"31%\">Subject: </td>");
		sb.append("<td width=\"69%\">").append(subject).append("</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td></tr><tr>");
		sb.append("<td>Message</td>");
		sb.append("<td>").append(message).append("</td></tr></table>");
		//<table width=\"84%\" border=\"0\" align=\"center\"><tr><td>&nbsp;</td>");
		//sb.append("</tr><tr><td><strong>Please use the following link to view the video :</strong></td></tr><tr><td>");
		//sb.append("<a href=\"").append(link).append("\">").append(link).append("</a></td></tr><tr><td>&nbsp;</td></tr></table></div>");
		sb.append("<table width=\"55%\" border=\"0\" align=\"center\">  <tr>");
		sb.append("<td style=\"font-size:12px;\"><div align=\"center\">&copy; All Rights Reserved - Synergy Chat Services - 2009</div></td>");
		sb.append("</tr></table></body></html>");
		return sb.toString();
	}

	public static String getVideoConferenceEmail(String message, String link) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\"><title>Synergy Chat</title>");
		sb.append("</head><body style=\"margin: 0px; background: rgb(255, 255, 255) repeat-x scroll 0% 0%; font-family: Arial,Helvetica,sans-serif; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial;\">");

		sb.append("<div style=\"border: 1px solid rgb(4, 56, 84); margin: 11px auto 0px; background: rgb(255, 255, 255) no-repeat scroll 0% 0%; width: 700px; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial;\">");
		sb.append("<table style=\"margin: 0px auto; border-collapse: collapse;\" border=\"0\">	<tbody><tr style=\"height: 120px;\">");
		sb.append("<th style=\"background: rgb(255, 255, 255) none repeat scroll 0% 0%; width: 500px; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; font-size: 13px; line-height: 18px; font-weight: normal;\">");
		sb.append("<img style=\"margin-top: 0px;\" src=\"http://www.synergychat.com/img/syn-logo.gif\" alt=\"Synergy Chat\"></th></tr></tbody></table>");

		sb.append("<img style=\"margin: 0px;\" src=\"http://www.synergychat.com/img/divider.gif\" alt=\"\">");

		sb.append("<table width=\"84%\" border=\"0\" align=\"center\" cellpadding=\"10\" style=\"margin-bottom:20px;\">");
		sb.append("<tr><td style=\"border-bottom:1px solid #000000;\">").append(message).append("</td> </tr></table>");
		sb.append("<table width=\"84%\" border=\"0\" align=\"center\"><tr><td>&nbsp;</td></tr><tr>");
		sb.append("<td><strong>Please use the following link to join the meeting :</strong></td></tr><tr>");
		sb.append("<td><a href=\"").append(link).append("\">").append(link).append("</a></td>");
		sb.append("</tr> <tr>  <td>&nbsp;</td> </tr></table></div><table width=\"55%\" border=\"0\" align=\"center\"><tr>");
		sb.append("<td style=\"font-size:12px;\"><div align=\"center\">&copy; All Rights Reserved - Synergy Chat Services - 2009</div></td>");
		sb.append("</tr></table></body></html>");
		return sb.toString();
	}

}
