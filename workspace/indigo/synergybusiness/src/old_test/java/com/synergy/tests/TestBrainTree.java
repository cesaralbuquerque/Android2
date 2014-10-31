package com.synergy.tests;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.braintreegateway.Subscription;
import com.synergy.service.BrainTreeService;
import com.synergy.util.SynergyConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestBrainTree {

	private BrainTreeService brainTreeService;

	@Test
	public void setUp() throws IOException {
		final InputStream in = this.getClass().getClassLoader().getResourceAsStream("synergyengine.properties");
		if (in == null) {
			Assert.fail("synergyengine.properties not found");
		}
		SynergyConfig.instance().load(in);
	}

	@Test
	public void testCreateCustomerNameEmail() {
		final Result<Customer> result = brainTreeService.createCustomerWithNameAndEmail("fabiano", "fabiano@test.com");
		Assert.assertTrue(result.isSuccess());

		final Customer target = result.getTarget();

		Assert.assertEquals(target.getFirstName(), "fabiano");
		Assert.assertEquals(target.getEmail(), "fabiano@test.com");

		Assert.assertNotNull(target);

		final String customerId = target.getId();

		final Result<Customer> result2 = brainTreeService.addCreditCard(customerId, "TESTE", "4111111111111111", "12/2012", "4123");
		Assert.assertTrue(result2.isSuccess());

		final Customer target2 = result2.getTarget();
		Assert.assertNotNull(target2);

		Assert.assertEquals(target.getFirstName(), "fabiano");
		Assert.assertEquals(target.getEmail(), "fabiano@test.com");

	}

	@Test
	public void testCreateCustomerWithCreditCardAndPurchasePlan() {
		final Result<Customer> result = brainTreeService.createCustomerWithCreditCard("Fabiano", "4111111111111111", "12/2012", "4123");
		Assert.assertTrue(result.isSuccess());

		Assert.assertNotNull(result.getTarget());

		Assert.assertFalse(result.getTarget().getCreditCards().isEmpty());

		String paymentToken = result.getTarget().getCreditCards().get(0).getToken();
		Assert.assertNotNull(paymentToken);
		final Result<Subscription> subscribe = brainTreeService.subscribe(paymentToken, "VMAIL_PLAN_1");
		Assert.assertNotNull(subscribe);
	}

	@Resource
	public void setBrainTreeService(BrainTreeService brainTreeService) {
		this.brainTreeService = brainTreeService;
	}
}
