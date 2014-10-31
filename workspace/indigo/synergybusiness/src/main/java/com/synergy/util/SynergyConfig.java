package com.synergy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SynergyConfig {

	private static SynergyConfig instance;

	private Properties prop = null;

	public static synchronized SynergyConfig instance() {
		if (instance == null) {
			instance = new SynergyConfig();
		}
		return instance;
	}

	private SynergyConfig() {
		prop = new Properties();
		String fileName = "synergyengine.properties";
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream("../" + fileName);
			if(is==null){
				is = getClass().getClassLoader().getResourceAsStream(fileName);
			}
			if (is != null) {
				prop.load(is);
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("SynergyEngine property file wasn't found: " + fileName);
	}

	public void load(InputStream in) throws IOException {
		if (prop.isEmpty()) {
			// only load if this property is empty, this way avoid property
			// overriding
			prop.load(in);
		}
	}
	
	public Boolean isTestMode() {
		return Boolean.valueOf(prop.getProperty("synergy.test"));
	}
	
	public String getUrlEncryptSecret() {
		return prop.getProperty("synergy.urlencrypt.secret");
	}

	public String getAdminSectionHost() {
		return prop.getProperty("synergy.adminsection.host");
	}

	public String getRed5RtmpServer() {
		return prop.getProperty("synergy.red5.rtmpserver");
	}
	
	public String getWowzaRtmpServer() {
		return prop.getProperty("synergy.wowza.rtmpserver");
	}

	public String getSupportMailHostName() {
		return prop.getProperty("synergy.mail.support.hostname");
	}

	public String getSupportMailUserName() {
		return prop.getProperty("synergy.mail.support.username");
	}

	public String getSupportMailPass() {
		return prop.getProperty("synergy.mail.support.password");
	}

	public String getSupportMailFrom() {
		return prop.getProperty("synergy.mail.support.from");
	}

	public String getNoreplyMailHostName() {
		return prop.getProperty("synergy.mail.noreply.hostname");
	}

	public String getNoreplyMailUserName() {
		return prop.getProperty("synergy.mail.noreply.username");
	}

	public String getNoreplyMailPass() {
		return prop.getProperty("synergy.mail.noreply.password");
	}

	public String getNoreplyMailFrom() {
		return prop.getProperty("synergy.mail.noreply.from");
	}

	public String getProperty(String property) {
		return prop.getProperty(property);
	}

	public String getProperty(String property, String defaultValue) {
		return prop.getProperty(property, defaultValue);
	}

}
