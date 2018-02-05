package com.incture.pmc.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.incture.pmc.workbox.services.NotificationFacadeLocal;

@Path("/notification")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class NotificationRest {

	@EJB
	private NotificationFacadeLocal notificationLocal;



	@GET
	@Path("/sendNotification")
	public String sendNotification() {
		notificationLocal.sendNotification();
		return "SUCCESS";
	}

	@GET
	@Path("/sendPushNotification")
	public String sendPushNotification(@QueryParam("message") String message) {
		notificationLocal.sendPushNotification(message);
		return "SUCCESS";
	}
	

}
