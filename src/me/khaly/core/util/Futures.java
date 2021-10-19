package me.khaly.core.util;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Futures {
	
	public static <T> void onMainThread(final Plugin plugin, final CompletableFuture<T> future,
			final BiConsumer<T, Throwable> consumer) {
		future.whenComplete((value, exception) -> {
			if (Bukkit.isPrimaryThread()) {
				consumer.accept(value, exception);
			} else {
				Bukkit.getScheduler().runTask(plugin, () -> consumer.accept(value, exception));
			}
		});
	}

	public static <T> Collector<CompletableFuture<T>, ?, CompletableFuture<List<T>>> collector() {
		return Collectors.collectingAndThen(Collectors.toList(), Futures::of);
	}

	public static <T> CompletableFuture<List<T>> of(final Stream<CompletableFuture<T>> futures) {
		return of(futures.collect(Collectors.toList()));
	}

	public static <T> CompletableFuture<List<T>> of(final Collection<CompletableFuture<T>> futures) {
		return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
				.thenApplyAsync($ -> awaitCompletion(futures));
	}

	private static <T> List<T> awaitCompletion(final Collection<CompletableFuture<T>> futures) {
		return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}
}
