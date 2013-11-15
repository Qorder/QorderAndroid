package com.example.clientprototype.menuselector;

public class QRParser implements IMenuSelector {
	
	
	private void doParsing()	{
		//TODO: use zbarscannerlibrary for parsing
	}
	
	@Override
	public String getCode() {
		doParsing();
		
		return "default";
	}

}
