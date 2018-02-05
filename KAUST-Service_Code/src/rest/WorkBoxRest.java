package com.incture.pmc.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import com.incture.pmc.workbox.dto.DeviceManagementDto;
import com.incture.pmc.workbox.dto.ResponseMessage;
import com.incture.pmc.workbox.dto.UserDetailDto;
import com.incture.pmc.workbox.dto.WorKBoxDetailDto;
import com.incture.pmc.workbox.dto.WorkboxRequestDto;
import com.incture.pmc.workbox.dto.WorkboxResponseDto;
import com.incture.pmc.workbox.services.WorkBoxBeanLocal;

@Path("/tasks")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class WorkBoxRest {

	@EJB
	private WorkBoxBeanLocal workBoxBeanLocal;


	/**
	 * URL : http://incturecwd:50000/pmc/workbox/tasks/byUser?userId=inc00466,skipCount=0,maxCount=20
	 * 
	 * @param userId
	 * @return WorkboxResponseDto
	 */

	@POST
	@Path("/byUser")
	public WorkboxResponseDto searchRecord(WorkboxRequestDto requestDto) {

		return workBoxBeanLocal.getWorkBoxDetailByUser(requestDto);
	}

	@GET
	@Path("/processNames")
	public List<String> getProcessNames() {
		return workBoxBeanLocal.getProcessNames();
	}

	@GET
	@Path("/processNamesFromDb")
	public List<String> getProcessNamesFromDb() {
		return workBoxBeanLocal.getProcessNamesFromDb();
	}

	@POST
	@Path("/detail")
	public WorKBoxDetailDto getDetail(WorkboxRequestDto requestDto) {
		return workBoxBeanLocal.getTaskDetails(requestDto);
	}

	//	@POST
	//	@Path("/attachment")
	//	public ResponseMessage getAttachments(WorkboxRequestDto requestDto) {
	//		return workBoxBeanLocal.getAttachment(requestDto);
	//	}

	//	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	//	@POST
	//	@Path("/attachment")
	//	public AttachmentResponse getAttachments(WorkboxRequestDto requestDto) {
	//		return workBoxBeanLocal.getAttachment(requestDto);
	//	}



	@POST
	@Path("/action")
	public ResponseMessage action(WorkboxRequestDto requestDto) {
		return workBoxBeanLocal.performAction(requestDto);
	}

	@POST
	@Path("/search")
	public List<UserDetailDto> searchUsers(WorkboxRequestDto requestDto) {
		return workBoxBeanLocal.getUsers(requestDto);
	}

	@POST
	@Path("/attachment")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getAttachments(WorkboxRequestDto requestDto)throws Exception {

		File targetFile = new File("abcd2.pdf");
		InputStream inputStream = workBoxBeanLocal.getAttachment(requestDto);
		// byte[] bytes = IOUtils.toByteArray(inputStream);
		OutputStream outputStream = new FileOutputStream(targetFile);
		IOUtils.copy(inputStream, outputStream);
		outputStream.close();
		Response r = Response.ok(targetFile,MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition","attachment; filename=\""+targetFile.getName()+"\"").build();	
		return r;
	}


	@POST
	@Path("/registerDevice")
	public ResponseMessage registerDevice(DeviceManagementDto dto) {

		return workBoxBeanLocal.registerDevice(dto);
	}

	@POST
	@Path("/removeDevice")
	public ResponseMessage removeDevice(DeviceManagementDto dto) {

		return workBoxBeanLocal.removeDevice(dto);
	}

	@POST
	@Path("/updateDevice")
	public ResponseMessage updateDevice(DeviceManagementDto dto) {

		return workBoxBeanLocal.updateDevice(dto);
	}

	@GET
	@Path("/allDeviceInstance")
	public String allDeviceInstance() {
		return workBoxBeanLocal.allDeviceInstance();
	}

}
