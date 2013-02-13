package com.example.mvvmsample.model.state;

import com.example.mvvmsample.model.Video;
import com.example.mvvmsample.mvvm.BaseStateModel;

public class VideoPlayerState extends BaseStateModel {
	// ViewModel State Model contains a data model
	private Video mCurrentVideo;
	private boolean mPlaying;

	/**
	 * @return the currentVideo
	 */
	public Video getCurrentVideo() {
		return mCurrentVideo;
	}

	/**
	 * @param currentVideo
	 *            the currentVideo to set
	 */
	public void setCurrentVideo(Video currentVideo) {
		mCurrentVideo = currentVideo;
	}

	/**
	 * @return the playing
	 */
	public boolean isPlaying() {
		return mPlaying;
	}

	/**
	 * @param playing
	 *            the playing to set
	 */
	public void setPlaying(boolean playing) {
		mPlaying = playing;
	}

}
