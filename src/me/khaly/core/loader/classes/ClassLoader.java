package me.khaly.core.loader.classes;

import me.khaly.core.loader.Loader;
import me.khaly.core.misc.Classes;
import me.khaly.core.rpg.classes.Archer;
import me.khaly.core.rpg.classes.Berserk;
import me.khaly.core.rpg.classes.Mage;

public class ClassLoader implements Loader {

	@Override
	public void load() {
		Classes.register(
				new Archer(),
				new Berserk(),
				new Mage()
				);
	}

}
