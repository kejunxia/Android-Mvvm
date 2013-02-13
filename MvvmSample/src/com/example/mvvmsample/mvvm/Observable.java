package com.example.mvvmsample.mvvm;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Observable<T extends Event> implements Iterable<T> {
	private final ConcurrentLinkedQueue<T> mEvents;

	public Observable() {
		mEvents = new ConcurrentLinkedQueue<T>();
	}

	/**
	 * Register an event
	 * 
	 * @param event
	 */
	public void registerEvent(T event) {
		mEvents.add(event);
	}

	/**
	 * Unregister an event
	 * 
	 * @param event
	 */
	public void unregisterEvent(T event) {
		mEvents.remove(event);
	}

	/**
	 * Clear all events
	 */
	public void clearEvents() {
		mEvents.clear();
	}

	@Override
	public Iterator<T> iterator() {
		return mEvents.iterator();
	}

	public int getEventCount() {
		return mEvents.size();
	}
}