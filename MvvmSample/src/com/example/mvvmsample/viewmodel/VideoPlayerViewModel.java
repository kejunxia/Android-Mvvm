package com.example.mvvmsample.viewmodel;

import com.example.mvvmsample.model.state.VideoPlayerState;
import com.example.mvvmsample.mvvm.BaseViewModel;

public class VideoPlayerViewModel extends
		BaseViewModel<VideoPlayerViewModelEvent, VideoPlayerState> {

	public void playVideo() {
		if (mModel != null) {
			if (!mModel.isPlaying()) {
				mModel.setPlaying(true);
				for (VideoPlayerViewModelEvent evet : this) {
					evet.onVideoPlayed(mModel.getCurrentVideo());
				}
			}
		}
	}

	public void pauseVideo() {
		if (mModel != null) {
			if (mModel.isPlaying()) {
				mModel.setPlaying(false);
				for (VideoPlayerViewModelEvent evet : this) {
					evet.onVideoPaused(mModel.getCurrentVideo());
				}
			}
		}
	}
}
