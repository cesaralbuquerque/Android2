package com.synergy.service.impl;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.synergy.util.SynergyConfig;

public class BraintreeConfiguration {
	/*
	 * Environment : sandbox Merchant ID : g8jnbbkbshhggy9g Public Key :
	 * v4btvyvd2tcxkhrr Private Key : 53ygj862bs7c5pdz
	 */

	public static BraintreeGateway getBraintreeGateway() {
		if(SynergyConfig.instance().isTestMode()){
			return new BraintreeGateway(Environment.SANDBOX, "g8jnbbkbshhggy9g", "v4btvyvd2tcxkhrr", "53ygj862bs7c5pdz");
		}else{
			return new BraintreeGateway(Environment.PRODUCTION, "ph4n7zq9f2hbwyzn", "hq22zb693b5kvd9b", "wjmd5hx9828r8vmv");
		}
	}

//	public static BraintreeGateway getBraintreeGateway() {
//		/*
//		 * return new BraintreeGateway( Environment.SANDBOX, "your_merchant_id",
//		 * "your_public_key", "your_private_key" );
//		 */
//
//		return new BraintreeGateway(Environment.PRODUCTION, "ph4n7zq9f2hbwyzn", "hq22zb693b5kvd9b", "wjmd5hx9828r8vmv");
//	}
}
