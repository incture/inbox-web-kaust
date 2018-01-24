package com.incture.pmc.workbox.services;

import javax.ejb.Local;

import com.incture.pmc.workbox.dto.WorKBoxDetailDto;

@Local
public interface ConsumeODataFacadeLocal {
	
	WorKBoxDetailDto getTaskDetails(String instanceId, String sapOrigin, String processor, String password);

	String allTaskInstance();

	String allOwnersInstance();

	String allAttrInstance();

	String allProcessInstance();
	
}
