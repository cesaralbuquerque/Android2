/*
 * @Copyright S&B Networks 2008
 */
package com.synergy.util;

import java.io.File;

import javax.servlet.ServletContext;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static void init(ServletContext sc) {
		try {
			sessionFactory = new AnnotationConfiguration().configure(new File(sc.getRealPath("WEB-INF/hibernate.cfg.xml"))).buildSessionFactory();
		} catch (Throwable ex) {
			// Log exception!
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}

	}

	public static Session openSession() throws HibernateException {
		Session session = sessionFactory.openSession();
		return session;
	}
}
