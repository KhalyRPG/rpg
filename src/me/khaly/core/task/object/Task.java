package me.khaly.core.task.object;

import org.bukkit.Bukkit;

import me.khaly.core.KhalyCore;

public abstract class Task {
	private long seconds;
	private long delay;
	
	public Task(long seconds, long delay) {
		this.seconds = seconds;
		this.delay = delay;
	}
	
	public abstract void run();
	
	public void execute() {
		Bukkit.getScheduler().runTaskTimer(KhalyCore.getInstance(), new Runnable() {

			@Override
			public void run() {
				Task.this.run();
			}
			
		}, delay, seconds);
	}
}
