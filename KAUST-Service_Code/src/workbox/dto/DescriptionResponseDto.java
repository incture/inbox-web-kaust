package com.incture.pmc.workbox.dto;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DescriptionResponseDto {

	private List<DescriptionDto> instanceList;
	private Map<String,Integer> userCountMap;
	
	
	public List<DescriptionDto> getInstanceList() {
		return instanceList;
	}


	public void setInstanceList(List<DescriptionDto> instanceList) {
		this.instanceList = instanceList;
	}


	public Map<String, Integer> getUserCountMap() {
		return userCountMap;
	}


	public void setUserCountMap(Map<String, Integer> userCountMap) {
		this.userCountMap = userCountMap;
	}


	@Override
	public String toString() {
		return "DescriptionResponseDto [instanceList=" + instanceList + ", userCountMap=" + userCountMap + "]";
	}
	
}
