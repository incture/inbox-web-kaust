package com.incture.pmc.workbox.dto;

import java.util.List;
import java.util.Map;

public class ExistingDataDto{

	
	private Map<String,Integer> totalCountMap ;
	private Map<String,List<String>> existingInstanceMap;
	
	
	
	public Map<String, Integer> getTotalCountMap() {
		return totalCountMap;
	}



	public void setTotalCountMap(Map<String, Integer> totalCountMap) {
		this.totalCountMap = totalCountMap;
	}



	public Map<String, List<String>> getExistingInstanceMap() {
		return existingInstanceMap;
	}



	public void setExistingInstanceMap(Map<String, List<String>> existingInstanceMap) {
		this.existingInstanceMap = existingInstanceMap;
	}



	@Override
	public String toString() {
		return "DeviceListDto [totalCountMap=" + totalCountMap + ", existingInstanceMap=" + existingInstanceMap + "]";
	}

	
}
