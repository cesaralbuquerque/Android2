package com.synergy.app;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.util.file.Folder;

import com.synergy.page.ChatLogMemberPage;
import com.synergy.page.ClientRegisteredSuccess;
import com.synergy.page.ContactUs;
import com.synergy.page.ContactsMemberPage;
import com.synergy.page.EditUserMemberPage;
import com.synergy.page.ForgotPassword;
import com.synergy.page.GroupsMemberPage;
import com.synergy.page.JoinCompanyPage;
import com.synergy.page.MemberAdminLogin;
import com.synergy.page.NewGroupMemberPage;
import com.synergy.page.PaymentConfirmPage;
import com.synergy.page.PaymentPage;
import com.synergy.page.ProductsPage;
import com.synergy.page.ProfileMemberPage;
import com.synergy.page.RegisterBetaClient;
import com.synergy.page.RegisterBuyClient;
import com.synergy.page.WelcomeMemberPage;

public class QwicketApplication extends WebApplication {
	private static final Log log = LogFactory.getLog(QwicketApplication.class);
	private Folder uploadFolder;

	public QwicketApplication() {
		mountBookmarkablePage("client-sucessfully-registered", ClientRegisteredSuccess.class);
		mountBookmarkablePage("join-company", JoinCompanyPage.class);
		mountBookmarkablePage("registration", RegisterBetaClient.class);
		mountBookmarkablePage("registration-buy", RegisterBuyClient.class);
		mountBookmarkablePage("login", MemberAdminLogin.class);
		mountBookmarkablePage("profile", ProfileMemberPage.class);
		mountBookmarkablePage("forgot-password", ForgotPassword.class);
		mountBookmarkablePage("edit-user", EditUserMemberPage.class);
		mountBookmarkablePage("group", NewGroupMemberPage.class);
		mountBookmarkablePage("manage-contacts", ContactsMemberPage.class);
		mountBookmarkablePage("manage-groups", GroupsMemberPage.class);
		mountBookmarkablePage("chat-logs", ChatLogMemberPage.class);
		mountBookmarkablePage("home-member", WelcomeMemberPage.class);
		mountBookmarkablePage("contact-us", ContactUs.class);
		mountBookmarkablePage("products", ProductsPage.class);
		mountBookmarkablePage("payment", PaymentPage.class);
		mountBookmarkablePage("payment-confirm", PaymentConfirmPage.class);
	}

	@Override
	public String getConfigurationType() {
		// TODO Auto-generated method stub
		// return Application.DEPLOYMENT;
		return super.getConfigurationType();
	}

	@Override
	public Class getHomePage() {
		QwicketSession session = (QwicketSession) Session.get();
		if (session != null && (session).getUser() != null) {
			return WelcomeMemberPage.class;
		} else {
			return MemberAdminLogin.class;
		}
	}

	@Override
	public QwicketSession newSession(Request request, Response response) {
		return new QwicketSession(request);
	}

	@Override
	protected WebResponse newWebResponse(HttpServletResponse servletResponse) {
		// TODO Auto-generated method stub
		return super.newWebResponse(servletResponse);
	}

	@Override
	protected void init() {
		super.init();
		addComponentInstantiationListener(new org.apache.wicket.spring.injection.annot.SpringComponentInjector(this));
		getDebugSettings().setAjaxDebugModeEnabled(false);
		getSecuritySettings().setAuthorizationStrategy(new QwicketAuthorizationStrategy());

		uploadFolder = new Folder(System.getProperty("java.io.tmpdir"), "wicket-uploads");
		// Ensure folder exists
		uploadFolder.mkdirs();

		log.debug("Qwicket started.");
	}

	public Folder getUploadFolder() {
		return uploadFolder;
	}

	/*
	 * private static class SpringComponentInjector extends ComponentInjector {
	 *//**
	 * Metadata key used to store application context holder in application's
	 * metadata
	 */
	/*
	 * private static MetaDataKey CONTEXT_KEY = new
	 * MetaDataKey(ApplicationContextHolder.class) {
	 * 
	 * private static final long serialVersionUID = 1L;
	 * 
	 * };
	 *//**
	 * Constructor used when spring application context is declared in the
	 * spring standard way and can be located through
	 * {@link WebApplicationContextUtils#getRequiredWebApplicationContext(ServletContext)}
	 * 
	 * @param webapp
	 *            wicket web application
	 */
	/*
	 * public SpringComponentInjector(WebApplication webapp) { // locate
	 * application context through spring's // default location // mechanism and
	 * pass it on to the proper // constructor //
	 * System.out.println(WebApplicationContextUtils.class); //
	 * System.out.println(ContextLoaderListener.class); //
	 * System.out.println(WebApplicationContextUtils
	 * .getRequiredWebApplicationContext(webapp.getServletContext()));
	 * this(webapp,
	 * WebApplicationContextUtils.getRequiredWebApplicationContext(webapp
	 * .getServletContext())); }
	 *//**
	 * Constructor
	 * 
	 * @param webapp
	 *            wicket web application
	 * @param ctx
	 *            spring's application context
	 */
	/*
	 * public SpringComponentInjector(WebApplication webapp, ApplicationContext
	 * ctx) { if (webapp == null) { throw new
	 * IllegalArgumentException("Argument [[webapp]] cannot be null"); }
	 * 
	 * if (ctx == null) { throw new
	 * IllegalArgumentException("Argument [[ctx]] cannot be null"); }
	 * 
	 * // store context in application's metadata ...
	 * webapp.setMetaData(CONTEXT_KEY, new ApplicationContextHolder(ctx));
	 * 
	 * // ... and create and register the annotation // aware injector
	 * InjectorHolder.setInjector(new AnnotSpringInjector(new
	 * ContextLocator())); }
	 *//**
	 * Constructor for portlet applications
	 * 
	 * @param portletapp
	 */
	/*
	 * public SpringComponentInjector(PortletApplication portletapp) {
	 * GenericApplicationContext ctx = new GenericApplicationContext();
	 * 
	 * // locate spring's application context ... String configLocation =
	 * null;//
	 * portletapp.getWicketPortlet().getInitParameter("contextConfigLocation");
	 * Resource resource = null; try { resource = new
	 * UrlResource(ResourceUtils.getURL(configLocation)); } catch
	 * (FileNotFoundException e) { throw new RuntimeException(e.getMessage()); }
	 * 
	 * new XmlBeanDefinitionReader(ctx).loadBeanDefinitions(resource);
	 * ctx.refresh();
	 * 
	 * // ... store it in application's metadata ...
	 * portletapp.setMetaData(CONTEXT_KEY, new ApplicationContextHolder(ctx));
	 * 
	 * // ... and create and register the annotation // aware injector
	 * InjectorHolder.setInjector(new AnnotSpringInjector(new
	 * ContextLocator())); }
	 *//**
	 * This is a holder for the application context. The reason we need a
	 * holder is that metadata only supports storing serializable objects but
	 * application context is not. The holder acts as a serializable wrapper for
	 * the context. Notice that although holder implements serializable it
	 * really is not because it has a reference to non serializable context -
	 * but this is ok because metadata objects in application are never
	 * serialized.
	 * 
	 * @author ivaynberg
	 * 
	 */
	/*
	 * private static class ApplicationContextHolder implements Serializable {
	 * private static final long serialVersionUID = 1L;
	 * 
	 * private final ApplicationContext context;
	 *//**
	 * Constructor
	 * 
	 * @param context
	 */
	/*
	 * public ApplicationContextHolder(ApplicationContext context) {
	 * this.context = context; }
	 *//**
	 * @return the context
	 */
	/*
	 * public ApplicationContext getContext() { return context; } }
	 *//**
	 * A context locator that locates the context in application's metadata.
	 * This locator also keeps a transient cache of the lookup.
	 * 
	 * @author ivaynberg
	 * 
	 */
	/*
	 * private static class ContextLocator implements ISpringContextLocator {
	 * private transient ApplicationContext context;
	 * 
	 * private static final long serialVersionUID = 1L;
	 * 
	 * public ApplicationContext getSpringContext() { if (context == null) {
	 * context = ((ApplicationContextHolder)
	 * Application.get().getMetaData(CONTEXT_KEY)).getContext(); } return
	 * context; }
	 * 
	 * }
	 * 
	 * }
	 */
}
