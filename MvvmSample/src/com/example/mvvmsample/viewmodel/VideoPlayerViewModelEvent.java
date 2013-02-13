package com.example.mvvmsample.viewmodel;

import com.example.mvvmsample.model.Video;
import com.example.mvvmsample.model.state.VideoPlayerState;
import com.example.mvvmsample.mvvm.BaseViewModelEvent;

public interface VideoPlayerViewModelEvent extends BaseViewModelEvent<VideoPlayerState> {
	void onVideoPlayed(Video video);

	void onVideoPaused(Video video);
}
