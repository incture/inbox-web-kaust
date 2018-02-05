package com.incture.pmc.workbox.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActionsRequestDto {
	
	private String action;
	private String itemNo;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	@Override
	public String toString() {
		return "ActionsRequestDto [action=" + action + ", itemNo=" + itemNo + "]";
	}
	
	
}
