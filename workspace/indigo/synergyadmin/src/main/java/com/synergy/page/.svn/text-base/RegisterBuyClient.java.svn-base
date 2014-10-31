package com.synergy.page;

import org.apache.wicket.spring.injection.annot.SpringBean;

import com.synergy.service.CompanyService;
import com.synergy.service.ProductService;
import com.synergy.service.PurchaseService;
import com.synergy.service.SubscriberService;

public class RegisterBuyClient extends BasePageRegistering {

	@SpringBean
	SubscriberService subscriberService;

	@SpringBean
	PurchaseService purchaseService;

	@SpringBean
	ProductService productService;

	@SpringBean
	CompanyService companyService;

	public RegisterBuyClient() {
		add(new ClientForm("form", subscriberService, purchaseService, productService, companyService, true));
	}
}
