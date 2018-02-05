package com.incture.pmc.workbox.dto;

import java.util.List;

public class ExistingDataDto{


	private int count ;
	private List<String> stringList ;
	
	
	@Override
	public String toString() {
		return "ExistingDataDto [count=" + count + ", stringList=" + stringList + "]";
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<String> getStringList() {
		return stringList;
	}
	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}








}
