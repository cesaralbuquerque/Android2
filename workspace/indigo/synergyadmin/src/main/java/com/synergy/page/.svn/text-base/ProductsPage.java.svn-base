package com.synergy.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.target.component.BookmarkablePageRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Company;
import com.synergy.model.Product;
import com.synergy.service.ProductService;
import com.synergy.vo.ProductInUse;

public class ProductsPage extends MemberBasePage {

	@SpringBean
	ProductService productService;

	List<ProductVO> products;

	private Company company;

	public ProductsPage() {
		this.company = clientService.find(((QwicketSession) getSession()).getUser().getId());
		add(new BookmarkablePageLink("currentLink", CurrentPlan.class));
		add(new BookmarkablePageLink("historyLink", ConnectionsHistory.class));
		// List<Product> products = productService.getAll();
		products = this.getProducts();

		// Add a FeedbackPanel for displaying our messages
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		add(feedbackPanel);

		Form frm = new ProductForm();

		frm.add(new ProductListView("products", products));

		add(frm);
	}

	final Label labelValue = new Label("totalVal", new IModel() {

		private String obj = "0.0";

		public Object getObject() {
			return obj;
		}

		public void setObject(Object arg0) {
			this.obj = (String) arg0;
		}

		public void detach() {
			// TODO Auto-generated method stub

		}

	});

	private class ProductForm extends Form {

		public ProductForm() {
			super("form1");
			add(labelValue);
		}

		protected void onSubmit() {

			// info("Total amount charged: " + calculateTotal(products));
			PageParameters params = new PageParameters();
			params.add("amt", "" + calculateTotal(products));

			boolean hasError = false;
			final List<ProductInUse> productsInUse = productService.getProductsInUse(company);
			for (ProductVO productVO : products) {
				int qtde = 0;
				try {
					qtde = Integer.parseInt(productVO.getNoOfUsers().getKey());
				} catch (Exception e) {
				}

				for (ProductInUse productInUse : productsInUse) {
					if (productVO.getName().equals(productInUse.product.getName())) {
						if (qtde < productInUse.quantity) {
							hasError = true;
							error("You can't downgrade the product " + productVO.getName() + ", because these seats are in use. Go to User & Contacts and unset the seats.");
						}

					}
				}
				if (qtde > 0) {
					params.add(productVO.getName(), String.valueOf(qtde));
				}
			}
			if (!hasError) {
				setResponsePage(new PaymentPage(params));
			}
			// getRequestCycle().setRequestTarget(new
			// BookmarkablePageRequestTarget(PaymentPage.class, params));
		}
	}

	private float calculateTotal(List<ProductVO> products) {

		float total = 0.0f;

		for (ProductVO productVO : products) {
			// total +=
			// (Integer.parseInt(productVO.getNoOfUsers().getKey())*
			// productVO.getPrice());
			if (productVO.getNoOfUsers() != null) {
				if (Product.VCHAT.equals(productVO.getName())) {
					total += (Integer.parseInt(productVO.getNoOfUsers().getKey()) * 9.95);
				} else if (Product.VMAIL.equals(productVO.getName())) {
					total += (Integer.parseInt(productVO.getNoOfUsers().getKey()) * 4.95);
				}
			}
		}
		return total;
	}

	private class ProductListView extends ListView {

		public ProductListView(String id, List<ProductVO> products) {
			super(id, products);
			setOutputMarkupId(true);
		}

		@Override
		protected void populateItem(ListItem item) {
			final ProductVO prod = (ProductVO) item.getModelObject();

			ChoiceRenderer choiceRenderer = new ChoiceRenderer("value", "key");

			float value = 10;
			if (Product.VCHAT.equals(prod.getName())) {
				value = 9.95f;
			} else if (Product.VMAIL.equals(prod.getName())) {
				value = 4.95f;
			}
			SelectOption[] plans = new SelectOption[] { new SelectOption("1", optValue(1, value)), new SelectOption("2", optValue(2, value)), new SelectOption("5", optValue(5, value)), new SelectOption("10", optValue(10, value)), new SelectOption("15", optValue(15, value)), new SelectOption("20", optValue(20, value)), new SelectOption("30", optValue(30, value)), new SelectOption("40", optValue(40, value)), new SelectOption("50", optValue(50, value)), new SelectOption("75", optValue(75, value)),
					new SelectOption("100", optValue(100, value)) };

			List plansList = Arrays.asList(plans);

			DropDownChoice choice = new DropDownChoice("noOfUsers", new PropertyModel(prod, "noOfUsers"), plansList, choiceRenderer) {
				@Override
				protected boolean wantOnSelectionChangedNotifications() {
					return true;
				}

				@Override
				protected void onSelectionChanged(Object newSelection) {
					labelValue.setModelObject(String.valueOf(calculateTotal(products)));
				}
			};

			choice.setMarkupId("noOfUsers_" + item.getIndex());

			choice.setOutputMarkupId(true);

			item.add(choice);

			item.add(new Label("name", prod.getName()));

			// Label lbl = new Label("price", "$" + prod.getPrice());
			//
			// lbl.setMarkupId("price_" + item.getIndex());
			//
			// lbl.setOutputMarkupId(true);
			//
			// item.add(lbl);
		}
	}

	private String optValue(int qntde, float value) {
		if (qntde == 1) {
			return "1 User / USD " + value * qntde;
		}
		return qntde + " Users / USD " + value * qntde;
	}

	private List<ProductVO> getProducts() {
		List<ProductVO> products = new ArrayList<ProductVO>();

		for (Product product : productService.getAll()) {

			ProductVO productVO = new ProductVO();

			productVO.setId(product.getId());
			productVO.setName(product.getName());

			products.add(productVO);
		}
		return products;
	}

}
