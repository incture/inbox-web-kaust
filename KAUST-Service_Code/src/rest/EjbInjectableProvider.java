package com.incture.pmc.rest;

import java.lang.reflect.Type;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

/**
 * 
 * @author Windows
 * 
 *         This class is used to load the EJB into the Jersey runtime so that
 *         whenever @EJB annotation is used in the REST service exposer class
 * 
 */
@Provider
public class EjbInjectableProvider implements InjectableProvider<EJB, Type>,
		Injectable<Object> {

	private EJB annot;
	private Type type;

	@Override
	public Injectable<Object> getInjectable(ComponentContext compCtx,
			EJB annot, Type type) {
		this.annot = annot;
		this.type = type;
		return this;
	}

	@Override
	public ComponentScope getScope() {
		return ComponentScope.Singleton;
	}

	@Override
	public Object getValue() {
		try {
			String name = annot.name();
			if (name.equals("")) {
				name = ((Class<?>) type).getSimpleName();
			}
			Object ejb = new InitialContext().lookup("java:comp/env/" + name);
			return ejb;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

}
