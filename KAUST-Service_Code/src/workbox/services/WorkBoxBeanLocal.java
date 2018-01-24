package com.incture.pmc.workbox.services;

import java.io.InputStream;
import java.util.List;

import javax.ejb.Local;

import com.incture.pmc.workbox.dto.DeviceManagementDto;
import com.incture.pmc.workbox.dto.ResponseMessage;
import com.incture.pmc.workbox.dto.UserDetailDto;
import com.incture.pmc.workbox.dto.WorKBoxDetailDto;
import com.incture.pmc.workbox.dto.WorkboxRequestDto;
import com.incture.pmc.workbox.dto.WorkboxResponseDto;

@Local
public interface WorkBoxBeanLocal {

	WorkboxResponseDto getWorkBoxDetailByUser(WorkboxRequestDto requestDto);

	List<String> getProcessNames();

	WorKBoxDetailDto getTaskDetails(WorkboxRequestDto requestDto);

	List<String> getProcessNamesFromDb();

	InputStream getAttachment(WorkboxRequestDto requestDto);

	ResponseMessage performAction(WorkboxRequestDto requestDto);

	List<UserDetailDto> getUsers(WorkboxRequestDto requestDto);
	
	ResponseMessage registerDevice(DeviceManagementDto deviceDto);

	ResponseMessage removeDevice(DeviceManagementDto deviceDto);

	ResponseMessage updateDevice(DeviceManagementDto deviceDto);
	
	String allDeviceInstance();
	 
}
