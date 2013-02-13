package com.example.mvvmsample.view;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvmsample.R;
import com.example.mvvmsample.model.Video;
import com.example.mvvmsample.model.state.VideoPlayerState;
import com.example.mvvmsample.viewmodel.VideoPlayerViewModel;
import com.example.mvvmsample.viewmodel.VideoPlayerViewModelEvent;

public class VideoPlayerView extends Activity implements OnClickListener {
	private static ObjectMapper sMapper;
	private static final String KEY_STATE_JSON = "KeyStateJson";

	private static final String TAG = VideoPlayerView.class.getSimpleName();
	private TextView mTextPlayingVideo;
	private TextView mTextVideoLen;
	private ImageView mPlayingButton;
	private Button mBtnLoadVideo;
	private VideoPlayerViewModel mViewModel;
	private VideoPlayerViewModelEvent mEvent;

	private VideoPlayerState mVideoPlayerState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPlayingButton = (ImageView) findViewById(R.id.imgPlayerButton);
		mPlayingButton.setOnClickListener(this);
		mTextPlayingVideo = (TextView) findViewById(R.id.txtPlayingVideoTitle);
		mTextVideoLen = (TextView) findViewById(R.id.txtVideoLength);
		mTextPlayingVideo.setOnClickListener(this);
		mBtnLoadVideo = (Button) findViewById(R.id.btnLoadVideo);
		mBtnLoadVideo.setOnClickListener(this);

		mViewModel = new VideoPlayerViewModel();

		// Setup events handler for the view models
		mEvent = new VideoPlayerViewModelEvent() {
			@Override
			public void onDataBound(VideoPlayerState dataModel) {
				if (dataModel == null) {
					mTextPlayingVideo.setText("No Video");
					mTextVideoLen.setText("0s");
					mPlayingButton.setImageResource(R.drawable.video_play_drawable);
					mBtnLoadVideo.setText("Load Video");
				} else {
					if (dataModel.getCurrentVideo() != null) {
						mTextPlayingVideo.setText(dataModel.getCurrentVideo().getName());
						mTextVideoLen.setText(dataModel.getCurrentVideo().getDuration() + "s");
						mPlayingButton.setImageResource(R.drawable.video_pause_drawable);
					}
					mBtnLoadVideo.setText("Unload Video");
				}

			}

			@Override
			public void onVideoPlayed(Video video) {
				mTextPlayingVideo.setText(video.getName());
				mPlayingButton.setImageResource(R.drawable.video_pause_drawable);
				Toast.makeText(getApplicationContext(), video.getName() + " is played.",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onVideoPaused(Video video) {
				mPlayingButton.setImageResource(R.drawable.video_play_drawable);
				Toast.makeText(getApplicationContext(), video.getName() + " is paused.",
						Toast.LENGTH_SHORT).show();
			}
		};
		// Register events.
		mViewModel.registerEvent(mEvent);

		// Use JSON to serialize state which is easy and quick, if too slow
		// replace it by Parcealable instead.
		if (savedInstanceState == null) {
			// Check if invoking activity send a video to play
			if (getIntent() != null) {
				if (getIntent().hasExtra(KEY_STATE_JSON)) {
					try {
						mVideoPlayerState = getMapper().readValue(
								getIntent().getStringExtra(KEY_STATE_JSON), VideoPlayerState.class);
					} catch (IOException e) {
						Log.e(TAG, e.getMessage(), e);
					}
				}
			}
		} else {
			// Restore the instance state when
			// 1. rotating the phone,
			// 2. back from background when it's killed by OS

			// This can be done in a base class for all view
			if (savedInstanceState.containsKey(KEY_STATE_JSON)) {
				try {
					mVideoPlayerState = getMapper().readValue(
							savedInstanceState.getString(KEY_STATE_JSON), VideoPlayerState.class);
					mViewModel.bindData(mVideoPlayerState);
				} catch (IOException e) {
					Log.e(TAG, e.getMessage(), e);
				}
			}
		}
		// No pre set player state, create a new one for testing.
		if (mVideoPlayerState == null) {
			mVideoPlayerState = new VideoPlayerState();
			Video video = new Video();
			video.setName("Test Video");
			video.setDuration(90);
			mVideoPlayerState.setCurrentVideo(video);
		}
	}

	// Remember to save instance state. Using static is dangerous as Android OS
	// will clear it in background. If you rely on them, you will find they
	// suddenly turned null when app came back from the background after a long
	// time period.

	// This can be done in a base class for all view
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mViewModel.getModel() != null) {
			try {
				outState.putString(KEY_STATE_JSON,
						getMapper().writeValueAsString(mViewModel.getModel()));
			} catch (IOException e) {
				Log.e(TAG, e.getMessage(), e);
			}
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btnLoadVideo:
				// Load unload button clicked.
				if (mViewModel.getModel() == null) {
					mViewModel.bindData(mVideoPlayerState);
				} else {
					mViewModel.bindData(null);
				}
				break;
			case R.id.imgPlayerButton:
				// Play/Pause button clicked.
				VideoPlayerState m = mViewModel.getModel();
				if (m != null) {
					if (!m.isPlaying()) {
						mViewModel.playVideo();
					} else {
						mViewModel.pauseVideo();
					}
				} else {
					mViewModel.bindData(mVideoPlayerState);
					mViewModel.playVideo();
				}
				break;
			default:
				break; // do nothing
		}
	}

	private static ObjectMapper getMapper() {
		if (sMapper == null) {
			sMapper = new ObjectMapper();
		}
		return sMapper;
	}
}
