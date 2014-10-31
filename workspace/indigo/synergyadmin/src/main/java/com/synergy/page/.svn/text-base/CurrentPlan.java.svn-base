package com.synergy.page;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.app.QwicketSession;
import com.synergy.model.Company;
import com.synergy.model.Product;
import com.synergy.model.Purchase;
import com.synergy.model.Subscriber;
import com.synergy.service.CompanyService;
import com.synergy.service.ProductService;
import com.synergy.service.PurchaseService;
import com.synergy.service.SubscriberService;
import com.synergy.vo.ProductInUse;

public class CurrentPlan extends MemberBasePage {

	@SpringBean
	CompanyService clientService;

	@SpringBean
	PurchaseService purchaseService;

	@SpringBean
	SubscriberService subscriberService;

	@SpringBean
	ProductService productService;

	public CurrentPlan() {
		add(new BookmarkablePageLink("buyLink", ProductsPage.class));
		add(new BookmarkablePageLink("historyLink", ConnectionsHistory.class));
		Company company = clientService.find(((QwicketSession) getSession()).getUser().getId());
		List<Purchase> conns = purchaseService.getPurchaseByCompany(company);
		float price = 0;
		ArrayList<PurchaseProduct> list = new ArrayList<PurchaseProduct>();
		List<ProductInUse> listInUse = productService.getProductsInUse(company);
		for (Purchase p : conns) {
			price += p.getPriceTotal();
			int qInUse = 0;
			for (ProductInUse pInUse : listInUse) {
				if (pInUse.product.equals(p.getProduct())) {
					qInUse = pInUse.quantity;
					break;
				}
			}
			list.add(new PurchaseProduct(p.getProduct().getName(), String.valueOf(p.getQuantity()), String.valueOf(qInUse), String.valueOf(p.getPriceTotal()), p.getDescription()));
		}
		add(new PurchaseListView(list));
		add(new Label("totalPrice", String.valueOf(price)));
	}

	private class PurchaseListView extends ListView {

		public PurchaseListView(List<PurchaseProduct> subscribers) {
			super("purchases", subscribers);
			setOutputMarkupId(true);
		}

		@Override
		protected void populateItem(ListItem item) {
			final PurchaseProduct purchase = (PurchaseProduct) item.getModelObject();
			item.add(new Label("plantype", purchase.name + "-" + purchase.description));
			item.add(new Label("nconns", purchase.quantity));
			item.add(new Label("nconnsu", purchase.qInUse));
			item.add(new Label("price", purchase.price));
		}
	}

	private class PurchaseProduct implements Serializable {

		String name;
		String quantity;
		String qInUse;
		String price;
		String description;

		public PurchaseProduct(String name, String quantity, String qInUse, String price, String description) {
			this.name = name;
			this.quantity = quantity;
			this.qInUse = qInUse;
			this.price = price;
			this.description = description;
		}

	}

}
