package me.atog.anr.plugin.nms;

import me.atog.anr.plugin.nms.actionbar.*;

public class Manager {
	
	public void setupModules() {
		new ActionBar_V1_12()
				.setMessage("Hola amigo")
				.setReceiver(null)
				.build();
	}
	
	/*public ActionBar getActionBarAPI() {
		if(Versions.is1_12()) {
			return new ActionBar_V1_12();
		} else {
			return null;
		}
	}*/
	
}
