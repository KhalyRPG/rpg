package me.khaly.core.loader.task;

import me.khaly.core.KhalyCore;
import me.khaly.core.loader.Loader;
import me.khaly.core.task.ActionbarTask;
import me.khaly.core.task.ManaRegenerationTask;
import me.khaly.core.task.object.Task;

public class TaskLoader implements Loader {
	
	private KhalyCore core;
	
	public TaskLoader(KhalyCore core) {
		this.core = core;
	}
	
	@Override
	public void load() {
		loadTasks(
				new ActionbarTask(core),
				new ManaRegenerationTask(core)
				);
	}
	
	private void loadTasks(Task... tasks) {
		for(Task task : tasks) {
			task.execute();
		}
	}
	
}
