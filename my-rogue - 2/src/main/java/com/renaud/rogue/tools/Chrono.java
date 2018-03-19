package com.renaud.rogue.tools;

public class Chrono {
	private long ref;
	private long time;
	
	public Chrono(long ref) {
		this.ref = ref;
		time = System.currentTimeMillis();
	}
	
	public boolean isEllapsed(){
		long cur = System.currentTimeMillis();
		if((cur - time)>ref){
			time = cur;
			return true;
		}
		return false;
	}
	
}
