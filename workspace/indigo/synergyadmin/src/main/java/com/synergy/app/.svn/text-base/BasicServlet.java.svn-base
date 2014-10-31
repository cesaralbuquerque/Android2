package com.synergy.app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;

import com.synergy.model.Video;
import com.synergy.service.VideoService;

public class BasicServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-cache");

		WebApplicationContext springContext = (WebApplicationContext) req.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		String op = req.getParameter("op");

		if ("activeAccount".equals(op)) {
			// ClientService clientService = (ClientService)
			// springContext.getBean("clientService");
			// Client client =
			// clientService.find(Long.parseLong(req.getParameter("cid")));
			// if (client == null ||
			// !client.getUsername().equals(req.getParameter("uname"))) {
			// resp.getOutputStream().print("ERROR: Account not found");
			// return;
			// }
			// client.setConfirmStatus(Client.CONFIRMED);
			// clientService.save(client);
			// req.setAttribute("username", client.getUsername());
			// req.getRequestDispatcher("/AccountActivated.jsp").forward(req,
			// resp);
			return;
		}

		if ("synergyPanel".equals(op)) {
			try {
				req.getRequestDispatcher("/SynergyPanel.jsp").forward(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}

		if ("viewDemo".equals(op)) {
			String vId = req.getParameter("vId");
			VideoService videoService = (VideoService) springContext.getBean("videoService");
			Video v = videoService.getVideoByVideoId(vId);
			if (v == null) {
				return;
			}
			req.getRequestDispatcher("/ViewVideoDemo.jsp").forward(req, resp);
			return;
		}

		if ("synergyVisitor".equals(op)) {
			req.setAttribute("cid", req.getParameter("cid"));
			req.setAttribute("confid", "");
			req.getRequestDispatcher("/LoadSynergyPanel.jsp").forward(req, resp);
			return;
		}
		// op=conference&cid=" + user.getClient().getId() + "&confid="
		if ("conference".equals(op)) {
			req.setAttribute("cid", req.getParameter("cid"));
			req.setAttribute("confid", req.getParameter("confid"));
			req.getRequestDispatcher("/LoadSynergyPanel.jsp").forward(req, resp);
			return;
		}

		if ("openSynergyPanel".equals(op)) {
			// req.setAttribute("cid", req.getParameter("cid"));
			// req.setAttribute("confid", req.getParameter("confid"));
			// req.getRequestDispatcher("/SynergyPanel.jsp").forward(req, resp);

			String confid = req.getParameter("confid");
			String oMode = "0";
			if (confid != null && confid.length() > 0 && !"null".equals(confid)) {
				oMode = "1";
			}
			if ("6".equals(req.getParameter("oMode"))) {
				oMode = "6";
				confid = req.getParameter("toDestId");
			}

			req.setAttribute("oMode", oMode);
			req.setAttribute("cid", req.getParameter("cid"));
			req.setAttribute("canResize", "true");
			req.setAttribute("toDestId", confid);
			req.setAttribute("uid", req.getParameter("uid"));
			if ("1".equals(oMode) || "6".equals(oMode)) {
				req.getRequestDispatcher("/SynergyConferenceNew.jsp").forward(req, resp);
			} else {
				req.getRequestDispatcher("/SynergyPanelNew.jsp").forward(req, resp);
			}
			return;
		}

		if ("openSynergyPanel2".equals(op)) {
			req.setAttribute("oMode", req.getParameter("oMode"));
			req.setAttribute("cid", req.getParameter("cid"));
			req.setAttribute("canResize", req.getParameter("canResize"));
			req.setAttribute("toDestId", req.getParameter("toDestId"));
			req.getRequestDispatcher("/SynergyPanelNew.jsp").forward(req, resp);
			return;
		}

	}
}
