package com.tetiana.bakingapp.recipeSteps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.tetiana.bakingapp.JSONParse;
import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.model.Recipe;
import com.tetiana.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener {

    List<Recipe> recipes = new JSONParse().execute().get();
    List<Step> steps = new ArrayList<>();
    private int step_id;
    private SimpleExoPlayer player;
    String videoURL;
    String thumbnailURL;
    private long playerPosition = C.TIME_UNSET;
    private static final String PLAYER_POSITION_KEY = "position";
    private boolean playWhenReady;

    public StepDetailFragment() throws ExecutionException, InterruptedException {
    }

    public void setStep_id(int step_id) {
        this.step_id = step_id;
    }

    @BindView(R.id.step_full_text)
    TextView stepText;

    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;

    @BindView(R.id.previous_step)
    Button previousStep;

    @BindView(R.id.next_step)
    Button nextStep;

    @BindView(R.id.video_image)
    ImageView video_image;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        step_id = getActivity().getIntent().getIntExtra("stepID", 0);
        int recipe_id = getActivity().getIntent().getIntExtra("recipeID", 0);
        steps = recipes.get(recipe_id).getSteps();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.steps_detail, container, false);
        ButterKnife.bind(this, view);

        previousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = step_id - 1;
                if (position >= 0) {
                    loading(position);
                    step_id = position;
                }
            }
        });

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = step_id + 1;
                if (position < steps.size() - 1) {
                    loading(position);
                    step_id = position;
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            loading(step_id);
            player.seekToDefaultPosition();
        } else {
            step_id = savedInstanceState.getInt("step_id");
            playerPosition = savedInstanceState.getLong(PLAYER_POSITION_KEY, C.TIME_UNSET);
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
            loading(step_id);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void prepareVideo(String videoURL) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity().getApplicationContext(), "Baking"));
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(videoURL),
                dataSourceFactory, extractorsFactory, null, null);
        player.addListener(this);
        player.prepare(videoSource);
        playerView.requestFocus();
        player.setPlayWhenReady(playWhenReady);
    }

    private void loading(int step_id) {
        stepText.setText(steps.get(step_id).getDescription());
        videoURL = steps.get(step_id).getVideoURL();
        thumbnailURL = steps.get(step_id).getThumbnailURL();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        LoadControl loadControl = new DefaultLoadControl();
        loadControl.shouldStartPlayback(0, false);

        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
        playerView.setPlayer(player);
        if (playerPosition != C.TIME_UNSET) {
            player.seekTo(playerPosition);

        }
        player.setPlayWhenReady(playWhenReady);
        if ((videoURL == null || videoURL.isEmpty())) {
            playerView.setVisibility(View.GONE);
            video_image.setVisibility(View.VISIBLE);
            if (thumbnailURL.equals("")){
                Picasso.with(getActivity().getApplicationContext())
                        .load(R.drawable.baseline_videocam_off_black_36dp)
                        .into(video_image);
            }else if(thumbnailURL != null){
                Picasso.with(getActivity().getApplicationContext())
                        .load(thumbnailURL)
                        .error(R.drawable.baseline_videocam_off_black_36dp)
                        .into(video_image);
            }
        }else {
            video_image.setVisibility(View.GONE);
            playerView.setVisibility(View.VISIBLE);
            prepareVideo(videoURL);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            loading(step_id);
        }
    }
    @Override
    public void onTimelineChanged (Timeline timeline, Object manifest){
    }

    @Override
    public void onTracksChanged (TrackGroupArray trackGroups, TrackSelectionArray
            trackSelections){
    }

    @Override
    public void onLoadingChanged ( boolean isLoading){
    }

    @Override
    public void onPlayerStateChanged ( boolean playWhenReady, int playbackState){
        switch (playbackState) {
            case ExoPlayer.STATE_BUFFERING:
                break;
            case ExoPlayer.STATE_IDLE:
                break;
            case ExoPlayer.STATE_READY:
                break;
            case ExoPlayer.STATE_ENDED:
                break;
        }
    }

    @Override
    public void onPlayerError (ExoPlaybackException error){
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity().getApplicationContext());
        adb.setTitle("Could not able to stream video");
        adb.setMessage("It seems that something is going wrong.\nPlease try again.");
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getActivity().finish();
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
    }

    @Override
    public void onPositionDiscontinuity () {
        Log.d("Mayur", "Discontinuity");
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onStop () {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState (@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("step_id", step_id);
        releasePlayer();
        outState.putLong(PLAYER_POSITION_KEY, playerPosition);
        outState.putBoolean("playWhenReady", playWhenReady);
    }

    private void releasePlayer() {
        if (player != null) {
            playerPosition = player.getCurrentPosition();
            playWhenReady = player.getPlayWhenReady();
            player.stop();
            player.release();
            player = null;
        }
    }
}
