package com.incture.pmc.workbox.services;

import java.util.List;

import javax.ejb.Local;

import com.incture.pmc.workbox.dto.ResponseMessage;
import com.incture.pmc.workbox.dto.UserDetailDto;
import com.incture.pmc.workbox.dto.WorkboxRequestDto;

@Local
public interface ConsumeODataXpathFacadeLocal {
	
	ResponseMessage getDataFromECC(String processor,String password);

	List<UserDetailDto> getUsers(WorkboxRequestDto requestDto);

}
