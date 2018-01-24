package com.incture.pmc.workbox.services;



import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityManager;

@Local
public interface EntityManagerProviderLocal {

	public EntityManager getEntityManager();

	public Object getSingleResult(String queryName, EntityManager em,
			Object... parameters);

	public List<?> getResultList(String queryName, Integer fromRowNo,
			Integer noOfRecords, EntityManager em, Object... parameters);

	public List<?> getResultListByCustomQuery(String jpaQuery,
			Integer fromRowNo, Integer noOfRecords, EntityManager em,
			List<?> parameters);

	String sayHello();

//	public Query createQuery(String string);
	


}