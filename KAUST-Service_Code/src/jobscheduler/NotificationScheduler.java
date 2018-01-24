package com.incture.pmc.jobscheduler;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.MessageListener;

import com.incture.pmc.workbox.services.NotificationFacadeLocal;
import com.sap.scheduler.runtime.JobContext;
import com.sap.scheduler.runtime.mdb.MDBJobImplementation;

/**
 * Message-Driven Bean implementation class for: NotificationScheduler
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "JobDefinition='NotificationScheduler' AND ApplicationName='incture.com/pmc~workbox~services~app'") })
public class NotificationScheduler extends MDBJobImplementation implements MessageListener {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	NotificationFacadeLocal notification;
	
	/**
     * @see MDBJobImplementation#MDBJobImplementation()
     */
    public NotificationScheduler() {
        super();
    }
	
    /*-- Logic to be executes while execution of the job - onJob --*/
    
	@Override
	public void onJob(JobContext arg0) throws Exception {
		notification.sendNotification();
		
	}

}
