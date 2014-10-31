package com.synergy.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.synergy.model.Company;
import com.synergy.model.Product;
import com.synergy.model.Purchase;
import com.synergy.model.Subscriber;
import com.synergy.service.ProductService;
import com.synergy.service.PurchaseService;
import com.synergy.service.SubscriberService;
import com.synergy.util.SynergyConfig;
import com.synergy.vo.ProductInUse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestPurchase {

	private PurchaseService purchaseService;

	private SubscriberService subscriberService;

	private ProductService productService;

	private EntityManagerFactory entityManagerFactory;

	@Test
	public void setUp() throws IOException {
		final InputStream in = this.getClass().getClassLoader().getResourceAsStream("synergyengine.properties");
		if (in == null) {
			Assert.fail("synergyengine.properties not found");
		}
		SynergyConfig.instance().load(in);

		Product product = new Product();
		product.setName(Product.VMAIL);
		productService.save(product);
		Assert.assertEquals(product.getId(), Long.valueOf(1));

		product = new Product();
		product.setName(Product.VCHAT);
		productService.save(product);
		Assert.assertEquals(product.getId(), Long.valueOf(2));

		Subscriber s = subscriberService.createSubscriberAndCompany("Fabiano", "fabiano@test.com", "f", "Fabiano Company", "website");
		subscriberService.create(s.getCompany(), s.getGroups(), "Brian", "brian@test.com", "b");
	}

	@Test
	public void testPurchase() {
		Subscriber fabiano = subscriberService.getByEmail("fabiano@test.com");
		final Product vmail = productService.find((long) 1);
		final Company company = fabiano.getCompany();
		Purchase purchase = purchaseService.purchaseProduct(company, vmail, 3, 30, "", null);
		Assert.assertNotNull(purchase);

		final List<Purchase> products = purchaseService.getPurchaseByProduct(company, vmail);
		Assert.assertEquals(1, products.size());

		final Integer numberOfPurchasedSeat = purchaseService.getNumberOfPurchasedSeat(company, vmail);
		Assert.assertEquals(3, numberOfPurchasedSeat.intValue());
	}

	@Test
	public void testAssignSeatPurchased() {
		final Product vmail = productService.find((long) 1);
		Subscriber fabiano = subscriberService.getByEmail("fabiano@test.com");
		final Company company = fabiano.getCompany();
		List<ProductInUse> pinuse = productService.getProductsInUse(company);
		Assert.assertTrue(pinuse.isEmpty());

		ArrayList<Product> products = new ArrayList<Product>();
		products.add(vmail);
		productService.updateProducts(fabiano, products);
		pinuse = productService.getProductsInUse(company);
		Assert.assertEquals(pinuse.get(0).quantity, 1);
	}

	@Test
	public void tearDown() {
		// drop the database, and create a new one for the next test
		final EntityManager e = entityManagerFactory.createEntityManager();
		e.getTransaction().begin();
		e.createNativeQuery("DROP DATABASE synergyunittest").executeUpdate();
		e.getTransaction().commit();
		e.getTransaction().begin();
		e.createNativeQuery("CREATE DATABASE synergyunittest").executeUpdate();
		e.getTransaction().commit();
	}

	@Resource
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	@Resource
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Resource
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	@Resource
	public void setPurchaseService(PurchaseService chatService) {
		this.purchaseService = chatService;
	}

}
