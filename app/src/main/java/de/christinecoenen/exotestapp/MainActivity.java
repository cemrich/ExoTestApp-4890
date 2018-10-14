package de.christinecoenen.exotestapp;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	private static final Uri VIDEO_URI = Uri.parse("https://mediandr-a.akamaihd.net/progressive/2018/1014/TV-20181014-0952-0900.hi.mp4");

	private SimpleExoPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// set uop player
		String userAgent = Util.getUserAgent(this, getString(R.string.app_name));
		DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent);
		TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();
		DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

		player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

		// set up view
		PlayerView playerView = findViewById(R.id.player);
		playerView.setPlayer(player);

		// load video
		MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
				.createMediaSource(VIDEO_URI);
		player.prepare(mediaSource);
	}

	@Override
	protected void onResume() {
		super.onResume();
		player.setPlayWhenReady(true);
	}

	@Override
	protected void onPause() {
		super.onPause();
		player.setPlayWhenReady(false);
	}
}
