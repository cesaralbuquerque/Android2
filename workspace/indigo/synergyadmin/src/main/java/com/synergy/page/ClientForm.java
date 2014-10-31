package com.synergy.page;

import java.util.ArrayList;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;

import com.synergy.app.SessionUtil;
import com.synergy.model.Company;
import com.synergy.model.Product;
import com.synergy.model.Subscriber;
import com.synergy.service.CompanyService;
import com.synergy.service.ProductService;
import com.synergy.service.PurchaseService;
import com.synergy.service.SubscriberService;
import com.synergy.util.Util;

public class ClientForm extends Form {

	private String name;

	private String companyName = "";

	private String email;

	private String phone;

	private String pass;

	private String repass;

	private String www = "http://";

	private String conteudo;

	private SubscriberService subscriberService;

	private CompanyService companyService;

	private Boolean acceptTerms = false;

	private boolean buy;

	private int randomInt(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	private String randomString(int min, int max) {
		int num = randomInt(min, max);
		byte b[] = new byte[num];
		for (int i = 0; i < num; i++)
			b[i] = (byte) randomInt('a', 'z');
		return new String(b);
	}

	private String imagePass = randomString(3, 5);
	private CaptchaImageResource captchaImageResource;
	private final TextField conteudoField = new TextField("conteudo");
	Image imageCaptcha = null;

	private String personalPage;

	private PurchaseService purchaseService;

	private ProductService productService;

	private void calcCaptchPass() {
		imagePass = randomString(3, 5);
	}

	public ClientForm(String id, SubscriberService subscriberService, PurchaseService purchaseService, ProductService productService, CompanyService companyService) {
		this(id, subscriberService, purchaseService, productService, companyService, false);
	}

	public ClientForm(String id, SubscriberService subscriberService, PurchaseService purchaseService, ProductService productService, CompanyService companyService, boolean buy) {
		super(id);
		this.subscriberService = subscriberService;
		this.purchaseService = purchaseService;
		this.productService = productService;
		this.companyService = companyService;
		this.buy = buy;
		setModel(new CompoundPropertyModel(this));
		add(conteudoField);
		captchaImageResource = new CaptchaImageResource(imagePass);
		imageCaptcha = new NonCachingImage("captchaImage", captchaImageResource);
		imageCaptcha.setOutputMarkupId(true);
		add(imageCaptcha);

		final Label lblUnameInUse = new Label("lblUnameInUse", "");
		add(lblUnameInUse.setVisible(true).setOutputMarkupId(true));

		add(new FeedbackPanel("feedback"));
		add(new CheckBox("acceptTerms"));
		add(new TextField("name").setRequired(true));
		add(new TextField("companyName"));
		add(new TextField("phone"));
		add(new TextField("email").setRequired(true).add(new AjaxFormComponentUpdatingBehavior("onblur") {

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				final boolean emailInUse = ClientForm.this.subscriberService.emailInUse(email);
				final Component lbl = ClientForm.this.get("lblUnameInUse");
				if (emailInUse) {
					lbl.setModelObject("Email already in use!");
				} else {
					lbl.setModelObject("");
				}
				ClientForm.this.addOrReplace(lbl);
				if (target != null) {
					target.addComponent(lbl);
				}
			}

		}));
		final PasswordTextField pass = new PasswordTextField("pass");
		final PasswordTextField repass = new PasswordTextField("repass");
		add(pass.setRequired(true));
		add(repass.setRequired(true));
		add(new AjaxFallbackLink("genCaptcha") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				setupCaptcha(target);
			}

		});
		add(new TextField("www"));
		add(new IFormValidator() {

			public FormComponent[] getDependentFormComponents() {
				// TODO Auto-generated method stub
				return null;
			}

			public void validate(Form form) {
				String email = Util.toString(form.getRequest().getParameter("email"));
				if (email.indexOf("@") == -1) {
					form.error(new String("Invalid email."));
				}
				String pass = Util.toString(form.getRequest().getParameter("pass"));
				if (!pass.equals(Util.toString(form.getRequest().getParameter("repass")))) {
					form.error(new String("The Password doesn't match the Retyped Password."));
				}
			}
		});
	}

	@Override
	protected void onSubmit() {
		if (!this.hasError()) {
			if (!acceptTerms) {
				error("You need to agree with the Terms of Service and Privacy Policy to do registering.");
				return;
			}
			if (imageCaptcha.isVisible() && !imagePass.equals(conteudo)) {
				setupCaptcha(null);
				error("The CAPTCHA doesn't match with the text entered.");
				return;
			}

			if (subscriberService.emailInUse(this.email)) {
				error("The username " + this.email + " already is registered. Please try a different one.");
				return;
			}

			Subscriber subscriber = subscriberService.createSubscriberAndCompany(name, email, pass, companyName, www, phone, buy);

			Company company = subscriber.getCompany();
			if (buy) {
				// if buying product, doesn't create MonthFree
				ServletWebRequest servletWebRequest = (ServletWebRequest) getRequest();
				SessionUtil.createSessionId(servletWebRequest.getHttpServletRequest(), subscriber);

				companyService.emailConfirmation(company);

				// add to CRM
				subscriberService.addToCRM(subscriber);

				setResponsePage(ProductsPage.class);
			} else {
				Product vmail = productService.getProductByName(Product.VMAIL);
				purchaseService.purchaseProduct(subscriber.getCompany(), vmail, 100, 0, "1 Month Free", null);
				Product vchat = productService.getProductByName(Product.VCHAT);
				purchaseService.purchaseProduct(subscriber.getCompany(), vchat, 100, 0, "1 Month Free", null);
				ArrayList<Product> products = new ArrayList<Product>();
				products.add(vmail);
				products.add(vchat);
				productService.updateProducts(subscriber, products);
				try {
					companyService.emailConfirmation(company);
					subscriberService.addToCRM(subscriber);
				} catch (RuntimeException e) {
					// TODO - Fucking work around, the service call must throw a
					// SynergyChatException.
					if (e.getMessage() != null && e.getMessage().indexOf("mail.synergychat.com") > -1) {
						error("Our system couldn't send a confirmation email to the email " + company.getEmail() + ". Please enter a different email or if you need some help send us an email to support@synergychat.com");
						return;
					}
					throw e;
				}

				setResponsePage(ClientRegisteredSuccess.class);
			}

		}
	}

	private void setupCaptcha(AjaxRequestTarget target) {
		calcCaptchPass();
		captchaImageResource = new CaptchaImageResource(imagePass);
		captchaImageResource.setCacheable(false);
		// imageCaptcha = new NonCachingImage("captchaImage",
		// captchaImageResource);
		// imageCaptcha.setOutputMarkupId(true);
		imageCaptcha.setImageResource(captchaImageResource);
		addOrReplace(imageCaptcha);
		if (target != null) {
			target.addComponent(imageCaptcha);
		}
	}

	protected void saveClient() {
		// QwicketSession session = (QwicketSession) Session.get();
		// session.addAttribute("CLIENT_REGISTERED", company);
		// setResponsePage(new RegisterBetaUser(company));
		// setResponsePage(RegisterBetaUser.class);
		setResponsePage(ClientRegisteredSuccess.class);
	}

	public String getPersonalPage() {
		return personalPage;
	}

	public void setPersonalPage(String personalPage) {
		this.personalPage = personalPage;
	}

	public String getName() {
		return name;
	}

	public void setName(String firstName) {
		this.name = firstName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRepass() {
		return repass;
	}

	public void setRepass(String repass) {
		this.repass = repass;
	}

	public String getWww() {
		return www;
	}

	public void setWww(String www) {
		this.www = www;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
}
