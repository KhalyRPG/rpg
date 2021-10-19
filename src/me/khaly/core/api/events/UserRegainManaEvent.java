package me.khaly.core.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class UserRegainManaEvent extends PlayerEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancel = false;
	private double amount;
	
	public UserRegainManaEvent(Player player, double amount) {
		super(player);
		this.amount = amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
	@Override
	public final HandlerList getHandlers(){
		return handlers;
	}
	
	public final void setCancelled(final boolean cancel){
		this.cancel = cancel;
	}
	
	@Override
	public boolean isCancelled() {
		return cancel;
	}
}
