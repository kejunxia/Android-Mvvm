package com.example.mvvmsample.model;

public class Video {
	private String mName;
	private int Duration;

	/**
	 * @return the name
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		mName = name;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return Duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(int duration) {
		Duration = duration;
	}

}
