package me.khaly.core.util;

import me.khaly.core.KhalyCore;

public class Utils {
	
	private ProfileUtil profileUtil;
	
	public Utils(KhalyCore core) {
		this.profileUtil = new ProfileUtil(core);
	}
	
	public ProfileUtil getProfileUtil() {
		return profileUtil;
	}
	
	
}
