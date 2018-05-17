package com.tetiana.bakingapp.recipeSteps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.tetiana.bakingapp.R;
import com.tetiana.bakingapp.ReadData;
import com.tetiana.bakingapp.model.Step;


import java.io.IOException;
import java.util.ArrayList;

public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener {

    ArrayList<Step> steps = new ArrayList<>();
    private int step_id;
    private SimpleExoPlayer player;
    String path;

    public void setStep_id(int step_id) {
        this.step_id = step_id;
    }

    public static StepDetailFragment newInstance() {
        return new StepDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        step_id = getActivity().getIntent().getIntExtra("stepID", 0);
        try {
            ReadData readData = new ReadData(getActivity().getApplicationContext());
            steps = readData.getStepList(steps);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.steps_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView stepText = view.findViewById(R.id.step_full_text);
        stepText.setText(steps.get(step_id).getDescription());

        path = steps.get(step_id).getVideoURL();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        LoadControl loadControl = new DefaultLoadControl();
        // 3. Create the player
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);

        SimpleExoPlayerView playerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);
        playerView.setPlayer(player);
        playerView.setKeepScreenOn(true);
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity().getApplicationContext(), "Baking"));

        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // This is the MediaSource representing the media to be played.
        if (!path.equals("")){
            MediaSource videoSource = new ExtractorMediaSource(Uri.parse(path),
                    dataSourceFactory, extractorsFactory, null, null);
            // Prepare the player with the source.
            player.addListener(this);
            player.prepare(videoSource);
            playerView.requestFocus();
            player.setPlayWhenReady(true);
        }else{
            playerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.mipmap.ic_recipe));
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.setPlayWhenReady(false); //to pause a video because now our video player is not in focus
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case ExoPlayer.STATE_BUFFERING:
                //You can use progress dialog to show user that video is preparing or buffering so please wait
                break;
            case ExoPlayer.STATE_IDLE:
                //idle state
                break;
            case ExoPlayer.STATE_READY:
                // dismiss your dialog here because our video is ready to play now
                break;
            case ExoPlayer.STATE_ENDED:
                // do your processing after ending of video
                break;
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        // show user that something went wrong. I am showing dialog but you can use your way
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity().getApplicationContext());
        adb.setTitle("Could not able to stream video");
        adb.setMessage("It seems that something is going wrong.\nPlease try again.");
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getActivity().finish(); // take out user from this activity. you can skip this
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
    }

    @Override
    public void onPositionDiscontinuity() {
        //Video is not streaming properly
        Log.d("Mayur", "Discontinuity");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.release();   //it is important to release a player
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
