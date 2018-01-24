package com.incture.pmc.workbox.services;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.incture.pmc.util.ServicesUtil;



/**
 * Mainly a wrapper over EntityManager. Intended as an equivalent of
 * EntityManagerFactory, since Dependency Injection is more prominent in J2EE
 * world. Instance can also be passed as an argument within/across non bean
 * classes. Also includes some utility methods written over Query API's.
 */
@Stateless
public class EntityManagerProvider implements EntityManagerProviderLocal {

	@PersistenceContext(unitName = "PMS_WB")
	private EntityManager em;

	public EntityManagerProvider() {
	}
	@Override
	public String sayHello(){
		Query q = em.createQuery("Select p From MasterDocumentDo p ");
		q.getResultList();
		return "Hi....";
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public Object getSingleResult(String queryName, EntityManager em,
			Object... parameters) {
		if (em == null) {
			em = getEntityManager();
		}
		Query query = em.createNamedQuery(queryName);
		if (!ServicesUtil.isEmpty(parameters)) {
			final int length = parameters.length;
			for (int i = 0; i < length; i++) {
				query.setParameter(i, parameters[i]);
			}
		}
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
		} catch (NonUniqueResultException e) {
		}
		return null;
	}

	@Override
	public List<?> getResultList(String queryName, Integer fromRowNo,
			Integer noOfRecords, EntityManager em, Object... parameters) {
		if (em == null) {
			em = getEntityManager();
		}
		Query query = em.createNamedQuery(queryName);
		if (!ServicesUtil.isEmpty(parameters)) {
			final int length = parameters.length;
			for (int i = 0; i < length; i++) {
				query.setParameter(i, parameters[i]);
			}
		}
		if (fromRowNo != null)
			query.setFirstResult(fromRowNo);
		if (noOfRecords != null)
			query.setMaxResults(noOfRecords);

		List<?> returnList = query.getResultList();
		return returnList;
	}

	@Override
	public List<?> getResultListByCustomQuery(String jpaQuery,
			Integer fromRowNo, Integer noOfRecords, EntityManager em,
			List<?> parameters) {
		if (em == null) {
			em = getEntityManager();
		}
		Query query = em.createQuery(jpaQuery);
		if (!ServicesUtil.isEmpty(parameters)) {
			final int length = parameters.size();
			for (int i = 0; i < length; i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		if (fromRowNo != null)
			query.setFirstResult(fromRowNo);
		if (noOfRecords != null)
			query.setMaxResults(noOfRecords);
		List<?> returnList = query.getResultList();
		return returnList;
	}
	


}