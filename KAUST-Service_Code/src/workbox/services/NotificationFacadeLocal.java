package com.incture.pmc.workbox.services;

import javax.ejb.Local;

@Local
public interface NotificationFacadeLocal {

	public void sendNotification();

	String sendPushNotification(String message);

}
